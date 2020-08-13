package servlet;

import java.io.IOException;
import java.math.BigDecimal;
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
import beans.ProdutoBean;
import dao.CategoriaDao;
import dao.ProdutoDao;

@WebServlet("/produtoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProdutoDao produtoDao = new ProdutoDao();
	
	private CategoriaDao categoriaDao = new CategoriaDao();
	
	public ProdutoServlet() {
		super();
	}
       
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String product = request.getParameter("product");
		
		if (acao.equalsIgnoreCase("delete")) {
			produtoDao.deletar(Long.parseLong(product));
			
			redirecionar(request, response);			
		} else if (acao.equalsIgnoreCase("editar")) {			
			RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
			
			try {
				ProdutoBean produto = produtoDao.consultar(Long.parseLong(product));
				request.setAttribute("product", produto);
				request.setAttribute("categorias", categoriaDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("listarTodos")) {
			redirecionar(request, response);
		}		
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		List<MensagemBean> mensagens = new ArrayList<>();
		boolean podeSalvar = true;
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			redirecionar(request, response);
		}
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		BigDecimal quantidade = new BigDecimal(request.getParameter("quantidade"));
		BigDecimal valor = new BigDecimal(request.getParameter("valor").replace(".", "").replace(",", ".").replace("R$ ", ""));
		String categoria = request.getParameter("categoria");
		
		if (nome == null || nome.isEmpty()) { 
			mensagens.add(new MensagemBean("warning", "Nome deve ser informado!"));
			podeSalvar = false;
		}
		
		if (quantidade == null || quantidade.doubleValue() <= 0) { 
			mensagens.add(new MensagemBean("warning", "Quantidade deve ser informada!"));
			podeSalvar = false;
		}
		
		if (valor == null || valor.doubleValue() <= 0) { 
			mensagens.add(new MensagemBean("warning", "Valor deve ser informada!"));
			podeSalvar = false;
		}
		
		Long _id = 0L;
		if (id != null && !id.isEmpty()) {
			_id = Long.parseLong(id);
		}
		
		Long categoria_id = 0L;
		if (categoria != null && !categoria.isEmpty()) {
			categoria_id = Long.parseLong(categoria);
		}
		
		ProdutoBean produto = new ProdutoBean();
		produto.setId(_id);
		produto.setNome(nome);
		produto.setQuantidade(quantidade);
		produto.setValor(valor);
		produto.setCategoriaId(categoria_id);
		
		if (!produtoDao.validarProduto(nome, _id)) {
			mensagens.add(new MensagemBean("warning", "Produto já cadastrado!"));
			podeSalvar = false;
		}		
		
		if (podeSalvar) {
			if (_id == 0L) {
				produtoDao.salvar(produto);
				mensagens.add(new MensagemBean("success", "Produto cadastrado com sucesso!"));
			} else {
				produtoDao.atualizar(produto);
				mensagens.add(new MensagemBean("success", "Produto atualizado com sucesso!"));
			}
		} else {
			request.setAttribute("product", produto);
		}
		
		request.setAttribute("mensagens", mensagens);
		
		if (_id != 0L && !podeSalvar) {
			request.setAttribute("product", produto);
			RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");						
			view.forward(request, response);
		} else {
			redirecionar(request, response);
		}	
	}

	private void redirecionar(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
		
		try {
			request.setAttribute("produtos", produtoDao.listar());
			request.setAttribute("categorias", categoriaDao.listar());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		view.forward(request, response);
	}
}
