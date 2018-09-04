package com.wonders.hms.expedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelAmenityMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelImageMapper;
import com.wonders.hms.expedia.persistence.ExpediaHotelInfoMapper;
import com.wonders.hms.expedia.vo.searchapi.ExApiHotel;
import com.wonders.hms.expedia.vo.staticInfo.ExAmenityList;
import com.wonders.hms.expedia.vo.staticInfo.ExImage;
import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
import com.wonders.hms.user.persistence.SearchHistoryMapper;
import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.wonder.type.HotelVendorKind;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class ExpediaHotelInfoUpdateService {

    @Autowired
    private ExpediaHotelInfoMapper expediaHotelInfoMapper;

    @Autowired
    private ExpediaHotelAmenityMapper expediaHotelAmenityMapper;

    @Autowired
    private ExpediaHotelImageMapper expediaHotelImageMapper;

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Autowired
    private ExpediaRequestService expediaRequestService;

    @Value("${expedia.static.hotel.content.url.format}")
    private String expediaStaticHotelContnetUrlFormat;

    @Value("${expedia.static.hotel.content.count}")
    private Integer expediaStaticHotelContentCount;

    @Value("${folder.filedownload.tmp}")
    private String expediaStaticHotelContentPath;

    private static final String expediaStaticHotelContentFilename = "ExpediaHotelContent";
    private static final String unzipFileExtension = ".json";
    private static final Integer unzipByteBufferSize = 65536;

    public void updateExpediaStaticHotelContent() {
        try {
            List<String> dowonloadZipFilePaths = downloadHotelContent();
            List<String> hotelStaticContentPaths = unzipHotelContentFile(dowonloadZipFilePaths);
            hotelStaticContentPaths.forEach(
                    hotelStaticContentPath -> {
                        try {
                            updateHotelInfoStaticFileToDB(hotelStaticContentPath);
                        } catch (Exception e) {
                            log.error(hotelStaticContentPath + " : " + e.toString());
                        }
                    });
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
    private List<String> downloadHotelContent() {
        List<String> downloadZipFilePaths = new ArrayList<>();

        IntStream.range(0, expediaStaticHotelContentCount).forEach(
                n -> {
                    try {
                        InputStream in = new URL(
                                String.format(expediaStaticHotelContnetUrlFormat, n + 1)
                        ).openStream();
                        Files.copy(
                                in,
                                Paths.get(expediaStaticHotelContentPath + "/" + expediaStaticHotelContentFilename + n),
                                StandardCopyOption.REPLACE_EXISTING
                        );
                        downloadZipFilePaths.add(expediaStaticHotelContentPath + "/" + expediaStaticHotelContentFilename + n);
                    } catch (MalformedURLException ex) {
                        log.error(ex.toString() + "download error expedia static hotel content " + n + "file ");
                    } catch (IOException ex) {
                        log.error(ex.toString() + "download error expedia static hotel content " + n + "file ");
                    }
        });

        return downloadZipFilePaths;
    }

    private List<String> unzipHotelContentFile(List<String> downloadZipFilePaths) {
        List<String> hotelStaticContentPaths = new ArrayList<>();

        downloadZipFilePaths.forEach(
                downloadZipFilePath -> {
                    try {
                        FileWriter fileWriter = new FileWriter(
                                downloadZipFilePath + unzipFileExtension,
                                true
                        );
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        org.apache.commons.compress.archivers.zip.ZipFile zipFile =
                                new org.apache.commons.compress.archivers.zip.ZipFile(downloadZipFilePath);

                        byte[] bytes = new byte[unzipByteBufferSize];
                        Enumeration<?> entries = zipFile.getEntries();

                        while (entries.hasMoreElements()) {
                            ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entries.nextElement();

                            int readBytesLength;

                            InputStream inputStream = zipFile.getInputStream(zipArchiveEntry);
                            ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(inputStream);

                            if (zipArchiveInputStream.canReadEntryData(zipArchiveEntry)) {
                                while ((readBytesLength = inputStream.read(bytes)) != -1) {
                                    if (readBytesLength > 0) {
                                        bufferedWriter.write(new String(bytes));
                                    }
                                }
                            }
                            zipArchiveInputStream.close();
                            bufferedWriter.close();
                        }
                        hotelStaticContentPaths.add(downloadZipFilePath + unzipFileExtension);
                        removeHotelContentFile(downloadZipFilePath);
                    } catch (IOException ex) {
                        log.error(ex.toString() + "unzip error expedia static hotel content " + downloadZipFilePath + "file ");
                    }
                });
        return hotelStaticContentPaths;
    }


    private void removeHotelContentFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()){
            file.delete();
        }
    }

    private void updateHotelInfoStaticFileToDB(String filePath) throws IOException {
        final int onceCallSize = 900;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String hotelJsonString;
        ObjectMapper objectMapper = new ObjectMapper();

        List<Long> needHotelOtherInfoUpdateId = new ArrayList<>();

        while ((hotelJsonString = bufferedReader.readLine()) != null) {
            ExpediaHotelInfo expediaHotelInfo = objectMapper.readValue(hotelJsonString, ExpediaHotelInfo.class);
            checkExistAndUpdateOrInsert(expediaHotelInfo);
            if (!isExist(Long.valueOf(expediaHotelInfo.getPropertyId()))) {
                needHotelOtherInfoUpdateId.add(Long.valueOf(expediaHotelInfo.getPropertyId()));
            }

            if (needHotelOtherInfoUpdateId.size() % onceCallSize == 0) {
                callHotelInfoAndUpdate(needHotelOtherInfoUpdateId);
                needHotelOtherInfoUpdateId = new ArrayList<>();
            }
        }
        if (needHotelOtherInfoUpdateId.size() > 0) {
            callHotelInfoAndUpdate(needHotelOtherInfoUpdateId);
        }
        bufferedReader.close();
        removeHotelContentFile(filePath);
    }

    private void checkExistAndUpdateOrInsert(ExpediaHotelInfo expediaHotelInfo) {
        if ( isExist( Long.valueOf( expediaHotelInfo.getPropertyId() ) ) ) {
            expediaHotelInfoMapper.updateStaticInfo(expediaHotelInfo);
        }
        else {
            expediaHotelInfoMapper.insertNewStaticInfo(expediaHotelInfo);

            insertAmenity(expediaHotelInfo);

            insertImages(expediaHotelInfo);
        }
    }

    private boolean isExist(Long propertyId) {
        log.info(propertyId.toString());
        return expediaHotelInfoMapper.isExistHotel(propertyId);
    }

    private void insertAmenity(ExpediaHotelInfo expediaHotelInfo) {
        ExAmenityList exAmenityList = expediaHotelInfo.getAmenity();

        List<String> exAmenities = exAmenityList.getList();

        if(!exAmenities.isEmpty()) {
            expediaHotelAmenityMapper.insertAmenity(Long.valueOf(expediaHotelInfo.getPropertyId()), exAmenities);
        }
    }

    private void insertImages(ExpediaHotelInfo expediaHotelInfo) {
        List<ExImage> imageList = expediaHotelInfo.getImage();

        List<Map<String, String>> images = new ArrayList<>();


        if (imageList != null && imageList.size() != 0) {
            int fileSize = 0;
            for (ExImage image : imageList) {
                if (image == null) continue;

                Map imageMap = new HashMap();

                if (image.getLink().getLink1000px() != null) {
                    imageMap.put("url", image.getLink().getLink1000px().getHref());
                    imageMap.put("isSmallImg", null);
                } else {
                    imageMap.put("url", image.getLink().getLink350px().getHref());
                    imageMap.put("isSmallImg", "yes");
                }

                images.add(imageMap);
                fileSize++;

                if (fileSize == 10) { // 파일 10개씩만 저장
                    break;
                }
            }

            expediaHotelImageMapper.insertImages(Long.valueOf(expediaHotelInfo.getPropertyId()), images);
        }
    }

    private void callHotelInfoAndUpdate(List<Long> needHotelOtherInfoUpdateId) throws IOException {
        List<ExApiHotel> exApiHotels = expediaRequestService.callHotelInfoApi(needHotelOtherInfoUpdateId);
        exApiHotels.forEach(exApiHotel -> {
            updateHotelInfo(exApiHotel);
        });
    }

    private void updateHotelInfo(ExApiHotel exApiHotel) {
        Map<String, Object> param = new HashMap<>();
        param.put("star_rating", exApiHotel.getStarRating());
        param.put("address1", exApiHotel.getLocation().getAddress().getAddress1());
        param.put("address2", exApiHotel.getLocation().getAddress().getAddress2());
        param.put("province", exApiHotel.getLocation().getAddress().getProvince());
        param.put("city", exApiHotel.getLocation().getAddress().getCity());
        try { param.put("hotel_teaser", exApiHotel.getDescription().getHotelTeaser());  } catch (NullPointerException e) {param.put("hotelTeaser", null);}
        try {param.put("room_teaser", exApiHotel.getDescription().getRoomTeaser()); } catch (NullPointerException e) {param.put("roomTeaser", null);}
        try {param.put("location_teaser", exApiHotel.getDescription().getLocationTeaser()); } catch (NullPointerException e) {param.put("locationTeaser", null);}
        param.put("propertyId", exApiHotel.getPropertyId());
        param.put("name", exApiHotel.getName());
        param.put("guest_rating", exApiHotel.getGuestRating());
        param.put("guest_review_count", exApiHotel.getGuestReviewCount());
        try {param.put("distance", exApiHotel.getDistance().getDirection() + " " + exApiHotel.getDistance().getValue() + exApiHotel.getDistance().getUnit());} catch (NullPointerException e) {param.put("distance", null);}

        expediaHotelInfoMapper.updateHotelInfoFromApi(param);

    }

    public void updateHotelInfo() throws  Exception{
        // file download
        String filePath = "/Users/we/Documents/expedia/hotel db/Expedia_Static_Hotel_Content.zip";
        updateStaticInfo(filePath);
        // dynamic 정보 update( api 호출)
    }

    private void updateStaticInfo(String filePath) throws Exception {

        ZipFile zipFile = new ZipFile(filePath);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);

            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            ObjectMapper objectMapper = new ObjectMapper();

            String hotel = null;
            while ((hotel = in.readLine()) != null) {
                ExpediaHotelInfo expediaHotelInfo = objectMapper.readValue(hotel, ExpediaHotelInfo.class);

                int result = expediaHotelInfoMapper.updateStaticInfo(expediaHotelInfo);
                if (result != 1) {
                    expediaHotelInfoMapper.insertNewStaticInfo(expediaHotelInfo);
                }
            }
        }

    }

    public void getBookingList() {
        List<SearchHistory> searchHistoryList = getExpediaBookingListFromDB();
    }

    private List<SearchHistory> getExpediaBookingListFromDB() {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setVendor(HotelVendorKind.AGODA);
        searchHistory.setStatus(BookStatus.BOOKING);

        return searchHistoryMapper.getLeastSearchHistoryBySearchHistoryObj(searchHistory);
    }

}
