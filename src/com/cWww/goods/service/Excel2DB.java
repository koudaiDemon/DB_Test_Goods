package com.cWww.goods.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.cWww.dbutils.DBUtils;
import com.cWww.excel.TypeTools;

public class Excel2DB {

	private static final String CRLF = "\n\t";
	
	public void insertDatas(File file,String tablename){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn =DBUtils.getConn();
			String sql = "";
			Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			if(tablename.equals("tb_goods")){
				sql = "insert into tb_goods(id,goodsname,price,offset,time,counts,cid) values(?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				for(int i = 1 ;i < rows ; i++){
					String s1 = sheet.getCell(0, i).getContents();//编号
					String s2 = sheet.getCell(1, i).getContents();//商品名
					String s3 = sheet.getCell(2, i).getContents();//单价
					String s4 = sheet.getCell(3, i).getContents();//折扣
					String s5 = sheet.getCell(4, i).getContents();//时间
					String s6 = sheet.getCell(5, i).getContents();//库存
					String s7 = sheet.getCell(6, i).getContents();//类别
					ps.setInt(1,TypeTools.getInt(s1));
					ps.setString(2,s2);
					ps.setBigDecimal(3,TypeTools.getBigDecimal(s3));
					ps.setDouble(4,TypeTools.getDouble(s4));
					if(TypeTools.getDate(s5) == null){
						ps.setObject(5,null);
					}else{
						ps.setDate(5,new java.sql.Date(TypeTools.getDate(s5).getTime()));
					}
					ps.setInt(6,TypeTools.getInt(s6));
					ps.setInt(7,TypeTools.getInt(s7));
					ps.addBatch();
				}
			}else{
				sql = "insert into tb_type(id,cname) values(?,?)";
				ps = conn.prepareStatement(sql);
				for(int i = 1 ; i < rows ; i++){
					String s1 = sheet.getCell(0, i).getContents();//编号
					String s2 = sheet.getCell(1, i).getContents();//类别名
					ps.setInt(1, TypeTools.getInt(s1));
					ps.setString(2, s2);
					ps.addBatch();
				}
			}
			int[] counts = ps.executeBatch();
			System.out.println(counts.length);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTableFromExcel(File file,String tablename){
		Connection conn = null;
		PreparedStatement ps = null;
		Workbook wb  =null;
		try {
			wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			Cell[] cells = sheet.getRow(0);
			StringBuffer sb = new StringBuffer();
			sb.append("create table ").append(tablename).append("(").append(CRLF);
			
			for(int i = 0 ; i < cells.length ; i++){
				String columnName = cells[i].getContents();
				switch(columnName){
				case "id":
					sb.append(columnName).append(" ").append("int primary key AUTO_INCREMENT,").append(CRLF);
					break;
				case "goodsname":
					sb.append(columnName).append(" ").append("varchar(255),").append(CRLF);
					break;
				case "price":
					sb.append(columnName).append(" ").append("decimal,").append(CRLF);
					break;
				case "offset":
					sb.append(columnName).append(" ").append("double,").append(CRLF);
					break;
				case "time":
					sb.append(columnName).append(" ").append("Date,").append(CRLF);
					break;
				case "counts":
					sb.append(columnName).append(" ").append("int,").append(CRLF);
					break;
				case "cid":
					sb.append(columnName).append(" ").append("int ").append("references ").append("tb_type(id)").append(CRLF);
					break;
				case "cname":
					sb.append(columnName).append(" ").append("varchar(32)").append(CRLF);
					break;
					default :
						break;
				}
			}
			sb.append(");");
			conn = DBUtils.getConn();
			ps = conn.prepareStatement(sb.toString());
			boolean f = ps.execute();
			if(!f){
				System.out.println(tablename+"创建成功！");
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(ps!=null){ps.close();}
				if(conn!=null){conn.close();}
				if(wb!=null){wb.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Excel2DB e2db = new Excel2DB();
//		e2db.createTableFromExcel(new File("src/类别表.xls"), "tb_type");
//		e2db.createTableFromExcel(new File("src/商品表.xls"), "tb_goods");
//		e2db.insertDatas(new File("src/类别表.xls"), "tb_type");
		e2db.insertDatas(new File("src/商品表.xls"), "tb_goods");
	}
	
}
