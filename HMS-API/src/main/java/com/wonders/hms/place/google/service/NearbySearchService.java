package com.wonders.hms.place.google.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.place.google.vo.NearbySearchRS;
import com.wonders.hms.place.google.vo.NearbySearchVO;
import com.wonders.hms.place.google.vo.NearbyTouristAttraction;
import com.wonders.hms.util.*;
import com.wonders.hms.util.vo.api.infra.google.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class NearbySearchService {
    private static final String GOOGLE_PLACE_NEARBY_SEARCH_PATH = "maps/api/place/nearbysearch/json";
    private static final String LODGING = "lodging";
    private static final int MAX_GOOGLE_PLACE_API_TRY_NUM = 5;
    private static final int MIN_RESLUT_PLACE_INFO_NUM = 20;

    @Value("${google.place.search.url}")
    private String GOOGLE_PLACE_SEARCH_URL;

    private String GOOGLE_PLACE_API_KEY;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GooglePlaceInfoCache googlePlaceInfoCache;

    private RestClient restClient;

    @Autowired
    private Place placeProp;

    @PostConstruct
    public void init() {

        this.GOOGLE_PLACE_API_KEY = placeProp.getKey();
        this.restClient = new RestClient(this.GOOGLE_PLACE_SEARCH_URL);
    }

    public List<NearbyTouristAttraction> nearbySearch(NearbySearchVO nearbySearchVO) throws IOException, HttpClientErrorException {
        List<NearbyTouristAttraction> nearbyTouristAttractionCacheResult = googlePlaceInfoCache.get(nearbySearchVO.getLocation());
        if (nearbyTouristAttractionCacheResult != null) {
            return nearbyTouristAttractionCacheResult;
        }

        List<NearbyTouristAttraction> nearbyTouristAttractions = new ArrayList<>();

        Double nearbySearchVOLatitude = Double.parseDouble(nearbySearchVO.getLocation().split(",")[0]);
        Double nearbySearchVOLongitude = Double.parseDouble(nearbySearchVO.getLocation().split(",")[1]);

        nearbySearchVO.setKey(this.GOOGLE_PLACE_API_KEY);
        int tryNum = 0;
        while (nearbyTouristAttractions.size() < MIN_RESLUT_PLACE_INFO_NUM && tryNum < MAX_GOOGLE_PLACE_API_TRY_NUM ) {

            String nearbySearchResponseBody =
                    this.restClient.get(this.GOOGLE_PLACE_NEARBY_SEARCH_PATH + "?" + this.objectMapper.convertValue(nearbySearchVO, UriFormat.class));

            NearbySearchRS nearbySearchRS = this.objectMapper.readValue(nearbySearchResponseBody, NearbySearchRS.class);

            nearbySearchRS.getResults().forEach(result -> {
                if (result.getTypes().get(0).equals(LODGING)) {
                    return;
                }
                NearbyTouristAttraction nearbyTouristAttraction = new NearbyTouristAttraction();

                nearbyTouristAttraction.setName(result.getName());
                nearbyTouristAttraction.setIcon(result.getIcon());
                nearbyTouristAttraction.setType(result.getTypes().get(0));
                nearbyTouristAttraction.setDistance(LocationDistance.distance(
                        nearbySearchVOLatitude, nearbySearchVOLongitude,
                        result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng(),
                        LocationDistanceUnit.KILOMETER
                ));

                nearbyTouristAttractions.add(nearbyTouristAttraction);
            });

            if (nearbySearchRS.getNextPageToken() == null) {
                break;
            }
            nearbySearchVO.setPageToken(nearbySearchRS.getNextPageToken());
            tryNum++;
        }

        googlePlaceInfoCache.set(nearbySearchVO.getLocation(), nearbyTouristAttractions);

        return nearbyTouristAttractions;
    }
}
