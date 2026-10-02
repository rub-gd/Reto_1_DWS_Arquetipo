package com.reto1; //Se usa este paquete aprovechando que así es como está nombrado el group id en el pom.

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios") //Significado: Cuando alguien haga una petición a "/usuarios", utiliza esta clase.
public class UsuariosServlet extends HttpServlet {

    private Connection conexion; //Significado: Estamos creando una variable llamada conexion capaz de guardar la conexión con "MySQL".
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html"); //Significado: Lo que te voy a enviar es HTML.

        String url = "jdbc:mysql://localhost:3306/Pruebas_conexión_arquetipo";
        //jdbc, significa: Java Database Connectivity.
        //mysql, le estamos diciendo a JDBC: La base de datos con la que quiero comunicarme es "MySQL."
        //localhost, significa: El ordenador en el que estoy ahora mismo.
        //3306 es el puerto en el que normalmente escucha MySQL.
        //Pruebas_conexión_arquetipo, es el nombre de la base de datos.

        String usuario = "root"; //Significado: Se pone tu nombre de usuario guardado en "MySQL".
        String contraseña = "root"; //Significado: Se pone tu contraseña del usuario guardado en "MySQL".
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Significado: Carga el controlador JDBC de MySQL.

            conexion = DriverManager.getConnection(url, usuario, contraseña); //Significado: Ahora utiliza ese controlador para conectarte a esta base de datos.
            //"conexion" representa nuestra conexión con MySQL.

            Statement statement = conexion.createStatement(); //Con esto le pedimos a esa conexión que nos prepare un objeto capaz de ejecutar instrucciones SQL. Ese objeto lo guardamos en "Statement statement".
            
            ResultSet resultado = statement.executeQuery("SELECT * FROM Usuarios"); //Significado: Esto ejecuta el comando "SELECT * FROM Usuarios" de SQL y se almacena en "resultado".

            while (resultado.next()) { //El "resultado.next" significa que avanza a la siguiente fila.

                int id = resultado.getInt("id_usuario");
                String nombre = resultado.getString("nombre_usuario");
                String apellido1 = resultado.getString("apellido1");
                String apellido2 = resultado.getString("apellido2");
                int edad = resultado.getInt("edad");
                
                response.getWriter().println(
                    "<p>" + id + " - " + nombre + " " + apellido1 + " " + apellido2 + " - " + edad + " años</p>"
                );
            }

        } catch (SQLException | ClassNotFoundException e) {

            response.getWriter().println("<h1>Error de conexión</h1>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
        }
    }
}
