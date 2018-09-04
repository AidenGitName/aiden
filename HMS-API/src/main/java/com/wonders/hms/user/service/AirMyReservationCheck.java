package com.wonders.hms.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.user.vo.AirMyReservation;
import com.wonders.hms.util.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AirMyReservationCheck {
    @Value("${wonder.air.api.url}")
    private String WONDER_AIR_SERVER;

    @Autowired
    private ObjectMapper objectMapper;

    public AirMyReservation getNowTo1MonthAirReservationInfo(Long mId) throws IOException{
        RestClient restClient = new RestClient(WONDER_AIR_SERVER);

        final String airReservationCheckPath = "air/AirMyRsvChk";

        String airReservationResponseBody = restClient.get(
                String.format("%s?mid=%d&gbn=%s&startDate=%s&endDate=%s&rsvStatus=%s",
                        airReservationCheckPath,
                        mId,
                        "D",
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        "RMPQ,RMTK"
                    )
        );

        AirMyReservation airReservation = objectMapper.readValue(airReservationResponseBody, AirMyReservation.class);

        return airReservation;
    }
}
