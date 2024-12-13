<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.iesbelen.model.Fabricante"%>
<%@page import="java.util.List"%>
<%@ page import="org.iesbelen.model.Producto" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.iesbelen.model.Usuario" %>
<%@include file="/header.jspf"%>
<%@include file="/nav.jspf"%>
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

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
    <div class="clearfix">
        <div style="float: left; width: 50%">
            <h1>Usuarios</h1>
        </div>
        <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

            <div style="position: absolute; left: 39%; top : 39%;">

                <form action="${pageContext.request.contextPath}/tienda/usuarios/crear">
                    <input type="submit" value="Crear">
                </form>
            </div>

        </div>

    </div>
    <div class="clearfix">
        <hr/>
    </div>
    <div class="clearfix">
        <div style="float: left;width: 10%">Id</div>
        <div style="float: left;width: 30%">Nombre</div>
        <div style="float: left;width: 20%">Contraseña</div>
        <div style="float: left;width: 20%">Rol</div>
        <div style="float: left;width: 20%;overflow: hidden;">Acción</div>
    </div>
    <div class="clearfix">
        <hr/>
    </div>
    <%
        if (request.getAttribute("listaUsuarios") != null && request.getAttribute("listaUsuarios") != null) {
            List<Usuario> listaUsuario = new ArrayList<Usuario>();

            listaUsuario = (List<Usuario>) request.getAttribute("listaUsuarios");

            for (Usuario usuario : listaUsuario) {
                String fab = "";
    %>

    <div style="margin-top: 6px;" class="clearfix">
        <div style="float: left;width: 10%"><%= usuario.getIdUsuario()%></div>
        <div style="float: left;width: 30%"><%= usuario.getUsuario()%></div>
        <div style="float: left;width: 20%">*******</div>
        <div style="float: left;width: 20%"><%= usuario.getRol()%></div>
        <div style="float: none;width: auto;overflow: hidden;">
            <form action="${pageContext.request.contextPath}/tienda/usuarios/<%= usuario.getIdUsuario()%>" style="display: inline;">
                <input type="submit" value="Ver Detalle" />

            </form>
            <form action="${pageContext.request.contextPath}/tienda/usuarios/editar/<%= usuario.getIdUsuario()%>" style="display: inline;">
                <input type="submit" value="Editar" />
            </form>
            <form action="${pageContext.request.contextPath}/tienda/usuarios/borrar/" method="post" style="display: inline;">
                <input type="hidden" name="__method__" value="delete"/>
                <input type="hidden" name="codigo" value="<%= usuario.getIdUsuario()%>"/>
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
</body>
</html>
<%@include file="/footer.jspf"%>
<%@include file="/boostrap.jspf"%>