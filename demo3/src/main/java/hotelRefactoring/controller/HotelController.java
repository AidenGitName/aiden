package hotelRefactoring.controller;

import com.sun.deploy.net.HttpResponse;
import hotelRefactoring.domain.BpHotelSearch;
import lombok.extern.java.Log;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

@Controller
@Log
public class HotelController {
    public String searchList(HttpRequest req, HttpResponse res){

        String query = null;// req.query

        buildSearch(query);

        return null;
    }

    public void buildSearch(String query){
        BpHotelSearch bpHotelSearch = new BpHotelSearch();
        if(bpHotelSearch != null) {
            log.info("save log");
        }
    }
}
