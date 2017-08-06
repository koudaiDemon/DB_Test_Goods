package com.cWww.goods.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cWww.dbutils.DBUtils;
import com.cWww.dbutils.DBUtils.CallBack;
import com.cWww.goods.dto.TypeDTO;

public class TypeDAO implements BaseDAO<TypeDTO>{

	@Override
	public boolean insert(TypeDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"insert into tb_type(id,cname) values(?,?)", 
				t.getId(),
				t.getName());
	}

	@Override
	public boolean delete(TypeDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"delete from tb_type where id = ?", 
				t.getId());
	}

	@Override
	public boolean update(TypeDTO t) {
		return DBUtils.execUpdate(DBUtils.getConn(), 
				"update tb_type set cname = ? where id = ?", 
				t.getName(),
				t.getId());
	}

	@Override
	public List<TypeDTO> findAll() {
		return DBUtils.queryList("select * from tb_type", new CallBack<TypeDTO>(){
			@Override
			public List<TypeDTO> getDatas(ResultSet rs) {
				List<TypeDTO> list = new ArrayList<>();
				try {
					TypeDTO type = null;
					while(rs.next()){
						type = new TypeDTO(rs.getInt("id"),rs.getString("cname"));
						list.add(type);
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
	public TypeDTO findById(TypeDTO t) {
		return DBUtils.queryOne("select * from tb_type where id = ?", new CallBack<TypeDTO>(){
			@Override
			public TypeDTO getData(ResultSet rs) {
				try {
					TypeDTO type = null;
					if(rs.next()){
						type = new TypeDTO(rs.getInt("id"),rs.getString("cname"));
					}
					return type;
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
	public List<TypeDTO> findByCondition(Object...params){
		return DBUtils.queryList("select * from tb_type where cid = ? order by counts limit ?,?", new CallBack<TypeDTO>(){
			@Override
			public List<TypeDTO> getDatas(ResultSet rs) {
				List<TypeDTO> list = new ArrayList<>();
				try {
					TypeDTO type = null;
					while(rs.next()){
						type = new TypeDTO(rs.getInt("id"),rs.getString("cname"));
						list.add(type);
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
