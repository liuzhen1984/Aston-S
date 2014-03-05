package com.silveroaklabs.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public interface IBaseDao {

	JdbcTemplate getJdbcTemplate();

	Integer save(String tableName, Map<String, Object> valueMap);

	String getInsertStr(String tableName, Map<String, Object> valueMap);

	void updateAttr(String tableName, Map<String, Object> valueMap,
                    Map<String, Object> whereMap);

	void updateAttr(String tableName, Map<String, Object> valueMap,
                    Map<String, Object> whereMap, String whereStr);

	Map<String, Object> queryForMap(String sqlStr, Object[] params);

	List<Map<String, Object>> getAll(String tableName, String orderByStr);

	long getCnt(String sql, Object... args);

}
