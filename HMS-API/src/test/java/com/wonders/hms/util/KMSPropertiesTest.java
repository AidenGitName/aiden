package com.wonders.hms.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KMSPropertiesTest {

    @Autowired
    private KMSProperties kmsProperties;

    @Test
    public void kmsPropertiesApiTest() {
        assert "hms-db-cluster-dev.cluster-c9lmeckkvmw5.ap-northeast-2.rds.amazonaws.com"
                .equals(kmsProperties.getMessage().getDatabase().getCluster().getHost());
    }

}
