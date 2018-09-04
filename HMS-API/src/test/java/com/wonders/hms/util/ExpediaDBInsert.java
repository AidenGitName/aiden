//package com.wonders.hms.util;
//
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import com.wonders.hms.expedia.vo.staticInfo.ExAmenity;
//import com.wonders.hms.expedia.vo.staticInfo.ExAmenityList;
//import com.wonders.hms.expedia.vo.staticInfo.ExImage;
//import com.wonders.hms.expedia.vo.staticInfo.ExpediaHotelInfo;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
///*
//local db에 익스피디아 호텔 정보를 넣기 위한 util class
// */
//public class ExpediaDBInsert{
//    static MysqlDataSource mysqlDS = new MysqlDataSource();
//    static Connection con = null;
//    static PreparedStatement mainPs = null;
//    static PreparedStatement amenityPs = null;
//    static PreparedStatement imagePs = null;
//
//
//
//    static {
////        mysqlDS.setUrl("jdbc:mysql://10.102.30.115:3307/hms_dev?verifyServerCertificate=false&useSSL=false");
////        mysqlDS.setUser("webserver_rw");
////        mysqlDS.setPassword("webserver_rw!Q@W#E");
//        mysqlDS.setUrl("jdbc:mysql://localhost:3306/kms?verifyServerCertificate=false&useSSL=false");
//        mysqlDS.setUser("root");
//        mysqlDS.setPassword("root");
//
//        try {
//            con = mysqlDS.getConnection();
//            mainPs = con.prepareStatement("insert into expedia_hotel_info(property_id, name, city, state_name, " +
//                    "postal_code, address, rating,latitude, longitude, phone, fax, category, checkin, checkout, " +
//                    "fee_mandatory, fee_optional, policies, images, amenities, dining, renovation, business_amenities, " +
//                    "room, attraction, location, country) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
//                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
//                    "?, ?, ?, ?, ?, ? ) ");
//
//            amenityPs = con.prepareStatement("insert into expedia_hotel_amenity(property_id, amenity) values (?, ?)");
//
//            imagePs = con.prepareStatement("insert into expedia_hotel_image(property_id, url, is_small_img) values (?, ?, ?)");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void amenityInsert(ExpediaHotelInfo expediaHotelInfo) throws Exception {
//            if (expediaHotelInfo.getAmenity() == null) {
//                return;
//            }
//
//                ExAmenityList exAmenityList = expediaHotelInfo.getAmenity();
//
//                List<String> list = exAmenityList.getList();
//
//                if (list != null && list.size() != 0) {
//                    for (String amenity : list) {
//                        try {
//                            if (amenity == null) continue;
//
//                            amenityPs.setString(1, expediaHotelInfo.getPropertyId());
//                            amenityPs.setString(2, amenity);
//
//                            amenityPs.execute();
//
//                        } catch (Exception e) {
//                            System.out.println(expediaHotelInfo);
//                            throw e;
//                        }
//                    }
//                }
//    }
//
//    public static void imageInsert(ExpediaHotelInfo expediaHotelInfo) throws Exception {
//        if (expediaHotelInfo.getImage() == null) {
//            return;
//        }
//
//        List<ExImage> imageList = expediaHotelInfo.getImage();
//
//
//        if (imageList != null && imageList.size() != 0) {
//            int fileSize = 0;
//            for (ExImage image: imageList) {
//                try {
//                    if (image == null) continue;
//
//                    imagePs.setString(1, expediaHotelInfo.getPropertyId());
//
//                    if (image.getLink().getLink1000px() != null) {
//                        imagePs.setString(2, image.getLink().getLink1000px().getHref());
//                        imagePs.setString(3, null);
//                    } else {
//                        imagePs.setString(2, image.getLink().getLink350px().getHref());
//                        imagePs.setString(3, "yes");
//                    }
//
//                    imagePs.execute();
//                    fileSize++;
//
//                    if (fileSize == 10) { // 파일 10개씩만 저장
//                        fileSize = 0;
//                        break;
//                    }
//
//
//                } catch (MySQLIntegrityConstraintViolationException e) {
//                    continue;
//                } catch (Exception e) {
//                    System.out.println(expediaHotelInfo);
//                    throw e;
//                }
//            }
//        }
//    }
//
//
//    public static void dbinsert(ExpediaHotelInfo expediaHotelInfo) throws Exception {
//
//        try {
//            mainPs.setString(1, expediaHotelInfo.getPropertyId());
//            mainPs.setString(2, expediaHotelInfo.getName());
//            mainPs.setString(3, expediaHotelInfo.getAddress().getCity());
//            mainPs.setString(4, expediaHotelInfo.getAddress().getStateProvinceName());
//            mainPs.setString(5, expediaHotelInfo.getAddress().getPostalCode());
//            mainPs.setString(6, expediaHotelInfo.getAddress().getAddressLine1() + expediaHotelInfo.getAddress().getAddressLine2() == null? "" : " " + expediaHotelInfo.getAddress().getAddressLine2());
//            mainPs.setDouble(7, expediaHotelInfo.getRating() != null ? expediaHotelInfo.getRating().getProperty().getRating():null);
//            mainPs.setDouble(8, expediaHotelInfo.getLocation() !=null ? expediaHotelInfo.getLocation().getCoordinates().getLatitude():null);
//            mainPs.setDouble(9, expediaHotelInfo.getLocation() !=null ? expediaHotelInfo.getLocation().getCoordinates().getLongitude():null);
//            mainPs.setString(10, expediaHotelInfo.getPhone() != null ? expediaHotelInfo.getPhone() : null);
//            mainPs.setString(11, expediaHotelInfo.getFax() != null ? expediaHotelInfo.getFax() : null);
//            mainPs.setString(12, expediaHotelInfo.getCategory() != null ? expediaHotelInfo.getCategory().getName() : null);
//            mainPs.setString(13, expediaHotelInfo.getCheckin() !=null ? expediaHotelInfo.getCheckin().getBeginTime() : null);
//            mainPs.setString(14, expediaHotelInfo.getCheckout() !=null ? expediaHotelInfo.getCheckout().getTime(): null);
//            mainPs.setString(15, expediaHotelInfo.getFees() !=null ? expediaHotelInfo.getFees().getMandatory(): null);
//            mainPs.setString(16, expediaHotelInfo.getFees() !=null ? expediaHotelInfo.getFees().getOptional(): null);
//            mainPs.setString(17, expediaHotelInfo.getPolicy() !=null ? expediaHotelInfo.getPolicy().getKnowBeforeYouGo() : null);
//            mainPs.setString(18, expediaHotelInfo.getImage() !=null ? expediaHotelInfo.getImage().get(0).getLink().getLink350px().getHref() : null);
//            mainPs.setString(19, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getAmenity(): null);
//            mainPs.setString(20, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getDining() : null);
//            mainPs.setString(21, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getRenovation() : null);
//            mainPs.setString(22, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getBusinessAmenity() : null);
//            mainPs.setString(23, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getRoom() : null);
//            mainPs.setString(24, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getAttraction():null);
//            mainPs.setString(25, expediaHotelInfo.getDescription() != null ? expediaHotelInfo.getDescription().getLocation() :null);
//            mainPs.setString(26, expediaHotelInfo.getAddress().getCountryCode());
//
//            mainPs.execute();
//
//
//        } catch (Exception e){
////            e.printStackTrace();
//            System.out.println(expediaHotelInfo);
//            throw e;
//        } finally {
////            mainPs.close();
////           con.close();
//        }
//    }
//}
