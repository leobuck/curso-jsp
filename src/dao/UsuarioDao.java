package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UsuarioBean;
import connection.SingleConnection;

public class UsuarioDao {

	private Connection connection;
	
	public UsuarioDao() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(UsuarioBean usuario) {
		try {
			String sql = " insert into usuario (login, senha, nome, cep, rua, bairro, cidade, estado, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura, ativo, sexo, perfil) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getCep());
			insert.setString(5, usuario.getRua());
			insert.setString(6, usuario.getBairro());
			insert.setString(7, usuario.getCidade());
			insert.setString(8, usuario.getEstado());
			insert.setString(9, usuario.getFotoBase64());
			insert.setString(10, usuario.getContentType());
			insert.setString(11, usuario.getCurriculoBase64());
			insert.setString(12, usuario.getContentTypeCurriculo());
			insert.setString(13, usuario.getFotoBase64Miniatura());
			insert.setBoolean(14, usuario.isAtivo());
			insert.setString(15, usuario.getSexo());
			insert.setString(16, usuario.getPerfil());
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
	
	public List<UsuarioBean> listar() throws SQLException {
		List<UsuarioBean> lista = new ArrayList<UsuarioBean>();
		String sql = " select * from usuario where login <> 'admin' order by id ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));
			
			lista.add(usuario);
		}
		
		return lista;
	}
	
	public List<UsuarioBean> listar(String descricao) throws SQLException {
		List<UsuarioBean> lista = new ArrayList<UsuarioBean>();
		String sql = " select * from usuario where login <> 'admin' and nome like '%" + descricao + "%' order by id ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));
			
			lista.add(usuario);
		}
		
		return lista;
	}
	
	public void deletar(Long id) {
		String sql = " delete from usuario where id = '" + id + "'";
		
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

	public UsuarioBean consultar(Long id) throws SQLException {
		String sql = " select * from usuario where id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setFotoBase64(resultSet.getString("fotobase64"));
			usuario.setContentType(resultSet.getString("contenttype"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));
			
			return usuario;
		}
		
		return null;
	}

	public void atualizar(UsuarioBean usuario) {
		int i = 1;
		String sql = " update usuario "
				+ " set login = ?, senha = ?, nome = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, estado = ? ";
		
		if (usuario.getContentType() != null && usuario.getFotoBase64() != null)
			sql += ", fotobase64 = ?, contenttype = ? ";
		
		if (usuario.getContentTypeCurriculo() != null && usuario.getCurriculoBase64() != null)
			sql += ", curriculobase64 = ?, contenttypecurriculo = ? ";
		
		if (usuario.getFotoBase64Miniatura() != null) {
			sql += ", fotobase64miniatura = ? ";
		}
		
		sql += ", ativo = ?, sexo = ?, perfil = ? ";
		
		sql += " where id = " + usuario.getId();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(i++, usuario.getLogin());
			statement.setString(i++, usuario.getSenha());
			statement.setString(i++, usuario.getNome());
			statement.setString(i++, usuario.getCep());
			statement.setString(i++, usuario.getRua());
			statement.setString(i++, usuario.getBairro());
			statement.setString(i++, usuario.getCidade());
			statement.setString(i++, usuario.getEstado());
			
			if (usuario.getContentType() != null && usuario.getFotoBase64() != null) {
				statement.setString(i++, usuario.getFotoBase64());
				statement.setString(i++, usuario.getContentType());
			}
			
			if (usuario.getContentTypeCurriculo() != null && usuario.getCurriculoBase64() != null) {
				statement.setString(i++, usuario.getCurriculoBase64());
				statement.setString(i++, usuario.getContentTypeCurriculo());
			}
			
			if (usuario.getFotoBase64Miniatura() != null) {
				statement.setString(i++, usuario.getFotoBase64Miniatura());
			}
			
			statement.setBoolean(i++, usuario.isAtivo());
			statement.setString(i++, usuario.getSexo());
			statement.setString(i++, usuario.getPerfil());
			
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
	
	public boolean validarLogin(String login, Long id) {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <> " + id;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
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
