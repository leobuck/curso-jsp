<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Telefones do Usuário</title>
	
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
	
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp"></jsp:include>
	
	<div class="container">
		<h2 class="text-center">Telefones do Usuário</h2>	
		<hr>
		<a class="btn btn-warning" href="salvarUsuario?acao=listarTodos">
			<i class="fas fa-arrow-left"></i>
			Voltar
		</a>
		<br><br>
		
		<c:if test="${mensagens != null}">
			<c:forEach items="${mensagens}" var="msg">
				<div class="alert alert-${msg.status}" role="alert">
					${msg.mensagem}
				</div>
			</c:forEach>
		</c:if>
	
		<div class="alert" id="alert" style="display: none;" role="alert">
			<span id="alert-mensagem"></span>
		</div>
	
		<form action="telefoneServlet" method="post" class="form" id="formFone" onsubmit="return validarCampos()">
			<div class="form-group">
				<label>Usuário</label> 
				<input type="text" id="user" name="user" value="${user.nome}" readonly="readonly" class="form-control" />
			</div>
		
			<div class="form-group">
				<label>Código</label> 
				<input type="text" id="id" name="id" value="${fone.id}" readonly="readonly" class="form-control" />
			</div>
			
			<div class="form-group">
				<label>Número</label> 
				<input type="text" id="numero" name="numero" value="${fone.numero}" class="form-control" placeholder="Informe o número do telefone do usuário" />
			</div>
			
			<div class="form-group">
				<label>Tipo</label> 
				<select id="tipo" name="tipo" class="form-control">
					<option>Residencial</option>
					<option>Celular</option>
					<option>Comercial</option>
				</select>
			</div>
			
			<button type="submit" class="btn btn-success">
				<i class="fas fa-save"></i>
				Salvar
			</button>
		</form>
		
		<br>
		
		<c:if test="${telefones != null}">
			<table class="table table-sm table-bordered table-hover">
				<thead class="text-center">
					<tr>
						<th>#</th>
						<th>Número</th>
						<th>Tipo</th>
						<th>Ação</th>
					</tr>
				</thead>
				<c:forEach items="${telefones}" var="fone">
					<tbody class="text-center">
						<tr>
							<td><c:out value="${fone.id}"></c:out></td>
							<td><c:out value="${fone.numero}"></c:out></td>
							<td><c:out value="${fone.tipo}"></c:out></td>
							<td class="text-center">
								<a class="btn btn-danger" href="telefoneServlet?acao=delete&fone=${fone.id}" onclick="return confirm('Confirmar a exclusão?');"> 
									<i class="fas fa-trash"></i>
								</a>
							</td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
    <!-- JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		function validarCampos() {
			if ($("#numero").val() == "") {
				$("#alert-mensagem").text("Informe o campo Número!");
				$("#alert").addClass("alert-warning");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#tipo").val() == "") {
				$("#alert-mensagem").text("Informe o campo Tipo!");
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