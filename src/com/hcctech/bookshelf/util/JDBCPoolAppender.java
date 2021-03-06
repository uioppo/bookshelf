package com.hcctech.bookshelf.util;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.jdbc.JDBCAppender;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCPoolAppender extends JDBCAppender {
	
	private static ComboPooledDataSource cpds = null;
	
	private static Properties property = null;
	
	public JDBCPoolAppender(){
		super();
//		property = new Properties();
//		try
//        {
//            property.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
//            this.databaseURL = property.getProperty("log.url");
//            this.databaseUser = property.getProperty("log.username");
//            this.databasePassword = property.getProperty("log.password");
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
		if(cpds==null){
			cpds = new ComboPooledDataSource();
			
			property = new Properties();
			try {
				property.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
				cpds.setDriverClass(property.getProperty("log.driverClassName"));
				cpds.setJdbcUrl(property.getProperty("log.url"));
				cpds.setUser(property.getProperty("log.username"));
				cpds.setPassword(property.getProperty("log.password"));
				cpds.setMinPoolSize(5);
				cpds.setMaxPoolSize(20);
				cpds.setMaxIdleTime(1800);
				cpds.setAcquireIncrement(2);
				cpds.setMaxStatements(20);
				cpds.setInitialPoolSize(20);
				cpds.setIdleConnectionTestPeriod(1800);
				cpds.setAcquireRetryAttempts(30);
				cpds.setAcquireRetryDelay(1000);
				cpds.setBreakAfterAcquireFailure(false);
				cpds.setTestConnectionOnCheckout(true);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	protected void closeConnection(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Connection getConnection() throws SQLException {
//		return super.getConnection();
		return cpds.getConnection();
	}
}
