<%@ page import="org.iesbelen.odt.FabricanteODT" %>
<%@ page import="java.util.List" %>
<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if (request.getAttribute("listaFabricantes") != null) {
        List<Fabricante> listaFabricante = (List<Fabricante>)request.getAttribute("listaFabricantes");
%>

<html>
<head>
    <title>Detalle Producto</title>
    <style>
        .clearfix::after {
            content: "";
            display: block;
            clear: both;
        }
    </style>
</head>
<body>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/tienda/productos/crear/" method="post">
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Crear Producto</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Crear"/>
                </div>
            </div>
        </div>

        <div class="clearfix">
            <hr/>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Nombre
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="nombre" />
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Precio
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="precio"/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Fabricante
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <select name="fabricante">
                    <% for (Fabricante fab : listaFabricante) { %>
                    <option value="<%= fab.getIdFabricante() %>"><%= fab.getNombre() %></option>
                    <% } %>
                </select>
            </div>
        </div>

    </form>
</div>
</body>
</html>

<%
    }
%>