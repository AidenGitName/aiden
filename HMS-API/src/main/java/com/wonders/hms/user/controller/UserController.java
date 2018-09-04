package com.wonders.hms.user.controller;

import com.wonders.hms.config.URIMapping;
import com.wonders.hms.user.service.UserService;
import com.wonders.hms.user.service.WonderIdService;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.HotelUrlInfo;
import com.wonders.hms.user.vo.MyPageHistory;
import com.wonders.hms.user.vo.MypageHotelSearchInfo;
import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.util.HmsXssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(URIMapping.BASE_URI + "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WonderIdService wonderIdService;

    @GetMapping("/{token}/hotels")
    @ResponseBody
    public List<MyPageHistory> getMyPageSearchHistory(@PathVariable("token") String token, BookStatus status) throws Exception{
        return userService.getMyPageSearchHistory(wonderIdService.getMid(token), status);
    }

//    @GetMapping("/m/{mid}/hotels")
//    @ResponseBody
//    public List<MyPageHistory> getMyPageSearchHistoryWithMid(@PathVariable("mid") String mid, BookStatus status) throws Exception{
//        return userService.getMyPageSearchHistory(Long.parseLong(mid), status);
//    }

    @PostMapping("/{token}/hotels")
    @ResponseBody
    public void getStatus(@PathVariable("token") String token, @RequestBody SearchHistory searchHistory) throws Exception {
        searchHistory.setMId(wonderIdService.getMid(token));
        userService.insertSearchHistory(searchHistory);
    }

    @GetMapping("/{token}/hotels/special")
    @ResponseBody
    public MypageHotelSearchInfo getHotelUrl(@PathVariable("token") String token, @Valid HotelUrlInfo hotelUrlInfo) {
        return userService.getHotelUrl(wonderIdService.getMid(token), hotelUrlInfo);
    }
}
