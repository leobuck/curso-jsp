package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;

public class LoginDao {

	private Connection connection;
	
	public LoginDao() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLoginESenha(String login, String senha) throws SQLException {
		
		String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			return true;
		}
		
		return false;
	}
}
