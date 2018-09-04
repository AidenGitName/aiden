package com.wonders.hms.wonder.service;

import com.wonders.hms.room.vo.CommonRoom;
import com.wonders.hms.wonder.persistence.WonderRoomInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WonderRoomInfoService {
    @Autowired
    WonderRoomInfoMapper wonderRoomInfoMapper;

    public void insertWonderRoomsInfo(List<CommonRoom> commonRooms) {
        wonderRoomInfoMapper.insertWonderRoomInfo(commonRooms);
    }

    public Map getWonderRoomInfo(Long wonderHotelInfoId){
        return wonderRoomInfoMapper.getWonderRoomInfo(wonderHotelInfoId);
    }
}
