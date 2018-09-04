package com.wonders.hms.shcedule;

import com.wonders.hms.agoda.service.AgodaHotelInfoUpdateService;
import com.wonders.hms.agoda.service.AgodaRequestService;
import com.wonders.hms.bookingDotCom.service.BookingDotComHotelInfoService;
import com.wonders.hms.expedia.service.ExpediaHotelInfoUpdateService;
import com.wonders.hms.expedia.service.ExpediaSearchHistoryService;
import com.wonders.hms.user.service.UserService;
import com.wonders.hms.wonder.service.WonderHotelUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HotelBookingHistoryScheduler {

    @Autowired
    private RedisTemplate<String, Object> scheduleRedisTemplate;

    @Autowired
    private AgodaRequestService agodaRequestService;

    @Autowired
    private ExpediaSearchHistoryService expediaSearchHistoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private AgodaHotelInfoUpdateService agodaHotelInfoUpdateService;

    @Autowired
    private BookingDotComHotelInfoService bookingDotComHotelInfoService;

    @Autowired
    private WonderHotelUpdateService wonderHotelUpdateService;

    @Autowired
    private ExpediaHotelInfoUpdateService expediaHotelInfoUpdateService;

    @Scheduled(cron = "0 */15 * * * *")
    public void searchHistoryUpdate() throws Exception {
        log.info("update started!");
        System.out.println("update started!");
        final int intervalMinutes = 15;

        if (LocalDateTime.now().getMinute() % intervalMinutes != 0) {
            int waitBumperMinutes = 1;
            Thread.sleep( waitBumperMinutes * 1000 * 60 );
        }

        int nowMinutes = LocalDateTime.now().getHour() * 60 + LocalDateTime.now().getMinute();
        int searchHistoryUpdateScheduleCycleInDay = nowMinutes / intervalMinutes;

        String searchHistoryUpdateScheduleKey = "bookHistoryUpdate" + searchHistoryUpdateScheduleCycleInDay;
        log.info("searchHistoryUpdateScheduleKey:" + searchHistoryUpdateScheduleKey);
        System.out.println(("searchHistoryUpdateScheduleKey:" + searchHistoryUpdateScheduleKey));

        ValueOperations<String, Object> valueOperations = scheduleRedisTemplate.opsForValue();

        boolean isKeyExist =
                valueOperations.setIfAbsent(searchHistoryUpdateScheduleKey, searchHistoryUpdateScheduleCycleInDay);

        if (!isKeyExist) {
            log.info("return");
            System.out.println("return");
            log.info("----------------------------------  " + LocalDateTime.now());
            System.out.println(("----------------------------------  " + LocalDateTime.now()));
            return;
        }

        scheduleRedisTemplate.expire(searchHistoryUpdateScheduleKey, 10, TimeUnit.MINUTES);

        agodaRequestService.updateBookingResult();
        System.out.println("agoda update start");

        userService.updateSearchHistoryWithBookingDotCom();
        System.out.println("booking dot com start");

        expediaSearchHistoryService.updateBookingResult();
        System.out.println("expedia start");

        log.info("update done");
        System.out.println("update done");
        log.info("----------------------------------  " + LocalDateTime.now());
        System.out.println(("----------------------------------  " + LocalDateTime.now()));
    }

    // TODO schedule check!!
//    @Scheduled(cron = "0 0 03 1 * ?")
    public void hotelInfoUpdate() {
        try {
            int hotelInfoUpdateScheduleCycleInYear = LocalDateTime.now().getDayOfYear();

            String hotelInfoUpdateScheduleKey = "hotelInfoUpdate" + hotelInfoUpdateScheduleCycleInYear;

            ValueOperations<String, Object> valueOperations = scheduleRedisTemplate.opsForValue();

            boolean isKeyExist =
                    valueOperations.setIfAbsent(hotelInfoUpdateScheduleKey, hotelInfoUpdateScheduleCycleInYear);

            if (!isKeyExist) {
                return;
            }

            final int searchHistoryUpdateKeyExpireDays = 1;

            scheduleRedisTemplate.expire(hotelInfoUpdateScheduleKey, searchHistoryUpdateKeyExpireDays, TimeUnit.DAYS);

            log.debug("agoda db update started!");
            agodaHotelInfoUpdateService.hotelInfoUpdate();

            log.debug("booking.com db update started!");
            bookingDotComHotelInfoService.updateBookingHotelInfo();

            log.debug("booking.com db update started!");
            expediaHotelInfoUpdateService.updateExpediaStaticHotelContent();

            log.debug("wonderhotel db update started!");
            wonderHotelUpdateService.hotelInfoUpdate();
        } catch (Exception e) {
            log.error("hotel static info update error", e);
        }
    }
}
