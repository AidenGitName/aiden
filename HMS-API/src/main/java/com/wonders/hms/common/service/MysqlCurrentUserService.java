package com.wonders.hms.common.service;

import com.wonders.hms.common.persistence.MysqlCurrentUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MysqlCurrentUserService {

    @Autowired
    MysqlCurrentUserMapper mysqlCurrentUserMapper;

    @Transactional(readOnly = true)
    public String getMysqlCurrentUserMapper() {
        return mysqlCurrentUserMapper.getCurrentUser();
    }

    @Transactional(readOnly = false)
    public String getMysqlCurrentUserMaster() {
        return mysqlCurrentUserMapper.getCurrentUser();
    }
}
