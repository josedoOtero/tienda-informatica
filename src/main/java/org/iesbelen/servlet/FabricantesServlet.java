package org.iesbelen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.iesbelen.dao.FabricanteDAO;
import org.iesbelen.dao.FabricanteDAOImpl;
import org.iesbelen.model.Fabricante;
import org.iesbelen.odt.FabricanteODT;

@WebServlet(name = "fabricantesServlet", value = "/tienda/fabricantes/*")
public class FabricantesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		String pathInfo = request.getPathInfo();

		if (pathInfo == null || "/".equals(pathInfo)) {
			FabricanteDAO fabDAO = new FabricanteDAOImpl();

			// Obtener parámetros de ordenación
			String valorOrdenacion = request.getParameter("valor_ordenacion");
			String direcionOrdenacion = request.getParameter("direcion_ordenacion");

			System.out.println("Valor de ordenación: " + valorOrdenacion);
			System.out.println("Dirección de ordenación: " + direcionOrdenacion);

			List<FabricanteODT> listaFabricantesODT = new ArrayList<>();

			if (valorOrdenacion != null && !valorOrdenacion.isEmpty() && direcionOrdenacion != null && !direcionOrdenacion.isEmpty()) {
				// Ordenar según los parámetros
				if (valorOrdenacion.equals("nombre") && direcionOrdenacion.equals("asc")) {
					listaFabricantesODT = fabDAO.getAll().stream()
							.map(fabricante -> new FabricanteODT(fabricante))
							.sorted((f1, f2) -> f1.getNombre().compareTo(f2.getNombre()))
							.collect(Collectors.toList());
				} else if (valorOrdenacion.equals("nombre") && direcionOrdenacion.equals("desc")) {
					listaFabricantesODT = fabDAO.getAll().stream()
							.map(fabricante -> new FabricanteODT(fabricante))
							.sorted((f1, f2) -> f2.getNombre().compareTo(f1.getNombre()))
							.collect(Collectors.toList());
				} else if (valorOrdenacion.equals("idFabricante") && direcionOrdenacion.equals("asc")) {
					listaFabricantesODT = fabDAO.getAll().stream()
							.map(fabricante -> new FabricanteODT(fabricante))
							.sorted((f1, f2) -> Integer.compare(f1.getIdFabricante(), f2.getIdFabricante()))
							.collect(Collectors.toList());
				} else if (valorOrdenacion.equals("idFabricante") && direcionOrdenacion.equals("desc")) {
					listaFabricantesODT = fabDAO.getAll().stream()
							.map(fabricante -> new FabricanteODT(fabricante))
							.sorted((f1, f2) -> Integer.compare(f2.getIdFabricante(), f1.getIdFabricante()))
							.collect(Collectors.toList());
				}
			} else {
				listaFabricantesODT = fabDAO.getAll().stream()
						.map(fabricante -> new FabricanteODT(fabricante))
						.collect(Collectors.toList());
			}

			request.setAttribute("listaFabricantesODT", listaFabricantesODT);

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");

		} else {
			// Manejo de rutas con ID o edición
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");

			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				// Redirigir a la página de creación
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/crear-fabricante.jsp");
			} else if (pathParts.length == 2) {
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				try {
					// Obtener un fabricante por ID
					request.setAttribute("fabricante", fabDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/detalle-fabricante.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");
				}
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				try {
					// Obtener un fabricante por ID para editarlo
					request.setAttribute("fabricante", fabDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/editar-fabricante.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");
				}
			} else {
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");
			}
		}

		// Verificar que el dispatcher no es null antes de llamar a forward
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			// Si dispatcher es null, enviar una respuesta de error
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la configuración del servlet.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String __method__ = request.getParameter("__method__");
		FabricanteDAO fabDAO = new FabricanteDAOImpl();

		if (__method__ == null) {
			// Crear un nuevo fabricante
			String nombre = request.getParameter("nombre");
			Fabricante nuevoFab = new Fabricante();
			nuevoFab.setNombre(nombre);
			fabDAO.create(nuevoFab);
		} else if ("put".equalsIgnoreCase(__method__)) {
			doPut(request, response);
		} else if ("delete".equalsIgnoreCase(__method__)) {
			doDelete(request, response);
		} else {
			System.out.println("Opción POST no soportada.");
		}

		// Redirigir a la lista de fabricantes
		response.sendRedirect(request.getContextPath() + "/tienda/fabricantes");
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		Fabricante fab = new Fabricante();

		try {
			int id = Integer.parseInt(codigo);
			fab.setIdFabricante(id);
			fab.setNombre(nombre);
			fabDAO.update(fab);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");

		try {
			int id = Integer.parseInt(codigo);
			fabDAO.delete(id);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
}