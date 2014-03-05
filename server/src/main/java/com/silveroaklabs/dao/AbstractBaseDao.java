package com.silveroaklabs.dao;

import com.silveroaklabs.util.StringUtil;
import com.silveroaklabs.util.UpdateCarrier;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBaseDao<E> implements IBaseDao  {
	
	private Logger logger = Logger.getLogger(AbstractBaseDao.class);
	
	protected JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(BasicDataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public Integer save(String tableName, Map<String, Object> valueMap) {
		if(valueMap == null || valueMap.size() <= 0) return null;
		
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer colStr = new StringBuffer();
		StringBuffer valueStr = new StringBuffer();
		
		colStr.append("(");
		valueStr.append("values(");
		
		List<Object> valueList = new ArrayList<Object>();
		
		sqlStr.append(" insert into `" + tableName + "`");
		int index = 0;
		for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
			
			if(index != 0) {
				colStr.append(",");
				valueStr.append(",");
			}
			
			colStr.append("`" + entry.getKey() + "`");
			valueStr.append("?");
			valueList.add(entry.getValue());
			
			index ++;
		}
		
		colStr.append(") ");
		valueStr.append(") ");
		sqlStr.append(colStr);
		sqlStr.append(valueStr);
		
        System.out.println(sqlStr.toString());
		Long priKey = insertAndGetKey(sqlStr.toString(), valueList.toArray());
		
		if(priKey == null) {
			return null;
		}else{
			return priKey.intValue();
		}
	}
	
	@Override
	public String getInsertStr(String tableName, Map<String, Object> valueMap) {
		
		if(valueMap == null || valueMap.size() <= 0) return null;
		
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer colStr = new StringBuffer();
		StringBuffer valueStr = new StringBuffer();
		
		colStr.append("(");
		valueStr.append("values(");
		
		sqlStr.append(" insert into " + tableName + "");
		int index = 0;
		for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
			
			if(index != 0) {
				colStr.append(",");
				valueStr.append(",");
			}
			
			colStr.append(entry.getKey());
			valueStr.append("'" + entry.getValue() + "'");
			
			index ++;
		}
		
		colStr.append(") ");
		valueStr.append(") ");
		sqlStr.append(colStr);
		sqlStr.append(valueStr);
		
		return sqlStr.toString();
	}
	
	/**
	 * 
	 * 增加并且获取主键
	 * @param sql  sql语句
	 * @param params 参数列表
	 * @return 主键
	 */
	public Long insertAndGetKey(final String sql, final Object... params) {

		final KeyHolder key = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
				PreparedStatement.RETURN_GENERATED_KEYS);
				if(params != null){
					for(int i=0;i<params.length;i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
				return ps;
			}

		}, key);

		return (Long) key.getKey();
	}

	
	@Override
	public void updateAttr(String tableName, Map<String, Object> valueMap, Map<String, Object> whereMap){
		if(valueMap == null || valueMap.size() <= 0) return;
		
		StringBuffer sqlStr = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		
		sqlStr.append(" update " + tableName + " ");
		sqlStr.append(" set  ");
		
		int index = 0;
		for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
			
			if(index != 0) {
				sqlStr.append(",");
			}
			if(entry.getValue() instanceof UpdateCarrier) {
				sqlStr.append(entry.getKey() + " = " + ((UpdateCarrier)entry.getValue()).getExpression() + " ? ");
				valueList.add(((UpdateCarrier)entry.getValue()).getValue());
			}else {
				sqlStr.append(entry.getKey() + " = ? ");
				valueList.add(entry.getValue());
			}
            index++;//当修改字段是会报sql语法错误
		}
		
		sqlStr.append(" where 1 = 1 ");
		
		if(whereMap != null && whereMap.size() > 0) {
			for(Map.Entry<String, Object> entry : whereMap.entrySet()) {
				sqlStr.append(" and " + entry.getKey() + " = ? ");
				valueList.add(entry.getValue());
			}
		}
		
		logger.info(sqlStr);
		
		jdbcTemplate.update(sqlStr.toString(), valueList.toArray());
	}
	
	@Override
	public void updateAttr(String tableName, Map<String, Object> valueMap, Map<String, Object> whereMap, String whereStr){
		if(valueMap == null || valueMap.size() <= 0) return;
		
		StringBuffer sqlStr = new StringBuffer();
		List<Object> valueList = new ArrayList<Object>();
		
		sqlStr.append(" update " + tableName + " ");
		sqlStr.append(" set  ");
		
		int index = 0;
		for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
			
			if(index != 0) {
				sqlStr.append(",");
			}
			
			sqlStr.append(entry.getKey() + " = ? ");
			valueList.add(entry.getValue());
			
			index ++;
			
		}
		
		sqlStr.append(" where 1 = 1 ");
		
		if(whereMap != null && whereMap.size() > 0) {
			for(Map.Entry<String, Object> entry : whereMap.entrySet()) {
				sqlStr.append(" and " + entry.getKey() + " = ? ");
				valueList.add(entry.getValue());
			}
		}
		
		if(whereStr != null) {
			sqlStr.append(whereStr);
		}
		
		logger.info(sqlStr);
		
		jdbcTemplate.update(sqlStr.toString(), valueList.toArray());
	}
	
	@Override
	public Map<String, Object> queryForMap(String sqlStr, Object... params){
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlStr.toString(), params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getAll(String tableName, String orderByStr){
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select * from " + tableName + " where 1 = 1 ");
		
		if(orderByStr != null) {
			sqlStr.append(" order by " + orderByStr + " ");
		}
		
		return jdbcTemplate.queryForList(sqlStr.toString());
	}

	@Override
	public long getCnt(String sql, Object... args) {
		return jdbcTemplate.queryForLong(sql, args);
	}

	public Integer saveForDup(String tableName, Map<String, Object> valueMap, Map<String, Object> updateValueMap) {
		if(valueMap == null || valueMap.size() <= 0) return null;
		
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer colStr = new StringBuffer();
		StringBuffer valueStr = new StringBuffer();
		
		colStr.append("(");
		valueStr.append("values(");
		
		List<Object> valueList = new ArrayList<Object>();
		
		sqlStr.append(" insert into " + tableName + "");
		int index = 0;
		for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
			
			if(index != 0) {
				colStr.append(",");
				valueStr.append(",");
			}
			
			colStr.append(entry.getKey());
			valueStr.append("?");
			valueList.add(entry.getValue());
			
			index ++;
		}
		
		colStr.append(") ");
		valueStr.append(") ");
		sqlStr.append(colStr);
		sqlStr.append(valueStr);
		
		index = 0;
		if(updateValueMap != null && updateValueMap.size() > 0) {
			sqlStr.append(" on duplicate key update ");
			for(Map.Entry<String, Object> entry : updateValueMap.entrySet()) {
				if(index != 0) {
					sqlStr.append(",");
				}
				
				if(entry.getValue() instanceof UpdateCarrier) {
					sqlStr.append(entry.getKey() + " = " + ((UpdateCarrier)(entry.getValue())).getExpression() + " ? ");
					valueList.add(((UpdateCarrier)(entry.getValue())).getValue());
				}else {
					sqlStr.append(entry.getKey() + " = ? ");
					valueList.add(entry.getValue());
				}
				
				
				index ++;
			}
		}
		
		Long priKey = insertAndGetKey(sqlStr.toString(), valueList.toArray());
		
		if(priKey == null) {
			return null;
		}else{
			return priKey.intValue();
		}
	}

	/**
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> List<T> findList(String sql, final Class<?> clazz, Object... args) {
		return jdbcTemplate.query(sql, new RowMapper<T>(){
			@SuppressWarnings("unchecked")
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					Object obj = clazz.newInstance();
					Field[] fields = clazz.getDeclaredFields();
					for(Field field : fields) {
						Class<?> type = field.getType();
						
						String fieldName = field.getName();
						Object value = rs.getObject(fieldName);
						String methodName = StringUtil.getSetMethod(fieldName);
						Method method = clazz.getMethod(methodName, type);
						method.invoke(obj, value);
					}
					return (T)obj;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}}, 
			
			args);
		
	}
}
