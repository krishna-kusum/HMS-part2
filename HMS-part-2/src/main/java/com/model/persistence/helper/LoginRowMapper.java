package com.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bean.Login;

public class LoginRowMapper implements RowMapper<Login>{

	@Override
	public Login mapRow(ResultSet rs, int rowNum) throws SQLException {

		String id = rs.getString("id");
		String password = rs.getString("password");
		
		Login login = new Login(id, password);
		return login;
	}

}
