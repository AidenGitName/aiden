package com.wonders.hms.user.persistence;

import com.wonders.hms.user.vo.SearchHistory;
import com.wonders.hms.wonder.type.HotelVendorKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class searchHistoryMapperTest {
    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Test
    public void getSearchHistoryBySearchHistoryObj() {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setVendor(HotelVendorKind.AGODA);

        List<SearchHistory> result = searchHistoryMapper.getLeastSearchHistoryBySearchHistoryObj(searchHistory);

        assertThat(result.size(), is(greaterThan(0)));
    }

    @Test
    public void getSearchHistoryWithUuidTest() {
        String uuid = "8837d291ffe54961a11e59ddc3793f97";
        List<SearchHistory> searchHistory = searchHistoryMapper.getSearchHistoryWithUuid(uuid);
        System.out.println(searchHistory);
    }
}
