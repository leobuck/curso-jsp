package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.TelefoneBean;
import connection.SingleConnection;

public class TelefoneDao {

	private Connection connection;
	
	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(TelefoneBean telefone) {
		try {
			String sql = "insert into telefone (numero, tipo, usuario) "
					+ " values (?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<TelefoneBean> listar(Long user) throws SQLException {
		List<TelefoneBean> lista = new ArrayList<TelefoneBean>();
		String sql = "select * from telefone where usuario = " + user + " order by id";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			TelefoneBean telefone = new TelefoneBean();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));
			
			lista.add(telefone);
		}
		
		return lista;
	}
	
	public void deletar(Long id) {
		String sql = "delete from telefone where id = '" + id + "'";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
