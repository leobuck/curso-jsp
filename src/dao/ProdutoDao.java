package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ProdutoBean;
import connection.SingleConnection;

public class ProdutoDao {

	private Connection connection;
	
	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}
	
	public List<ProdutoBean> listar() throws SQLException {
		List<ProdutoBean> lista = new ArrayList<ProdutoBean>();
		String sql = "select * from produto order by id";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			ProdutoBean produto = new ProdutoBean();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getBigDecimal("quantidade"));
			produto.setValor(resultSet.getBigDecimal("valor"));
			produto.setCategoriaId(resultSet.getLong("categoria_id"));
			
			lista.add(produto);
		}
		
		return lista;		
	}
	
	public void salvar(ProdutoBean produto) {
		String sql = "insert into produto(nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setBigDecimal(2, produto.getQuantidade());
			statement.setBigDecimal(3, produto.getValor());
			statement.setLong(4, produto.getCategoriaId());
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
	
	public void atualizar(ProdutoBean produto) {
		String sql = "update produto set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setBigDecimal(2, produto.getQuantidade());
			statement.setBigDecimal(3, produto.getValor());
			statement.setLong(4, produto.getCategoriaId());
			statement.setLong(5, produto.getId());
			statement.executeUpdate();
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
	
	public void deletar(Long id) {
		String sql = "delete from produto where id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
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
	
	public ProdutoBean consultar(Long id) throws SQLException {
		String sql = "select * from produto where id = ?";		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			ProdutoBean produto = new ProdutoBean();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getBigDecimal("quantidade"));
			produto.setValor(resultSet.getBigDecimal("valor"));
			produto.setCategoriaId(resultSet.getLong("categoria_id"));
			
			return produto;
		}
		
		return null;
	}
	
	public boolean validarProduto(String nome, Long id) {
		String sql = "select count(1) as qtd from produto where nome = ? and id <> ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setLong(2, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("qtd") <= 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return false;
	}
}
