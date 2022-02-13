package tienda;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Start {

	protected static Connection conexion;
	protected static Statement peticion;
	protected static ResultSet resultados;
	protected static Scanner entradaDatos = new Scanner(System.in);

	public static boolean crearProducto() {
		String producto;
		int precio, cantidad, resultado;

		try {
			peticion = (Statement) conexion.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.print("Introduzca el nombre del producto: ");
		entradaDatos.nextLine();
		producto = entradaDatos.nextLine();

		System.out.print("Introduzca el precio unitario del producto: ");
		try {
			precio = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		System.out.print("Introduzca la cantidad del producto: ");
		try {
			cantidad = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		try {

			resultado = peticion.executeUpdate("INSERT INTO `productos`(`id`, `nombre`, `precio`, `cantidad`)"
					+ "VALUES (NULL, '" + producto + "', '" + precio + "', '" + cantidad + "' )");

			if (resultado == 1) {
				return true;
			}

			else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static void listarProductos() {
		try {
			peticion = (Statement) conexion.createStatement();
			resultados = (ResultSet) peticion.executeQuery("SELECT * FROM productos");

			while (resultados.next()) {
				System.out.println(resultados.getString(1) + ". " + resultados.getString(2) + " - "
						+ resultados.getString(3) + "€ - " + resultados.getString(4) + " unidades");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error al conectar");
			System.exit(0);
		}
	}

	public static boolean modificarProducto() {
		String producto;
		int id, precio, cantidad, resultado;

		try {
			peticion = (Statement) conexion.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.print("Introduzca el identificador del producto: ");
		try {
			id = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		System.out.print("Introduzca el nombre del producto: ");
		entradaDatos.nextLine();
		producto = entradaDatos.nextLine();

		System.out.print("Introduzca el precio unitario del producto: ");
		try {
			precio = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		System.out.print("Introduzca la cantidad del producto: ");
		try {
			cantidad = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		try {

			resultado = peticion.executeUpdate("UPDATE `productos` SET `nombre`='" + producto + "',`precio`='" + precio
					+ "',`cantidad`='" + cantidad + "' WHERE `id` = '" + id + "'");

			if (resultado == 1) {
				return true;
			}

			else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static boolean borrarProducto() {

		int id, resultado;

		try {
			peticion = (Statement) conexion.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.print("Introduzca el identificador del producto: ");
		try {
			id = entradaDatos.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Introduzca un número entero.");
			entradaDatos.next();
			return false;
		}

		try {

			resultado = peticion.executeUpdate("DELETE FROM `productos` WHERE `id`='" + id + "'");

			if (resultado == 1) {
				return true;
			}

			else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		int option = -1;

		try {
			DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
			conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
			System.out.println("Conectado");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error al conectar");
			System.exit(0);
		}

		do {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("\n* * * GESTOR DE PRODUCTOS * * *");
			System.out.println("1) Crear un producto");
			System.out.println("2) Listar todos los productos");
			System.out.println("3) Modificar un producto");
			System.out.println("4) Borrar un producto");
			System.out.println("0) Salir del programa");

			System.out.print("Opción: ");
			try {
				option = entradaDatos.nextInt();
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Introduzca un número válido");
				entradaDatos.next();
				continue;
			}

			switch (option) {
			case 1:
				if (crearProducto()) {
					System.out.println("Producto añadido correctamente.");
					break;
				}

				else {
					System.err.println("No se ha podido crear el producto.");
					break;
				}

			case 2:
				listarProductos();
				break;

			case 3:
				if (modificarProducto()) {
					System.out.println("Producto modificado correctamente.");
					break;
				}

				else {
					System.err.println("El producto no se ha podido modificar.");
					break;
				}

			case 4:
				if (borrarProducto()) {
					System.out.println("Producto borrado correctamente.");
					break;
				}

				else {
					System.err.println("El producto no pudo ser eliminado.");
					break;
				}

			case 0:
				System.out.println("\n* * * FIN DEL PROGRAMA * * *");
				break;

			default:
				System.err.println("Opción errónea.");
			}

		} while (option != 0);
	}
}