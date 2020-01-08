package com.smProject.dao.util;

import java.io.IOException;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClobJsonTypeHandler extends BaseTypeHandler<CamelMap> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, CamelMap parameter, JdbcType jdbcType)
			throws SQLException {
		final String parameterString = mapToJson(parameter);
		StringReader reader = new StringReader(parameterString);
		ps.setCharacterStream(i, reader, parameterString.length());
	}

	public CamelMap getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = "";
		Clob clob = rs.getClob(columnName);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1L, size);
		}

		return jsonToMap(value);
	}

	public CamelMap getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = "";
		Clob clob = rs.getClob(columnIndex);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1L, size);
		}

		return jsonToMap(value);
	}

	public CamelMap getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = "";
		Clob clob = cs.getClob(columnIndex);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1L, size);
		}

		return jsonToMap(value);
	}

	private CamelMap jsonToMap(String from) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(from, CamelMap.class);
		} catch (IOException e) {
			throw new Error();
		}
	}

	private String mapToJson(CamelMap from) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(from);
		} catch (IOException e) {
			throw new Error();
		}
	}
}