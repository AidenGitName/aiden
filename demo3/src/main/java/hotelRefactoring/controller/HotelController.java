package hotelRefactoring.controller;

import com.sun.deploy.net.HttpResponse;
import hotelRefactoring.domain.AgodaRequest;
import lombok.extern.java.Log;
import org.springframework.http.HttpRequest;

@Log
public class HotelController {
    String searchList (HttpRequest req, HttpResponse res) {
        String query = req.getMethodValue();

        buildSearch(query)
                .then(searchObj => {
                log.info("saving log");
        return hotelModel.saveLog(searchObj);
    })
    .then(searchObj => {
                logger.debug('search!');
        return hotelModel.select(searchObj);
    })
    .then(dbQueriedSearch => {
                logger.debug('dbQueriedSearch!');
        let req = new AgodaRequest();
        return req.run(dbQueriedSearch);
    })
    .then(agodaQueriedSearch => {
                logger.debug('agodaQueriedSearch!');

        agodaQueriedSearch.setEachHotelMinMaxPrice();
        let minPrice = agodaQueriedSearch.getMinHotelPirce();
        let maxPrice = agodaQueriedSearch.getMaxHotelPirce();

        agodaQueriedSearch.filter();

        agodaQueriedSearch.setEachHotelMinMaxPrice();

        let response = makeResponse(agodaQueriedSearch, minPrice, maxPrice);

        res.send(response);
    })
    .catch(err => {
                res.status(err.status || 500);
        res.send('Internal Server Error');
    });
    };

    let searchListByGeo = function(req, res) {
        let query = req.query;

        buildSearch(query)
                .then(searchObj => {
        return hotelModel.select(searchObj, 2);
    })
    .then(dbQueriedSearch => {
                logger.debug('dbQueriedSearch!');
        let req = new AgodaRequest();
        return req.run(dbQueriedSearch);
    })
    .then(agodaQueriedSearch => {
                logger.debug('agodaQueriedSearch!');

        agodaQueriedSearch.setEachHotelMinMaxPrice();
        let minPrice = agodaQueriedSearch.getMinHotelPirce();
        let maxPrice = agodaQueriedSearch.getMaxHotelPirce();

        agodaQueriedSearch.filter();

        agodaQueriedSearch.setEachHotelMinMaxPrice();

        let response = makeResponse(agodaQueriedSearch, minPrice, maxPrice);

        res.send(response);
    })
    .catch(err => {
                res.status(err.status || 500);
        res.send('Internal Server Error');
    });
    }


    function makeResponse(agodaQueriedSearch, minPrice, maxPrice) {
        let response = new HotelSearchResponser();
        response.hotels = agodaQueriedSearch.paging();
        response.totalSize = agodaQueriedSearch.result.hotels.length || 0;
        response.minPrice = minPrice;
        response.maxPrice = maxPrice;
        response.latitude = agodaQueriedSearch.latitude;
        response.longitude = agodaQueriedSearch.longitude;

        return response;
    }

    void buildSearch(String query) {
        return bpHotelSearch.BpHotelSearchBuilder()
                .setCity(query.city)
                .setCheckinDate(query.checkinDate)
                .setCheckoutDate(query.checkoutDate)
                .setRooms(query.rooms)
                .setAdults(query.adults)
                .setChildren(query.children)
                .setStarRating(query.starRating)
                .setRatingAvg(query.ratingAvg)
                .setMinPrice(query.minPrice)
                .setMaxPrice(query.maxPrice)
                .setCheckinTimeFrom(query.checkinTimeFrom)
                .setCheckinTimeTo(query.checkinTimeTo)
                .setCheckoutTimeFrom(query.checkoutTimeFrom)
                .setCheckoutTimeTo(query.checkoutTimeTo)
                .setPage(query.page)
                .setLatitude(query.lat)
                .setLongitude(query.long)
        .setLanguage(query.lang)
                .setCurrency(query.currency)
                .setIncludeSoldout(query.includeSoldout)
                .setGeoType(query.geoType)
                .setCountry(query.country)
                .buildPromise();
    }

    let suggest = function (req, res) {
        logger.debug('suggest!');

        let query = req.query;
        placeAuto(query.city)
                .then(results => {
                res.status(200);
        res.send(results);
        })
        .catch(err => {
                logger.error(err);
        if (err.code) {
            throw err;
        }
        throw bpError.makeError('Internal Server Error ', 500);
        });
    };

    function starRatingCount(req, res) {
        logger.debug('star rating count called!');
        let query = req.query;

        if(isEmpty(query)) {
            throw bpError.makeError('need query');
        }

        if (isEmpty(query.city)) {
            if ( isEmpty(query.lat) || isEmpty(query.long) ) {
                throw bpError.makeError('need city, lat or long');
            }
        }

        bpHotelSearch.BpHotelSearchBuilder()
                .setCity(req.query.city)
                .setLatitude(req.query.lat)
                .setLongitude(req.query.long)
        .buildPromise()
                .then(bpHotelSearch => {
        return hotelModel.countByRatingAvg(bpHotelSearch);
        })
        .then(score => {
                res.send(score);
        })
        .catch(err => {
                logger.error(err);
        throw Error();
        });
    }
}
