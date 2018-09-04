package com.wonders.hms.common.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlCurrentUserTest {
    @Autowired
    MysqlCurrentUserService mysqlCurrentUserService;

    @Test
    public void readOnlyDataSourceTest() {
        String readOnlyUser = mysqlCurrentUserService.getMysqlCurrentUserMapper();
        System.out.println(readOnlyUser);
        assert readOnlyUser.equals("webserver_ro@%");
    }

    @Test
    public void readWriteDataSourceTest() {
        String readWriteUser = mysqlCurrentUserService.getMysqlCurrentUserMaster();
        System.out.println(readWriteUser);
        assert readWriteUser.equals("webserver_rw@%");
    }
}
