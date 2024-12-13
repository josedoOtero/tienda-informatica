<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesbelen.model.Fabricante"%>
<%@page import="java.util.List"%>
<%@ page import="org.iesbelen.odt.FabricanteODT" %>

<%
	List<FabricanteODT> listaFabricante = null;

	if (request.getAttribute("listaFabricantesODT") != null) {
		listaFabricante = (List<FabricanteODT>)request.getAttribute("listaFabricantesODT");
	}
%>
<%@include file="../../../boostrap.jspf"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Fabricantes</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<style>
		.clearfix::after {
			content: "";
			display: block;
			clear: both;
		}
	</style>
</head>
<body>
<%@include file="../../../header.jspf"%>
<%@include file="../../../nav.jspf"%>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Fabricantes</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				<div style="position: absolute; left: 39%; top : 39%;">
					
					<form action="${pageContext.request.contextPath}/tienda/fabricantes/crear">
						<input type="submit" value="Crear">
					</form>
				</div>
			</div>
		</div>
		<div>
			<form action="${pageContext.request.contextPath}/tienda/fabricantes" method="post">
				<select name="valor_ordenacion" id="valor_ordenacion">
					<option value="nombre">Nombre</option>
					<option value="idFabricante">Código</option>
				</select>

				<select name="direcion_ordenacion" id="direcion_ordenacion">
					<option value="asc">Ascendente</option>
					<option value="desc">Descendente</option>
				</select>

				<input type="submit" value="Ordenar">
			</form>
			</form>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 25%">Código</div>
			<div style="float: left;width: 25%">Nombre</div>
			<div style="float: left;width: 25%">Cantidad</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 

            
            for (FabricanteODT fabricante : listaFabricante) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%"><%= fabricante.getIdFabricante()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getNombre()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getNumeroProductor()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="${pageContext.request.contextPath}/tienda/fabricantes/<%= fabricante.getIdFabricante()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="${pageContext.request.contextPath}/tienda/fabricantes/editar/<%= fabricante.getIdFabricante()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="${pageContext.request.contextPath}/tienda/fabricantes/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= fabricante.getIdFabricante()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>

	<%
        } if(listaFabricante == null) {
    %>
		No hay registros de fabricante
	<% } %>
	</div>

<%@include file="../../../footer.jspf"%>
</body>
</html>