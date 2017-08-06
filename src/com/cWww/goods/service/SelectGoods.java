package com.cWww.goods.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cWww.goods.dao.GoodDAO;
import com.cWww.goods.dao.TypeDAO;
import com.cWww.goods.dto.GoodDTO;
import com.cWww.goods.dto.TypeDTO;

/**
 * 业务逻辑代码
 * @author Administrator
 *
 */
public class SelectGoods {

	private GoodDAO ga = new GoodDAO();
	private TypeDAO ta = new TypeDAO();
	
	public List<GoodDTO> findOrderByCounts(TypeDTO type,int currentPage,int pageSize){
		List<GoodDTO> list = ga.findAll();
		List<GoodDTO> newlist = new ArrayList<>();
		List<GoodDTO> returnlist = new ArrayList<>();
		int index = (currentPage - 1)*pageSize;
		for(GoodDTO good : list){
			if(good.getType().getId() == type.getId()){
				newlist.add(good);
			}
		}
		Collections.sort(newlist,new Comparator<GoodDTO>() {
			@Override
			public int compare(GoodDTO o1, GoodDTO o2) {
				if(o1.getCounts()>o2.getCounts()){
					return 1;
				}else if(o1.getCounts() < o2.getCounts()){
					return -1;
				}
				return 0;
			}
		});
		for(int i = 0 ; i < newlist.size() ; i++){
			if(i >= (index - 1) && i < (index + pageSize)){
				returnlist.add(newlist.get(i));
			}
		}
		System.out.println(returnlist.size());
		return returnlist;
	}
	
	public List<GoodDTO> findByInfo(String infos,int currentPage,int pageSize){
		List<GoodDTO> list = ga.findAll();
		List<GoodDTO> newlist = new ArrayList<>();
		List<GoodDTO> returnlist = new ArrayList<>();
		int index = (currentPage - 1)*pageSize;
		TypeDTO type = null;
		for(GoodDTO good: list){
			type = ta.findById(good.getType());
			String goodInfo = good.getGoodsname()+type.getName();
			if(goodInfo.contains(infos)){
				good.setType(type);
				newlist.add(good);
			}
		}
		for(int i = 0 ; i < newlist.size() ; i++){
			if(i >= (index - 1) && i < (index + pageSize)){
				returnlist.add(newlist.get(i));
			}
		}
		return returnlist;
	}
	
	public static void main(String[] args) {
		
//		SelectGoods sg = new SelectGoods();
//		List<GoodDTO> list = sg.findByInfo("电视", 1, 20);
//		List<GoodDTO> list = sg.findOrderByCounts(new TypeDTO(1,""), 1, 5);
		GoodDAO gd = new GoodDAO();
		List<GoodDTO> list = gd.findByCondition(1,0,5);
		for(GoodDTO good: list){
			System.out.println(good);
		}
//		GoodDTO good = new GoodDTO();
//		good.setId(10);
//		good = gd.findById(good);
//		System.out.println(good);
		
		
//		TypeDAO type = new TypeDAO();
//		type.findByCondition();
	}
	
}
