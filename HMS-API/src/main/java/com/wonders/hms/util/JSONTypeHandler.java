package com.wonders.hms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JSONTypeHandler extends BaseTypeHandler<Object> {
    private ObjectMapper mapper;

    public JSONTypeHandler() {
        mapper = new ObjectMapper();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String objectJsonString = new String();
        try {
            objectJsonString = mapper.writeValueAsString(parameter);
        } catch (IOException e) {
        }
        ps.setObject(i, objectJsonString);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object d = rs.getObject(columnName);

        if(d == null) return d;
        try {
            return mapper.readValue(d.toString(), Object.class);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object d = rs.getObject(columnIndex);
        if(d == null) return d;
        try {
            return mapper.readValue(d.toString(), Object.class);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object d = cs.getObject(columnIndex);
        if(d == null) return d;
        try {
            return mapper.readValue(d.toString(), Object.class);
        }catch (IOException e){
            return null;
        }
    }

}
