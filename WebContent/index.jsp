<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index</title>
</head>
<body>
	<jsp:include page="cabecalho.jsp"></jsp:include>
	
	<h1>Index</h1>
	<h2>Bem vindo ao curso de JSP</h2>
	<%= "seu sucesso garantido..." %>
	
	<br>
	<a href="login.jsp">Acessar o sistema</a>
	<br>
	<form action="receber-nome.jsp">
		<input type="text" id="nome" name="nome">
		<input type="submit" value="Enviar">
	</form>
	
	<% session.setAttribute("curso", "jsp"); %>
	
	<%@ page import="java.util.Date" %>
	<%= new Date() %>
	
	<%@ page info="Página do curso de JSP" %>
	<%@ page errorPage="receber-nome.jsp" %>
	<br>
	<%= 100/2 %>
	
	<%@ include file="pagina-include.jsp" %>
	
	<myprefix:minhatag/>
	
	<%--<jsp:forward page="receber-nome.jsp">
		<jsp:param value="Curso de JSP" name="paramforward"/>
	</jsp:forward>--%>
	
	<br>
	<%= "Bean: " + calcula.calcula(50) %>
	
	<form action="receber-nome.jsp" method="post">
	
		<% session.setAttribute("user", "java"); %>
		
		<input type="text" id="nome" name="nome" value="Leo">
		<input type="text" id="ano" name="ano" value="1998">
		<input type="text" id="sexo" name="sexo" value="Masculino">
		
		<input type="submit" value="Enviar">
	</form>
	
	<jsp:include page="rodape.jsp"></jsp:include>
</body>
</html>