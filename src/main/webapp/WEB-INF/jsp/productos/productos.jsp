<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesbelen.model.Fabricante"%>
<%@page import="java.util.List"%>
<%@ page import="org.iesbelen.model.Producto" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<title>Productos</title>
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
<%@include file="../../../boostrap.jspf"%>
 <%@include file="../../../header.jspf"%>
 <%@include file="../../../nav.jspf"%>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Productos</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="${pageContext.request.contextPath}/tienda/productos/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
				
			</div>

			<form action="${pageContext.request.contextPath}/tienda/productos/">
				<input type="text" name="busqueda" placeholder="Buscar...">
				<input type="submit" value="buscar">
			</form>

		</div>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 10%">Código</div>
			<div style="float: left;width: 30%">Nombre</div>
			<div style="float: left;width: 20%">Precio</div>
			<div style="float: left;width: 20%">Fabricante</div>
			<div style="float: left;width: 20%;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaProductos") != null && request.getAttribute("listaFabricantes") != null) {
			List<Producto> listaProducto = new ArrayList<Producto>();
			if(request.getAttribute("listaProductos") != Optional.empty()){
            	listaProducto = (List<Producto>)request.getAttribute("listaProductos");
			}

			List<Fabricante> listaFabricante = (List<Fabricante>) request.getAttribute("listaFabricantes");

            for (Producto producto : listaProducto) {
				String fab = "";
				for(Fabricante fabricante : listaFabricante) {
					if(producto.getCodigo_fabricante() == fabricante.getIdFabricante()){
						fab = fabricante.getNombre();
						break;
					}
				}
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 10%"><%= producto.getIdProducto()%></div>
			<div style="float: left;width: 30%"><%= producto.getNombre()%></div>
			<div style="float: left;width: 20%"><%= producto.getPrecio()%></div>
			<div style="float: left;width: 20%"><%= fab%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="${pageContext.request.contextPath}/tienda/productos/<%= producto.getIdProducto()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="${pageContext.request.contextPath}/tienda/productos/editar/<%= producto.getIdProducto()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="${pageContext.request.contextPath}/tienda/productos/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= producto.getIdProducto()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>
	<% 
            }
        } else { 
    %>
		No hay registros de producto
	<% } %>
	</div>
 <%@include file="../../../footer.jspf"%>
</body>
</html>