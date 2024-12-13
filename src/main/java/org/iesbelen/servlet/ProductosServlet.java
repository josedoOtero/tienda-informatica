package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.FabricanteDAOImpl;
import org.iesbelen.dao.ProductoDAO;
import org.iesbelen.dao.ProductoDAOImpl;
import org.iesbelen.model.Fabricante;
import org.iesbelen.model.Producto;

import java.io.IOException;

@WebServlet(name = "productosServlet", value = "/tienda/productos/*")
public class ProductosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP Method: GET
	 * Paths: 
	 * 		/productos/
	 * 		/productos/{id}
	 * 		/productos/editar{id}
	 * 		/productos/crear
	 */		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
				
		String pathInfo = request.getPathInfo();
			
		if ((pathInfo == null || "/".equals(pathInfo)) || (pathInfo != null && pathInfo.startsWith("/productos"))) {
			ProductoDAO prodDao = new ProductoDAOImpl();
			FabricanteDAOImpl fabDao = new FabricanteDAOImpl();
			String busqueda = request.getParameter("busqueda");


			if (busqueda != null) {
				request.setAttribute("listaProductos", prodDao.getfiltradoName(busqueda));
				System.out.println(prodDao.getfiltradoName(busqueda));

			} else {
				request.setAttribute("listaProductos", prodDao.getAll());
			}

			request.setAttribute("listaFabricantes", fabDao.getAll());
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
			        		       
		} else {
			// GET
			// 		/productos/{id}
			// 		/productos/{id}/
			// 		/productos/edit/{id}
			// 		/productos/edit/{id}/
			// 		/productos/crear
			// 		/productos/crear/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /productos/crear
				FabricanteDAOImpl fabDao = new FabricanteDAOImpl();
				request.setAttribute("listaFabricantes", fabDao.getAll());
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/crear-producto.jsp");
        												
			
			}else if(pathParts.length == 2){
				ProductoDAO fabDAO = new ProductoDAOImpl();
				// GET
				// /productos/{id}
				try {
					request.setAttribute("producto",fabDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
				}
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				ProductoDAO fabDAO = new ProductoDAOImpl();
				
				// GET
				// /productos/editar/{id}
				try {
					request.setAttribute("producto",fabDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-producto.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
			 
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String __method__ = request.getParameter("__method__");

		if (__method__ == null) {
			// Crear un nuevo producto
			try {
				ProductoDAO prodDAO = new ProductoDAOImpl();

				String nombre = request.getParameter("nombre");
				Double precio = Double.parseDouble(request.getParameter("precio"));
				int fabricanteId = Integer.parseInt(request.getParameter("fabricante"));

				// Crea y configura el nuevo producto
				Producto nuevoProducto = new Producto();
				nuevoProducto.setNombre(nombre);
				nuevoProducto.setPrecio(precio);
				nuevoProducto.setCodigo_fabricante(fabricanteId);

				prodDAO.create(nuevoProducto);

			} catch (NumberFormatException e) {
				e.printStackTrace();
				// Manejo adicional para enviar un error al usuario o redirigir a una página de error si prefieres
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("put".equalsIgnoreCase(__method__)) {
			doPut(request, response);
		} else if ("delete".equalsIgnoreCase(__method__)) {
			doDelete(request, response);
		} else {
			System.out.println("Método POST no soportado.");
		}
		response.sendRedirect(request.getContextPath() + "/tienda/productos");
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductoDAO fabDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		Producto fab = new Producto();
		
		try {
			
			int id = Integer.parseInt(codigo);
			fab.setIdProducto(id);
			fab.setNombre(nombre);
			fabDAO.update(fab);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	{
		RequestDispatcher dispatcher;
		ProductoDAO fabDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		fabDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
}
