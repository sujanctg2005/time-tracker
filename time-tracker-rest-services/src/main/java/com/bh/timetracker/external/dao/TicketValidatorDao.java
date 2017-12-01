package com.bh.timetracker.external.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class TicketValidatorDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static DataSource datasource;
	@Value("${ticketValidator.datasource.url}")
	private String jdbcURL;
	@Value("${ticketValidator.datasource.username}")
	private String username;
	@Value("${ticketValidator.datasource.password}")
	private String pssword;

	@PostConstruct
	public void initialize() {
		getDataSource();
	}

	public DataSource getDataSource() {
		
		logger.debug("db url: {}, user:{}, password: {},", jdbcURL,username,pssword);
		if (datasource == null) {
			HikariConfig config = new HikariConfig();

			config.setJdbcUrl(jdbcURL);
			config.setUsername(username);
			config.setPassword(pssword);

			config.setMaximumPoolSize(10);
			config.setAutoCommit(false);
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			datasource = new HikariDataSource(config);
		
		}
		return datasource;
	}

	public String[] getTicketInfo(String ticketTableInfo, String ticketId) {
		
	  String tableInfo[]= ticketTableInfo.split(",");
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String[]  data= new String [2];
		try {
			
			DataSource dataSource = getDataSource();
			connection = dataSource.getConnection();
			String sql = String.format("SELECT %s, %s FROM %s    where %s='%s'", tableInfo[1],tableInfo[2],tableInfo[0], tableInfo[1],ticketId);
			logger.info("sql :%s",sql );
			pstmt = connection.prepareStatement(sql);

			logger.debug("The Connection Object is of Class: " + connection.getClass());

			resultSet = pstmt.executeQuery();
			while (resultSet.next())
			{
				data[0]=resultSet.getString(1);
				data[1]= resultSet.getString(2);
					
			}
			
			connection.close();
		} catch (Exception e) {			
			logger.error("Fail to validate tiket", e);
		}finally {
			
		}
		return data;
	}
	
	public static void main(String[] args) {

		

	}

}
