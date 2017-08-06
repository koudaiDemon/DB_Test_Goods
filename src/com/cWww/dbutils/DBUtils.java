package com.cWww.dbutils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * DBCP数据库连接的工具类
 * 依赖
 * 1.mysql驱动
 * 2.dbcp相关插件
 * @author Administrator
 *
 */
public class DBUtils {

	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	
	private static int initSize;
	private static int maxSize;
	private static int maxIdle;
	private static long maxWait;
	
	private static BasicDataSource bds;
	
	static{
		
	}

	private static void init(){
		try {
			//创建数据源对象
			bds = new BasicDataSource();
			//加载属性文件，获取属性信息
			Properties props = new Properties();
			//连接数据库基本信息
			props.load(DBUtils.class.getResourceAsStream("jdbc.properties"));
			driverClass = props.getProperty("driver");
			url = props.getProperty("url");
			username = props.getProperty("user");
			password = props.getProperty("password");
			
			initSize = Integer.parseInt(props.getProperty("initSize"));
			maxSize = Integer.parseInt(props.getProperty("maxSize"));
			maxIdle = Integer.parseInt(props.getProperty("maxIdle"));
			maxWait = Long.parseLong(props.getProperty("maxWait"));
			
			//设置驱动类路径
			bds.setDriverClassName(driverClass);
			//设置url
			bds.setUrl(url);
			//设置用户名
			bds.setUsername(username);
			//设置密码
			bds.setPassword(password);
			
			//设置初始化连接数
			bds.setInitialSize(initSize);
			//设置最大连接数
			bds.setMaxTotal(maxSize);
			//设置最大闲置数
			bds.setMaxIdle(maxIdle);
			//设置最大等待时间
			bds.setMaxWaitMillis(maxWait);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Connection getConn(){
		try {
			if(bds == null){
				bds = new BasicDataSource();
				init();
			}
			return bds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//封装资源回收的方法
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		try {
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用的增删改操作
	 * @param conn
	 * @param sql
	 * @param objs
	 * @return
	 */
	public static boolean execUpdate(Connection conn,String sql,Object... objs){
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0 ; i<objs.length ; i++){
				ps.setObject(i+1, objs[i]);
			}
			int i = ps.executeUpdate();
			return i>0?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 查询所有
	 * @param sql
	 * @param call
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryList(String sql,CallBack<T> call,Object...params){
		Connection conn = DBUtils.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0 ; i < params.length ; i++){
				ps.setObject(i+1, params[i]);
			}
			return call.getDatas(ps.executeQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 查询单个
	 * @param sql
	 * @param call
	 * @param params
	 * @return
	 */
	public static <T> T queryOne(String sql,CallBack<T> call,Object... params){
		Connection conn = DBUtils.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0 ; i < params.length ; i++){
				ps.setObject(i+1, params[i]);
			}
			return call.getData(ps.executeQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 按照任意条件查询
	 * @param sql
	 * @param call
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryRandom(String sql,CallBack<T> call,Object... params){
		
		return null;
	}
	
	/**
	 * JDK1.8可以使用接口中的默认方法
	 * 回调
	 * @author Administrator
	 *
	 * @param <T>
	 */
	public interface CallBack<T>{
		default List<T> getDatas(ResultSet rs){return null;}
		default T getData(ResultSet rs){return null;}
	}
	
}
