package com.model.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Login;
import com.model.persistence.helper.LoginRowMapper;

//create login credentials bean 
@Repository("loginDaoImpl")
public class LoginDaoImpl implements LoginDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean validate(String id, String password,int priority) {
		String pssd = "";
		
		try {
			String query = "";
			if(priority == 1) {
				query="select * from admin_login where id=?";
			}else if (priority == 2) {
				query="select * from doctor_login where id=?";
			}else if(priority == 3) {
				query="select * from patient_login where id=?";
			}
			
			Login login= jdbcTemplate.queryForObject(query,new LoginRowMapper(),id);
			
			
			if(login != null) {
				pssd = login.getPassword();
			}
			if(pssd.equals(password))
				return true;
			
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		return false;
	}


	@Override
	public boolean registerUser(String id, String password) {
		
		int rows = 0;
		try {
			String query="INSERT INTO login_credentials values(?,?)";
			rows= jdbcTemplate.update(query,new LoginRowMapper(),id,password);
			
			if(rows >0 ) 
				return true;
			
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return false;
	}

}
