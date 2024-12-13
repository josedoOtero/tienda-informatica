package org.iesbelen.servlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.*;
import org.iesbelen.model.Fabricante;
import org.iesbelen.model.Producto;
import org.iesbelen.model.Usuario;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.iesbelen.servlet.hash.hashPassword;

@WebServlet(name = "usuariosServlet", value = "/tienda/usuarios/*")

public class UsuariosServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            List<Usuario> usuarios = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", usuarios);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                UsuarioDAO userDao = new UsuarioDAOImpl();
                request.setAttribute("listaUsuarios", userDao.getAll());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crear-usuario.jsp");

            } else if (pathParts.length == 2) {
                UsuarioDAO userDAO = new UsuarioDAOImpl();
                try {
                    // Obtener un usuario por ID
                    request.setAttribute("usuario", userDAO.find(Integer.parseInt(pathParts[1])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/detalle-usuario.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
                }
            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                UsuarioDAO userDAO = new UsuarioDAOImpl();
                try {
                    // Obtener un fabricante por ID para editarlo
                    request.setAttribute("usuario", userDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editar-usuario.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
                }
            } else {
                // Si no es /crear ni tiene un ID, se muestra la lista de usuarios
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
                List<Usuario> usuarios = usuarioDAO.getAll();
                request.setAttribute("listaUsuarios", usuarios);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
            }
        }

        // Realiza la redirección o envío
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            try {
                UsuarioDAO userDAO = new UsuarioDAOImpl();

                String usuario = request.getParameter("usuario");
                String password = request.getParameter("password");
                String rol = request.getParameter("rol");
                System.out.println("Nuevo user: "+usuario+" "+password+" "+rol);

                // Crea y configura el nuevo producto
                Usuario nuevoUser = new Usuario();
                nuevoUser.setUsuario(usuario);
                nuevoUser.setPassword(password);
                nuevoUser.setRol(rol);

                userDAO.create(nuevoUser);

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
        response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO userDAO = new UsuarioDAOImpl();
        String idUsuario = request.getParameter("idUsuario");
        String usuario = request.getParameter("usuario");
        String pasword = null;
        try {
            pasword = hashPassword(request.getParameter("pasword"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String rol = request.getParameter("rol");

        try {
            Usuario user = new Usuario();
            int id = Integer.parseInt(idUsuario);
            user.setUsuario(usuario);
            user.setPassword(pasword);
            user.setRol(rol);

            userDAO.update(user);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){
        UsuarioDAO userDAO = new UsuarioDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);
            userDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}
