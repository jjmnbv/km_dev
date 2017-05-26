package com.pltfm.app.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;

@SuppressWarnings("unchecked")
public class OrderEnumTypeHandler extends BaseTypeHandler implements TypeHandler {

  private Class type;

  public OrderEnumTypeHandler(Class type) {
    this.type = type;
  }

  public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType)
      throws SQLException {
    ps.setInt(i, ((Enum) parameter).ordinal() + 1);
  }

  public Object getResult(ResultSet rs, String columnName) throws SQLException {
    Object s = rs.getInt(columnName);
    if (rs.wasNull()) {
      return null;
    } else {
      return type.getEnumConstants()[(Integer) s - 1];
    }
  }

  public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
    Object s = rs.getInt(columnIndex);
    if (rs.wasNull()) {
      return null;
    } else {
      return type.getEnumConstants()[(Integer) s - 1];
    }
  }

  public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
    Object s = cs.getInt(columnIndex);
    if (cs.wasNull()) {
      return null;
    } else {
      return type.getEnumConstants()[(Integer) s - 1];
    }
  }

  public Object valueOf(String s) {
    return Enum.valueOf(type, (String) s);
  }

}
