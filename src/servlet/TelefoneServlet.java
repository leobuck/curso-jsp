package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.MensagemBean;
import beans.TelefoneBean;
import beans.UsuarioBean;
import dao.TelefoneDao;
import dao.UsuarioDao;

@WebServlet("/telefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UsuarioDao usuarioDao = new UsuarioDao();
	private TelefoneDao telefoneDao = new TelefoneDao();
	
    public TelefoneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao.equalsIgnoreCase("delete")) {
			List<MensagemBean> mensagens = new ArrayList<>();
			String fone = request.getParameter("fone");
			
			telefoneDao.deletar(Long.parseLong(fone));
			
			mensagens.add(new MensagemBean("success", "Telefone excluído com sucesso!"));
			request.setAttribute("mensagens", mensagens);
			
			UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("user");
			try {
				request.setAttribute("telefones", telefoneDao.listar(usuario.getId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("listar")) {
			try {
				String user = request.getParameter("user");
				
				UsuarioBean usuario = usuarioDao.consultar(Long.parseLong(user));
				request.setAttribute("user", usuario);
				request.getSession().setAttribute("user", usuario);
				request.setAttribute("telefones", telefoneDao.listar(usuario.getId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher("telefonesUsuario.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		List<MensagemBean> mensagens = new ArrayList<>();
		boolean podeSalvar = true;
		
		UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("user");
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");
		
		if (numero == null || numero.isEmpty()) {
			mensagens.add(new MensagemBean("warning", "Número deve ser informado!"));
			podeSalvar = false;
		}
		
		TelefoneBean telefone = new TelefoneBean();
		telefone.setNumero(numero);
		telefone.setTipo(tipo);
		telefone.setUsuario(usuario.getId());
		
		if (podeSalvar) {
			telefoneDao.salvar(telefone);
			mensagens.add(new MensagemBean("success", "Telefone cadastrado com sucesso!"));
		}
		request.setAttribute("mensagens", mensagens);
		request.setAttribute("user", usuario);
		request.getSession().setAttribute("user", usuario);
		
		try {
			request.setAttribute("telefones", telefoneDao.listar(usuario.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher view = request.getRequestDispatcher("telefonesUsuario.jsp");						
		view.forward(request, response);
	}

}
