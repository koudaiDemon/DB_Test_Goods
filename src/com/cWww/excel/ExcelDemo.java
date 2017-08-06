package com.cWww.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
/**
 * ����JXLʵ�ֶ���excel-2000/2003�汾���ļ����ж�д����
 * @author Administrator
 *
 */
public class ExcelDemo {
	
	public List<Goods> read(File file){
		List<Goods> list = new ArrayList<>();
		Goods good = null;
		Workbook workbook = null;
		try {
			//����һ��������
			workbook = Workbook.getWorkbook(file);
			//��ȡ���б�
//			Sheet[] sheet = workbook.getSheets();
			//��ȡָ�������ı�
			Sheet sheet = workbook.getSheet(0);
			//��ȡָ�����Ƶı�
//			workbook.getSheet("Sheet1");
			//��ȡ������
//			int rows = sheet.getRows();
//			System.out.println("������:"+rows);
			//��ȡָ���ĵ�Ԫ�����
//			Cell cell = sheet.getCell(1, 0);
//			Cell[] cells = sheet.getRow(0);
//			String str = cell.getContents();
//			System.out.println(str);
			
			int rows = sheet.getRows();
			for(int i = 1; i < rows ; i++){
				good = new Goods();
				String s1 = sheet.getCell(0, i).getContents();//���
				String s2 = sheet.getCell(1, i).getContents();//��Ʒ��
				String s3 = sheet.getCell(2, i).getContents();//����
				String s4 = sheet.getCell(3, i).getContents();//�ۿ�
				String s5 = sheet.getCell(4, i).getContents();//ʱ��
				String s6 = sheet.getCell(5, i).getContents();//���
				good.setGno(TypeTools.getInt(s1));
				good.setGname(s2);
				good.setPrice(TypeTools.getBigDecimal(s3));
				good.setOffset(TypeTools.getDouble(s4));
				good.setTime(TypeTools.getDate(s5));
				good.setCount(TypeTools.getInt(s6));
				list.add(good);
			}
			
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		} finally{
			if(workbook != null){
				workbook.close();
			}
		}
		return list;
	}
	
	public void createExcel(List<Goods> goods,File dir){
		//����ϵͳʱ������һ��excel�ļ�
		File file = new File(dir,System.currentTimeMillis()+".xls");
		WritableWorkbook wwb = null;
		try {
			//����һ����Э������
			wwb = Workbook.createWorkbook(file);
			//��ȡһ����д�ı�
			WritableSheet sheet = wwb.createSheet("��Ʒ��Ϣ��", 0);
			WritableCell c1 = new Label(0,0,"���");
			WritableCell c2 = new Label(1,0,"��Ʒ��");
			WritableCell c3 = new Label(2,0,"����");
			WritableCell c4 = new Label(3,0,"�ۿ�");
			WritableCell c5 = new Label(4,0,"�ϼ�ʱ��");
			WritableCell c6 = new Label(5,0,"���");
			sheet.addCell(c1);
			sheet.addCell(c2);
			sheet.addCell(c3);
			sheet.addCell(c4);
			sheet.addCell(c5);
			sheet.addCell(c6);
			
			//�������
			for(int i = 0 ; i < goods.size() ; i++){
				c1 = new Label(0,i+1,goods.get(i).getGno()+"");
				c2 = new Label(1,i+1,goods.get(i).getGname());
				c3 = new Label(2,i+1,goods.get(i).getPrice().toString());
				c4 = new Label(3,i+1,goods.get(i).getOffset()+"");
				c5 = new Label(4,i+1,TypeTools.getDate(goods.get(i).getTime()));
				c6 = new Label(5,i+1,goods.get(i).getCount()+"");
				sheet.addCell(c1);
				sheet.addCell(c2);
				sheet.addCell(c3);
				sheet.addCell(c4);
				sheet.addCell(c5);
				sheet.addCell(c6);
			}
			
			
			//д��
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally{
			try {
				if(wwb!= null){
					wwb.close();
				}
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		File f = new File("src/goodslist.xls");
		List<Goods> list = new ExcelDemo().read(f);
		for(Goods g : list){
			System.out.println(g);
		}
		new ExcelDemo().createExcel(list,new File("C:\\Documents and Settings\\Administrator\\����\\java"));
	}
	
}
