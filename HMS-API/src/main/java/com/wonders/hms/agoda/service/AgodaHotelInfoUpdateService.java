package com.wonders.hms.agoda.service;

import com.wonders.hms.agoda.persistence.AgodaHotelInfoMapper;
import com.wonders.hms.agoda.vo.AgodaHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class AgodaHotelInfoUpdateService {

    @Value("${agoda.dataFile.url}")
    private String dataFileUrl;

    @Autowired
    private AgodaHotelInfoMapper agodaHotelInfoMapper;

    @Value("${folder.filedownload.tmp}")
    private String filePath;

    private final static String AGODA_DATA_FILE_NAME = "agodaDataFile.zip";

    private List<AgodaHotel> agodaDBHotelList;

    @PostConstruct
    private void init() {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void hotelInfoUpdate() throws Exception {
        log.debug("agoda hotel info updating started...");
        checkFileExistence();

        log.debug("agoda hotel static file downloading...");
        fileDownload();

        log.debug("read agoda hotel info from db...");
        getAgodaHotelDBInfo();

        log.debug("agoda hotel info updating...");
        compare();
        log.debug("agoda hotel static info updated!!!");
    }

    private void checkFileExistence() {
        File file = new File(filePath + "/" + AGODA_DATA_FILE_NAME);
        if (file.exists()) {
            file.delete();
            log.debug("agoda data file was deleted");
        }
    }

    private void getAgodaHotelDBInfo() {
        agodaDBHotelList = agodaHotelInfoMapper.getAllHotels();
    }

    private void compare() throws Exception {
        log.debug("file analyzing...");
        ZipFile zipFile = new ZipFile(filePath + "/" + AGODA_DATA_FILE_NAME);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream in = zipFile.getInputStream(entry);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine(); // 컬럼 행 읽어들이기

            while ((line = br.readLine()) != null) {
                String[] hotel = splitByComma(line);
                removeQuotaionMark(hotel);
                AgodaHotel agodaHotel = createAgodaHotelObject(hotel);

                if (agodaHotel == null) {
                    continue;
                }

                if (isChangedHotel(agodaHotel)) {
                    agodaHotelInfoMapper.updateHotel(agodaHotel);
                }
            }
        }
    }

    private boolean isChangedHotel(AgodaHotel newAgodaHotel) {
        AgodaHotel existedAgodaHotel = agodaDBHotelList.stream()
                .filter(agodaHotel -> agodaHotel.getHotelId().compareTo(newAgodaHotel.getHotelId()) == 0)
                .findAny()
                .orElse(null);

        if (existedAgodaHotel == null) {
            agodaHotelInfoMapper.insertHotel(newAgodaHotel);
            return false;
        }

        if (existedAgodaHotel.compare(newAgodaHotel) == 0) {
            return false;
        }

        return true;
    }

    private String[] splitByComma(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    private AgodaHotel createAgodaHotelObject(String[] hotel) {
        AgodaHotel agodaHotel = new AgodaHotel();
        try {
            agodaHotel.setHotelId(Long.parseLong(hotel[0]));
            agodaHotel.setChainId(Long.parseLong(hotel[1]));
            agodaHotel.setChainName(hotel[2]);
            agodaHotel.setBrandId(Long.parseLong(hotel[3]));
            agodaHotel.setBrandName(hotel[4]);
            agodaHotel.setHotelName(hotel[5]);
            agodaHotel.setHotelFormerlyName(hotel[6]);
            agodaHotel.setHotelTranslatedName(hotel[7]);
            agodaHotel.setAddressLine1(hotel[8]);
            agodaHotel.setAddressLine2(hotel[9]);
            agodaHotel.setZipcode(hotel[10]);
            agodaHotel.setCity(hotel[11]);
            agodaHotel.setState(hotel[12]);
            agodaHotel.setCountry(hotel[13]);
            agodaHotel.setCountryIsoCode(hotel[14]);
            agodaHotel.setStarRating(Double.parseDouble(hotel[15]));
            agodaHotel.setLongitude(Double.parseDouble(hotel[16]));
            agodaHotel.setLatitude(Double.parseDouble(hotel[17]));
            agodaHotel.setUrl(hotel[18]);
            agodaHotel.setCheckin(hotel[19]);
            agodaHotel.setCheckout(hotel[20]);
            agodaHotel.setNumberRooms("".equals(hotel[21]) ? 0 : Integer.parseInt(hotel[21]));
            agodaHotel.setNumberFloors("".equals(hotel[22]) ? 0 : Integer.parseInt(hotel[22]));
            agodaHotel.setYearOpened(hotel[23]);
            agodaHotel.setYearEnovated(hotel[24]);
            agodaHotel.setPhoto1(hotel[25]);
            agodaHotel.setPhoto2(hotel[26]);
            agodaHotel.setPhoto3(hotel[27]);
            agodaHotel.setPhoto4(hotel[28]);
            agodaHotel.setPhoto5(hotel[29]);
            agodaHotel.setOverview(hotel[30]);
            agodaHotel.setRatesFrom(hotel[31]);
            agodaHotel.setContinentId(Long.parseLong(hotel[32]));
            agodaHotel.setContinentName(hotel[33]);
            agodaHotel.setCityId(Long.parseLong(hotel[34]));
            agodaHotel.setCountryId(Long.parseLong(hotel[35]));
            agodaHotel.setNumberOfReviews(Integer.parseInt(hotel[36]));
            agodaHotel.setRatingAverage(Double.parseDouble(hotel[37]));
            agodaHotel.setRatesCurrency(hotel[38]);
            agodaHotel.setRatesFromExclusive(hotel[39]);
            agodaHotel.setAccommodationType(hotel[40]);

            return agodaHotel;
        } catch (Exception e) {
        }

        return null;
    }

    private void removeQuotaionMark(String[] hotel) {
        for (int i = 0; i < hotel.length; i++) {
            if (hotel[i] instanceof String) {
                hotel[i] = hotel[i].replace("\"", "");
            }
        }
    }

    private void fileDownload() throws Exception {
        InputStream in = new URL(dataFileUrl).openStream();
        Files.copy(in, Paths.get(filePath + "/" + AGODA_DATA_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);

        log.debug("agoda file downloaded");
    }


}
