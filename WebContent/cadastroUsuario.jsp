<%@page import="beans.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Cadastro de Usuários</title>
	
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
	
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp"></jsp:include>
	
	<div class="container">
		<h2 class="text-center">Cadastro de Usuários</h2>	
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
	
		<form action="salvarUsuario" method="post" class="form" id="formUser" onsubmit="return validarCampos()" enctype="multipart/form-data">
			<div class="form-group">
				<label>Código</label> 
				<input type="text" id="id" name="id" value="${user.id}" readonly="readonly" class="form-control" />
			</div>
			
			<div class="form-group">
				<label>Login</label> 
				<input type="text" id="login" name="login" value="${user.login}" class="form-control" placeholder="Informe o login" />
			</div> 
			
			<div class="form-group">
				<label>Senha</label>
				<input type="password" id="senha" name="senha" value="${user.senha}" class="form-control" placeholder="Informe a senha" />
			</div>
			
			<div class="form-group">
				<label>Nome</label> 
				<input type="text" id="nome" name="nome" value="${user.nome}" class="form-control" placeholder="Informe o nome" />
			</div> 
			
			<div class="form-group">
				<label>CEP</label> 
				<input type="text" id="cep" name="cep" value="${user.cep}"  onblur="consultarCep()" class="form-control" placeholder="Informe o CEP" />
			</div>
			
			<div class="form-group">
				<label>Rua</label> 
				<input type="text" id="rua" name="rua" value="${user.rua}" class="form-control" />
			</div> 
			
			<div class="form-group">
				<label>Bairro</label> 
				<input type="text" id="bairro" name="bairro" value="${user.bairro}" class="form-control" />
			</div> 
			
			<div class="form-group">
				<label>Cidade</label> 
				<input type="text" id="cidade" name="cidade" value="${user.cidade}" class="form-control" />
			</div>
			
			<div class="form-group">
				<label>Estado</label> 
				<input type="text" id="estado" name="estado" value="${user.estado}" class="form-control" />
			</div>
			
			<div class="form-group">
				<label>Foto</label> 
				<input type="file" id="foto" name="foto" value="${user.fotoBase64}" class="form-control" />
			</div>
			
			<div class="form-group">
				<label>Currículo</label> 
				<input type="file" id="curriculo" name="curriculo" value="${user.curriculoBase64}" class="form-control" />
			</div>
			
			<div class="form-check">
				<input class="form-check-input" type="checkbox" id="ativo" name="ativo"
					<%
						if (request.getAttribute("user") != null) {
							
							UsuarioBean user = (UsuarioBean) request.getAttribute("user");
							
							if (user.isAtivo()) {
								out.print(" checked=\"checked\" ");
							}
						}
					%>
				>
			  	<label class="form-check-label" for="ativo">
			    	Ativo?
			  	</label>
			</div>
			
			<br>
			
			<label>Sexo</label>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="sexo" id="masculino" value="masculino"
					<%
						if (request.getAttribute("user") != null) {
							
							UsuarioBean user = (UsuarioBean) request.getAttribute("user");
							
							if (user.getSexo().equalsIgnoreCase("masculino")) {
								out.print(" checked=\"checked\" ");
							}
						}
					%>
				>
			  	<label class="form-check-label" for="masculino">
			    	Masculino
				</label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="sexo" id="feminino" value="feminino"
					<%
						if (request.getAttribute("user") != null) {
							
							UsuarioBean user = (UsuarioBean) request.getAttribute("user");
							
							if (user.getSexo().equalsIgnoreCase("feminino")) {
								out.print(" checked=\"checked\" ");
							}
						}
					%>
				>
			  	<label class="form-check-label" for="feminino">
			    	Feminino
				</label>
			</div>
			
			<br>
			
			<div class="form-group">
		    	<label for="perfil">Perfil</label>
		      	<select id="perfil" name="perfil" class="form-control">
		        	<option value="nao_informado"
		        		<%
							if (request.getAttribute("user") != null) {
								
								UsuarioBean user = (UsuarioBean) request.getAttribute("user");
								
								if (user.getPerfil().equalsIgnoreCase("nao_informado")) {
									out.print(" selected=\"selected\" ");
								}
							}
						%>
		        	>Selecione</option>
		        	<option value="administrador"
		        		<%
							if (request.getAttribute("user") != null) {
								
								UsuarioBean user = (UsuarioBean) request.getAttribute("user");
								
								if (user.getPerfil().equalsIgnoreCase("administrador")) {
									out.print(" selected=\"selected\" ");
								}
							}
						%>
		        	>Administrador</option>
		        	<option value="secretario"
		        		<%
							if (request.getAttribute("user") != null) {
								
								UsuarioBean user = (UsuarioBean) request.getAttribute("user");
								
								if (user.getPerfil().equalsIgnoreCase("secretario")) {
									out.print(" selected=\"selected\" ");
								}
							}
						%>
		        	>Secretário</option>
		        	<option value="gerente"
		        		<%
							if (request.getAttribute("user") != null) {
								
								UsuarioBean user = (UsuarioBean) request.getAttribute("user");
								
								if (user.getPerfil().equalsIgnoreCase("gerente")) {
									out.print(" selected=\"selected\" ");
								}
							}
						%>
		        	>Gerente</option>
		        	<option value="funcionario"
		        		<%
							if (request.getAttribute("user") != null) {
								
								UsuarioBean user = (UsuarioBean) request.getAttribute("user");
								
								if (user.getPerfil().equalsIgnoreCase("funcionario")) {
									out.print(" selected=\"selected\" ");
								}
							}
						%>
		        	>Funcionário</option>
		      	</select>
		    </div>
			
			<button type="submit" class="btn btn-success">
				<i class="fas fa-save"></i>
				Salvar
			</button> 
			<button type="submit" class="btn btn-danger" onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'">
				<i class="fas fa-times"></i>
				Cancelar
			</button>
		</form>
		
		<br>
		
		<form action="pesquisaServlet" method="post" class="form" id="formPesquisa">
			<div class="form-group">
				<label>Descrição</label> 
				<input type="text" id="descricao" name="descricao" class="form-control" />
			</div>
			
			<button type="submit" class="btn btn-primary">
				<i class="fas fa-search"></i>
				Pesquisar
			</button> 	
		</form>
		
		<br>
		
		<c:if test="${usuarios != null}">
			<table class="table table-sm table-bordered table-hover">
				<thead class="text-center">
					<tr>
						<th>#</th>
						<th>Foto</th>
						<th>Currículo</th>
						<th>Login</th>						
						<th>Nome</th>
						<th>CEP</th>
						<th>Ação</th>
					</tr>
				</thead>
				<c:forEach items="${usuarios}" var="user">
					<tbody class="text-center">
						<tr>
							<td><c:out value="${user.id}"></c:out></td>
							<td>
								<a href="salvarUsuario?acao=download&tipo=image&user=${user.id}">
									<img src='<c:out value="${user.tmpImageUser}"></c:out>' height="50" width="50" class="img-redonda">
								</a>
							</td>
							<td>
								<a href="salvarUsuario?acao=download&tipo=file&user=${user.id}">
									<i class="fas fa-file-pdf"></i>
								</a>
							</td>
							<td><c:out value="${user.login}"></c:out></td>
							<td><c:out value="${user.nome}"></c:out></td>
							<td><c:out value="${user.cep}"></c:out></td>
							<td class="text-center">
								<a class="btn btn-primary" href="salvarUsuario?acao=editar&user=${user.id}"> 
									<i class="fas fa-edit"></i>
								</a> 
								<a class="btn btn-danger" href="salvarUsuario?acao=delete&user=${user.id}" onclick="return confirm('Confirmar a exclusão?');"> 
									<i class="fas fa-trash"></i>
								</a>
								<a class="btn btn-warning" href="telefoneServlet?acao=listar&user=${user.id}"> 
									<i class="fas fa-phone"></i>
								</a>
							</td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
    <!-- JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		function validarCampos() {
			if ($("#login").val() == "") {
				$("#alert-mensagem").text("Informe o campo Login!");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#senha").val() == "") {
				$("#alert-mensagem").text("Informe o campo Senha!");
				$("#alert").css({display: "block"});
				return false;
			} else if ($("#nome").val() == "") {
				$("#alert-mensagem").html("Informe o campo Nome!");
				$("#alert").css({display: "block"});
				return false;
			} 
			
			$("#alert-mensagem").text("");
			$("#alert").css({display: "none"});
			
			return true;
		}
		
		function consultarCep() {
			var cep = $("#cep").val();	

			//Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
                if (!("erro" in dados)) {
                    //Atualiza os campos com os valores da consulta.
					 $("#rua").val(dados.logradouro);
                     $("#bairro").val(dados.bairro);
                     $("#cidade").val(dados.localidade);
                     $("#estado").val(dados.uf);
                } else {
                	$("#rua").val('');
                    $("#bairro").val('');
                    $("#cidade").val('');
                    $("#estado").val('');
                    //CEP pesquisado não foi encontrado.
                    alert("CEP não encontrado.");
                }
            });
		}
		
	</script>
</body>
</html>