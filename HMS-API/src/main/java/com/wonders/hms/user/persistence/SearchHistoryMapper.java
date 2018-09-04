package com.wonders.hms.user.persistence;

import com.wonders.hms.user.type.BookStatus;
import com.wonders.hms.user.vo.MyPageHistory;
import com.wonders.hms.user.vo.SearchHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchHistoryMapper {

    public void insertSearchHistory(SearchHistory searchHistory);

    public void updateSearchHistory(@Param("status") BookStatus status, @Param("reservationId") Long reservationId,
                                    @Param("reservationVendorUrl") String reservationVendorUrl,
                                    @Param("uuid") String uuid);

    public void updateBookingDotComStatus(@Param("uuid") String uuid);

    public SearchHistory getBookingDotComByBookingStatusSearchHistory(
            @Param("uuid") String uuid,
            @Param("bookingDotComStatus") Boolean bookingDotComStatus
    );

    public List<SearchHistory> getSearchHistory(Long mId);

    public List<SearchHistory> getSearchHistoryWithUuid(String uuid);

    public List<SearchHistory> getAllSearchHistory();

    public List<SearchHistory> getLeastSearchHistoryBySearchHistoryObj(SearchHistory searchHistory);

    public List<MyPageHistory> getPassedBook(Long mId);

    public List<MyPageHistory> getProcessingBook(Long mId);

    public List<SearchHistory> getLeastHistory(SearchHistory searchHistory);

    public List<SearchHistory> getLeastBookedHistoryForAgoda();

    public void updateReservationVendorUrl(SearchHistory searchHistory);

    public SearchHistory getExpediaByStatus(@Param("uuid") String uuid,
                                            @Param("expediaStatus") Boolean expediaStatus);

    public void updateExpediaStatus(String uuid);
}
