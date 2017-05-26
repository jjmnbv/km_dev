package com.kmzyc.b2b.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.SensitiveDao;

@Repository("sensitiveDao")
public class SensitiveDaoImpl implements SensitiveDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getSensitiveWordByType(final int type) {
        String sql = "select KEY_WORDS from KEY_WORDS where STATUS=1 and WORDS_TYPE=?";

        List<String> result = jdbcTemplate.query(sql, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, type);
            }
        }, new RowMapper() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("KEY_WORDS");
            }
        });
        return result;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
