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
 * DBCP���ݿ����ӵĹ�����
 * ����
 * 1.mysql����
 * 2.dbcp��ز��
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
			//��������Դ����
			bds = new BasicDataSource();
			//���������ļ�����ȡ������Ϣ
			Properties props = new Properties();
			//�������ݿ������Ϣ
			props.load(DBUtils.class.getResourceAsStream("jdbc.properties"));
			driverClass = props.getProperty("driver");
			url = props.getProperty("url");
			username = props.getProperty("user");
			password = props.getProperty("password");
			
			initSize = Integer.parseInt(props.getProperty("initSize"));
			maxSize = Integer.parseInt(props.getProperty("maxSize"));
			maxIdle = Integer.parseInt(props.getProperty("maxIdle"));
			maxWait = Long.parseLong(props.getProperty("maxWait"));
			
			//����������·��
			bds.setDriverClassName(driverClass);
			//����url
			bds.setUrl(url);
			//�����û���
			bds.setUsername(username);
			//��������
			bds.setPassword(password);
			
			//���ó�ʼ��������
			bds.setInitialSize(initSize);
			//�������������
			bds.setMaxTotal(maxSize);
			//�������������
			bds.setMaxIdle(maxIdle);
			//�������ȴ�ʱ��
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
	
	//��װ��Դ���յķ���
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
	 * ͨ�õ���ɾ�Ĳ���
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
	 * ��ѯ����
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
	 * ��ѯ����
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
	 * ��������������ѯ
	 * @param sql
	 * @param call
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryRandom(String sql,CallBack<T> call,Object... params){
		
		return null;
	}
	
	/**
	 * JDK1.8����ʹ�ýӿ��е�Ĭ�Ϸ���
	 * �ص�
	 * @author Administrator
	 *
	 * @param <T>
	 */
	public interface CallBack<T>{
		default List<T> getDatas(ResultSet rs){return null;}
		default T getData(ResultSet rs){return null;}
	}
	
}
