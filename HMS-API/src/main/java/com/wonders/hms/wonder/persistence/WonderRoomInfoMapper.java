package com.wonders.hms.wonder.persistence;

import com.wonders.hms.room.vo.CommonRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WonderRoomInfoMapper {

    void insertWonderRoomInfo(@Param("commonRooms") List<CommonRoom> commonRooms);

    Map getWonderRoomInfo(Long wonderHotelInfoId);
}
