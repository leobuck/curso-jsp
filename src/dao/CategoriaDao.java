package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CategoriaBean;
import connection.SingleConnection;

public class CategoriaDao {

	private Connection connection;
	
	public CategoriaDao() {
		connection = SingleConnection.getConnection();
	}
	
	public List<CategoriaBean> listar() throws SQLException {
		List<CategoriaBean> lista = new ArrayList<CategoriaBean>();
		
		String sql = "select * from categoria";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			CategoriaBean categoria = new CategoriaBean();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			
			lista.add(categoria);
		}
		
		return lista;
	}
}
