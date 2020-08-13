<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Receber nome</title>
</head>
<body>
	<h1>Receber nome</h1>
	<%= 
	"Nome recebido: " + request.getParameter("nome") 
	%>
	<br>
	<%! int cont = 2;
	
		public int retorna(int n) {
			return n * 2;
		}
	%>
	<%= cont %>
	<br>
	<%= retorna(8) %>
	<br>
	<%= request.getContextPath() %>
	<br>
	<%= request.getContentType() %>
	<br>
	<%= request.getLocalAddr() %>
	<br>
	<%= request.getLocalName() %>
	<br>
	<%= request.getLocalPort() %>
	<br>
	<%= request.getProtocol() %>
	<br>
	<%-- response.sendRedirect("/cadastro-pessoa.jsp"); --%>
	<br>
	<%= application.getInitParameter("estado") %>
	<br>
	<%= session.getAttribute("curso") %>
	<br>
	<%@ page isErrorPage="true" %>
	<%= "Exception: " + exception %>
	<br>
	<%= "Param Forward: " + request.getParameter("paramforward") %>
	<br>
	<p>Getters e Setters</p>
	<jsp:setProperty property="*" name="calcula"/>
	<jsp:getProperty property="nome" name="calcula"/><br>
	<jsp:getProperty property="ano" name="calcula"/><br>
	<jsp:getProperty property="sexo" name="calcula"/><br>
	
	<p>Expression Language</p>
	Nome: ${param.nome}
	<br>
	Ano: ${param.ano}
	<br>
	Sexo: ${param.sexo}
	<br>
	User: ${sessionScope.user}
</body>
</html>