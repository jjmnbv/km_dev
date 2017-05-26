package com.kmzyc.supplier.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kmzyc.supplier.dao.SensitiveDao;

@Repository("sensitiveDao")
public class SensitiveDaoImpl implements SensitiveDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getSensitiveWordByType(final int type) {
        String sql = "select KEY_WORDS from KEY_WORDS where STATUS=1 and WORDS_TYPE=?";
        List<String> result = jdbcTemplate.query(sql, ps -> {
            ps.setInt(1, type);
        }, (rs, rowNum) -> {
            return rs.getString("KEY_WORDS");
        });
        return result;
    }

}