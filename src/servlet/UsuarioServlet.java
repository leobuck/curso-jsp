package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.MensagemBean;
import beans.UsuarioBean;
import dao.UsuarioDao;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UsuarioDao usuarioDao = new UsuarioDao();
	
    public UsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");
		
		if (acao.equals("delete")) {
			usuarioDao.deletar(Long.parseLong(user));
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			
			try {
				request.setAttribute("usuarios", usuarioDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
		} else if (acao.equals("editar")) {
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			
			try {
				UsuarioBean usuario = usuarioDao.consultar(Long.parseLong(user));
				request.setAttribute("user", usuario);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("listarTodos")) {
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			
			try {
				request.getSession().setAttribute("user", null);
				request.setAttribute("user", null);
				request.setAttribute("usuarios", usuarioDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
			
		} else if (acao.equalsIgnoreCase("download")) {
			try {
				UsuarioBean usuario = usuarioDao.consultar(Long.parseLong(user));
				if (usuario != null) {
					String tipo = request.getParameter("tipo");
					
					if (tipo.equalsIgnoreCase("image") && usuario.getFotoBase64() != null) {
						response.setHeader("Content-Disposition", "attachment;filename=foto_user" + usuario.getId() + "." 
								+ usuario.getContentType().split("\\/")[1]);
						
						byte[] imageBytes = new Base64().decodeBase64(usuario.getFotoBase64());
						
						InputStream is = new ByteArrayInputStream(imageBytes);
						
						int read = 0;
						byte[] bytes = new byte[1024];
						OutputStream os = response.getOutputStream();
						
						while ((read = is.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}
						
						os.flush();
						os.close();
					} else if (tipo.equalsIgnoreCase("file") && usuario.getCurriculoBase64() != null) {
						response.setHeader("Content-Disposition", "attachment;filename=curriculo_user" + usuario.getId() + "." 
								+ usuario.getContentTypeCurriculo().split("\\/")[1]);
						
						byte[] fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
						
						InputStream is = new ByteArrayInputStream(fileBytes);
						
						int read = 0;
						byte[] bytes = new byte[1024];
						OutputStream os = response.getOutputStream();
						
						while ((read = is.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}
						
						os.flush();
						os.close();
					} else {
						response.sendRedirect("salvarUsuario?acao=listarTodos");
					}
				} else {
					response.sendRedirect("salvarUsuario?acao=listarTodos");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		List<MensagemBean> mensagens = new ArrayList<>();
		boolean podeSalvar = true;
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			
			try {
				request.setAttribute("user", null);
				request.setAttribute("usuarios", usuarioDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
		}
		
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String nome = request.getParameter("nome");
		String cep = request.getParameter("cep");
		String rua = request.getParameter("rua");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String estado = request.getParameter("estado");
		boolean ativo = request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on");
		String sexo = request.getParameter("sexo");
		String perfil = request.getParameter("perfil");
				
		if (login == null || login.isEmpty()) { 
			mensagens.add(new MensagemBean("warning", "Login deve ser informado!"));
			podeSalvar = false;
		}
		
		if (senha == null || senha.isEmpty()) {
			mensagens.add(new MensagemBean("warning", "Senha deve ser informada!"));
			podeSalvar = false;
		}
		
		if (nome == null || nome.isEmpty()) {
			mensagens.add(new MensagemBean("warning", "Nome deve ser informado!"));
			podeSalvar = false;
		}
		
		Long _id = 0L;
		if (id != null && !id.isEmpty()) {
			_id = Long.parseLong(id);
		} 
		
		UsuarioBean usuario = new UsuarioBean();
		usuario.setId(_id);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setBairro(bairro);
		usuario.setCidade(cidade);
		usuario.setEstado(estado);
		usuario.setAtivo(ativo);
		usuario.setSexo(sexo);
		usuario.setPerfil(perfil);
		
		// Upload de imagem e pdf
		if (ServletFileUpload.isMultipartContent(request)) {			
			try {
				Part imagemFoto = request.getPart("foto");
				if (imagemFoto != null) {
					if (imagemFoto.getInputStream().available() > 0) {
						byte[] bytesImagem = converteStreamParaByte(imagemFoto.getInputStream());
						
						String fotoBase64 = new Base64().encodeBase64String(bytesImagem);
						
						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						
						// Miniatura da imagem
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);						
						String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
						usuario.setFotoBase64Miniatura(miniaturaBase64);
					}
				}
				
				Part curriculoPdf = request.getPart("curriculo");
				if (curriculoPdf != null) {
					if (curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64().encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));
						
						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!usuarioDao.validarLogin(login, _id)) {
			mensagens.add(new MensagemBean("warning", "Usuário já existe com o mesmo login!"));
			podeSalvar = false;
		}
		
		if (podeSalvar) {
			if (_id == 0L) {
				usuarioDao.salvar(usuario);
				mensagens.add(new MensagemBean("success", "Usuário cadastrado com sucesso!"));
			} else {
				usuarioDao.atualizar(usuario);
				mensagens.add(new MensagemBean("success", "Usuário atualizado com sucesso!"));
			}		
			
		} else {
			request.setAttribute("user", usuario);
		}
		
		request.setAttribute("mensagens", mensagens);
		
		if (_id != 0L && !podeSalvar) {
			request.setAttribute("user", usuario);
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");						
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			
			try {
				request.setAttribute("usuarios", usuarioDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
		}		
	}
	
	private byte[] converteStreamParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		
		return baos.toByteArray();
	}
}
