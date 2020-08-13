<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Cadastro de Produtos</title>
	
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
	
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp"></jsp:include>

	<div class="container">
		<h2 class="text-center">Cadastro de Produtos</h2>
		<hr>
		<c:if test="${mensagens != null}">
			<c:forEach items="${mensagens}" var="msg">
				<div class="alert alert-${msg.status}" role="alert">
					${msg.mensagem}
				</div>
			</c:forEach>
		</c:if>
	
		<div class="alert alert-warning" id="alert" style="display: none;" role="alert">
			<span id="alert-mensagem"></span>
		</div>
		
		<form action="produtoServlet" method="post" class="form" id="formProduct" onsubmit="return validarCampos()">
			<div class="form-group">
				<label>Código</label> 
				<input type="text" id="id" name="id" value="${product.id}" readonly="readonly" class="form-control" />
			</div> 
			
			<div class="form-group">
				<label>Nome</label> 
				<input type="text" id="nome" name="nome" value="${product.nome}" class="form-control" />
			</div>
			 
			<div class="form-group">
				<label>Quantidade</label>
				<input type="number" id="quantidade" name="quantidade" value="${product.quantidade}" class="form-control" />
			</div>
			 
			<div class="form-group">
				<label>Valor</label>
				<input type="text" id="valor" name="valor" value="${product.valorEmTexto}" class="form-control"  
					data-thousands="." data-decimal="," data-prefix="R$ " />
			</div>
			
			<div class="form-group">
		    	<label for="categoria">Categoria</label>
		      	<select id="categoria" name="categoria" class="form-control">
		        	<c:forEach items="${categorias}" var="cat">
		        		<option value="${cat.id}" id="${cat.id}"
		        			<c:if test="${cat.id == product.categoriaId}">
		        				<c:out value=" selected='selected' "/>
		        			</c:if>
		        		>${cat.nome}</option>
		        	</c:forEach>
		      	</select>
		    </div>
			
			<button type="submit" class="btn btn-success">
				<i class="fas fa-save"></i>
				Salvar
			</button> 
			<button type="submit" class="btn btn-danger" onclick="document.getElementById('formProduct').action='produtoServlet?acao=reset'">
				<i class="fas fa-times"></i>
				Cancelar
			</button>
		</form>
	
		<br>
	
		<c:if test="${produtos != null}">
			<table class="table table-sm table-bordered table-hover">
				<thead class="text-center">
					<tr>
						<th>#</th>
						<th>Nome</th>
						<th>Quantidade</th>
						<th>Valor</th>
						<th>Ação</th>
					</tr>
				</thead>
				<c:forEach items="${produtos}" var="product">
					<tbody class="text-center">
						<tr>
							<td><c:out value="${product.id}"></c:out></td>
							<td><c:out value="${product.nome}"></c:out></td>
							<td><c:out value="${product.quantidade}"></c:out></td>
							<td><fmt:formatNumber type="number" value="${product.valor}" 
								minFractionDigits="2"  maxFractionDigits="3"></fmt:formatNumber></td>
							<td class="text-center">
								<a class="btn btn-primary" href="produtoServlet?acao=editar&product=${product.id}"> 
									<i class="fas fa-edit"></i>
								</a> 
								<a class="btn btn-danger" href="produtoServlet?acao=delete&product=${product.id}" onclick="return confirm('Confirmar a exclusão?');"> 
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
	
	<script type="text/javascript" src="resources/js/jquery.maskMoney.js"></script>
	
	<script type="text/javascript">
		function validarCampos() {
			if ($("#nome").val() == "") {
				$("#alert-mensagem").html("Informe o campo Nome!");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#quantidade").val() == "") {
				$("#alert-mensagem").html("Informe o campo Quantidade!");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#valor").val() == "") {
				$("#alert-mensagem").html("Informe o campo Valor!");
				$("#alert").css({display: "block"});
				return false;
			}

			$("#alert-mensagem").text("");
			$("#alert").css({display: "none"});
			
			return true;
		}
	</script>
	<script type="text/javascript">
		$(function() {
	    	$('#valor').maskMoney();
	  	})
	</script>
</body>
</html>