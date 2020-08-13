<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Login</title>
	
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
	
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<%--<c:out value="${'Bem vindo ao JSTL'}"/>
	<c:import var="data" url="https://www.google.com.br"></c:import>
	<c:set var="data" scope="page" value="${500*10}"></c:set>
	<c:remove var="data"/>
	<c:out value="${data}"></c:out>
	<c:catch var="erro">
		<% int var = 100/2; %>
	</c:catch>
	<c:if test="${erro != null }">
		${erro.message}
	</c:if>
	<c:set var="numero" value="${100/2}"/>
	<c:choose>
		<c:when test="${numero > 50}">
			<c:out value="${'Maior que 50'}"></c:out>
		</c:when>
		<c:when test="${numero < 50}">
			<c:out value="${'Menor que 50'}"></c:out>
		</c:when>
		<c:otherwise>
			<c:out value="${'Não encontrou valor correto'}"></c:out>
		</c:otherwise>
	</c:choose>
	<c:forEach var="n" begin="1" end="${numero}">
		Item: ${n} <br>
	</c:forEach>
	<c:forTokens items="Leonardo-Buck-Godoy" delims="-" var="nome">
		Nome: <c:out value="${nome }"/><br>
	</c:forTokens>
	<c:url value="acesso-liberado.jsp" var="acesso">
		<c:param name="param1" value="123"/>
	</c:url>
	${acesso}
	<c:if test="${numero > 50}">
		<c:redirect url="http://www.google.com.br"/>
	</c:if>
	<c:if test="${numero < 50}">
		<c:redirect url="http://www.facebook.com.br"/>
	</c:if>
	--%>
	<div class="container">
		<br>
		<div class="alert" id="alert" style="display: none;" role="alert">
			<span id="alert-mensagem"></span>
		</div>
		
		<form action="LoginServlet" method="post" class="form form-signin" onsubmit="return validarCampos()">
			<div class="form-group">
				<label for="login">Login</label>
				<input type="text" id="login" name="login" class="form-control">
			</div>
			<div class="form-group">
				<label for="senha">Senha</label>
				<input type="password" id="senha" name="senha" class="form-control">
			</div>
			<button type="submit" class="btn btn-primary btn-block">
				<i class="fas fa-sign-in-alt"></i>
				Acessar
			</button>
		</form>
	</div>
	
	<!-- JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		function validarCampos() {
			if ($("#login").val() == "") {
				$("#alert-mensagem").text("Informe o campo Login!");
				$("#alert").addClass("alert-warning");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#senha").val() == "") {
				$("#alert-mensagem").text("Informe o campo Senha!");
				$("#alert").addClass("alert-warning");
				$("#alert").css({display: "block"});
				return false;
			}
			
			$("#alert-mensagem").text("");
			$("#alert").css({display: "none"});
			
			return true;
		}
	</script>
</body>
</html>