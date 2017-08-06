package com.cWww.goods.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cWww.dbutils.DBUtils;
import com.cWww.dbutils.DBUtils.CallBack;
import com.cWww.goods.dto.GoodDTO;
import com.cWww.goods.dto.TypeDTO;

public class GoodDAO implements BaseDAO<GoodDTO>{

	@Override
	public boolean insert(GoodDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"insert into tb_goods(goodsname,price,offset,counts,cid) values(?,?,?,?,?)", 
				t.getGoodsname(),
				t.getPrice(),
				t.getOffset(),
				t.getCounts(),
				t.getType().getId());
	}

	@Override
	public boolean delete(GoodDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"delete from tb_goods where id = ?", 
				t.getType().getId());
	}

	@Override
	public boolean update(GoodDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"update tb_goods set counts = ? where id = ?", 
				t.getCounts(),
				t.getType().getId());
	}

	@Override
	public List<GoodDTO> findAll() {
		return DBUtils.queryList("select * from tb_goods", new CallBack<GoodDTO>(){
			@Override
			public List<GoodDTO> getDatas(ResultSet rs) {
				List<GoodDTO> list = new ArrayList<>();
				try {
					GoodDTO good = null;
					TypeDTO type = null;
					while(rs.next()){
						type = new TypeDTO();
						type.setId(rs.getInt("cid"));
						good = new GoodDTO(rs.getInt("id"),
								rs.getString("goodsname"),
								rs.getBigDecimal("price"),
								rs.getDouble("offset"),
								rs.getDate("time"),
								rs.getInt("counts"),
								type);
						list.add(good);
					}
					return list;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Override
	public GoodDTO findById(GoodDTO t) {
		return DBUtils.queryOne("select * from tb_goods where id = ?", new CallBack<GoodDTO>(){
			@Override
			public GoodDTO getData(ResultSet rs) {
				try {
					GoodDTO good = null;
					TypeDTO type = new TypeDTO();
					if(rs.next()){
						type.setId(rs.getInt("cid"));
						good = new GoodDTO(rs.getInt("id"),rs.getString("goodsname"),rs.getBigDecimal("price"),rs.getDouble("offset"),rs.getDate("time"),rs.getInt("counts"),type);
					}
					return good;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		},t.getId());
	}

	/**
	 * 按照条件来查询
	 * @param params
	 * @return
	 */
	@Override
	public List<GoodDTO> findByCondition(Object...params){
		return DBUtils.queryList("select * from tb_goods where cid = ? order by counts limit ?,?", new CallBack<GoodDTO>(){
			@Override
			public List<GoodDTO> getDatas(ResultSet rs) {
				List<GoodDTO> list = new ArrayList<>();
				try {
					GoodDTO good = null;
					TypeDTO type = null;
					while(rs.next()){
						type = new TypeDTO();
						type.setId(rs.getInt("cid"));
						good = new GoodDTO(rs.getInt("id"),
								rs.getString("goodsname"),
								rs.getBigDecimal("price"),
								rs.getDouble("offset"),
								rs.getDate("time"),
								rs.getInt("counts"),
								type);
						list.add(good);
					}
					return list;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		},params);
	}
	
}
