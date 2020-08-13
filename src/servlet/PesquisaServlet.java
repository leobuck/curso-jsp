package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;

@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private UsuarioDao usuarioDao = new UsuarioDao();
	
    public PesquisaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String descricao = request.getParameter("descricao");
		
		RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
		request.getSession().setAttribute("user", null);
		request.setAttribute("user", null);
		
		
		try {
			if (descricao != null && !descricao.trim().isEmpty()) {
				request.setAttribute("usuarios", usuarioDao.listar(descricao));
			} else {
				request.setAttribute("usuarios", usuarioDao.listar());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		view.forward(request, response);
	}

}
