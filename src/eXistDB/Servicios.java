package eXistDB;

import java.util.List;
import org.xmldb.api.base.XMLDBException;
import Modelo.ModeloAlquiler;
import Modelo.ModeloServicio;
import ObjectDB.AccesoAlquileres;
import Principal.Principal;
import entrada.Teclado;

public class Servicios {

	public static void escribirMenu() {
		System.out.println("----- Submenu de eXistDB -----");
		System.out.println("a. Insertar servicio.");
		System.out.println("b. Actualizar servicio.");
		System.out.println("c. Eliminar servicio.");
		System.out.println("d. Listar servicios.");
		System.out.println("e. MODIFICACION 4, servicio mas caro.");
		System.out.println("x. Volver al menu principal.");
		System.out.println("");
	}

	public static void main(String[] args) {
		int id, idAlquiler;
		double costoMensual;
		String opcion, tipoServicio, decision;
		ModeloServicio servicio;
		ModeloAlquiler alquiler;
		List<ModeloServicio> lista;
		List<String> listaString;
		ModeloAlquiler alq;

		do {
			escribirMenu();
			opcion = Teclado.leerCadena("Elija una opcion: ");
			try {
				switch(opcion) {
				case "a":
					id = Teclado.leerEntero("ID del servicio? ");
					if(!AccesoServicios.consultarServicioPorID(id)) {
						tipoServicio = Teclado.leerCadena("Tipo de servicio? ");
						costoMensual = Teclado.leerReal("Costo mensual del servicio? ");
						idAlquiler = Teclado.leerEntero("ID del alquiler? ");
						alquiler = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
						if(alquiler != null) {
							servicio = new ModeloServicio(id, tipoServicio, costoMensual, idAlquiler);
							if(AccesoServicios.insertar(servicio)) {
								System.out.println("Se ha insertado el servicio: " + tipoServicio + " en la base de datos.");
							}
							else {
								System.err.println("Error al insertar servicio en la base de datos.");
							}
						}
						else {
							System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
						}

					}
					else {
						System.out.println("Ese ID ya esta asociado a un servicio.");
					}

					break;
				case "b":
					AccesoServicios.LeerBD();
					lista = AccesoServicios.consultarServicios();
					if(!lista.isEmpty()) {
						for(ModeloServicio ms : lista) {
							System.out.println(ms.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un servicio de la base de datos.");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + " servicios de la base de datos.");
						}
					}
					else {
						System.out.println("No hay servicios en la base de datos.");
					}
					id = Teclado.leerEntero("ID del servicio a actualizar? ");
					if(AccesoServicios.consultarServicioPorID(id)) {
						tipoServicio = Teclado.leerCadena("Nuevo tipo de servicio? ");
						costoMensual = Teclado.leerReal("Nuevo costo mensual del servicio? ");
						idAlquiler = Teclado.leerEntero("Nuevo ID del alquiler? ");
						alquiler = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
						if(alquiler != null) {
							servicio = new ModeloServicio(id, tipoServicio, costoMensual, idAlquiler);
							if(AccesoServicios.actualizarServicio(servicio)) {
								System.out.println("Se ha actualizado un servicio en la base de datos.");
							}
							else {
								System.err.println("Error al actualizar servicio en la base de datos.");
							}
						}
					}
					else {
						System.err.println("No hay ningun servicio asociado al ID: " + id + " en la base de datos.");
					}
					break;
				case "c":
					AccesoServicios.LeerBD();
					lista = AccesoServicios.consultarServicios();
					if(!lista.isEmpty()) {
						for(ModeloServicio ms : lista) {
							System.out.println(ms.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un servicio de la base de datos.");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + " servicios de la base de datos.");
						}
					}
					else {
						System.out.println("No hay servicios en la base de datos.");
					}
					decision = Teclado.leerCadena("Desea borrar un solo servicio (1) o "
							+ "eliminar todos los servicios de un alquiler(2)? (1/2)");
					if(decision.equals("1")) {
						id = Teclado.leerEntero("ID de servicio a eliminar? ");
						if(AccesoServicios.consultarServicioPorID(id)) {
							if(AccesoServicios.eliminarServicio(id)) {
								System.out.println("Se ha eliminado el servicio con ID: " + id + " con exito.");
							}
							else {
								System.out.println("Error al eliminar servicios.");
							}
						}
						else {
							System.err.println("No hay ningun servicio asociado al ID: " + id + " en la base de datos.");
						}
					}
					else if(decision.equals("2")){
						idAlquiler = Teclado.leerEntero("ID de alquiler que desee borrar sus servicios? ");
						alq = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
						if(alq != null) {
							if(AccesoServicios.eliminarServicioPorIdAlquiler(idAlquiler)) {
								System.out.println("Se han eliminado todos los servicios del alquiler: " + idAlquiler + "." );
							}
							else {
								System.out.println("Error al eliminar servicios.");
							}
						}
						else {
							System.out.println("No hay ningun alquiler con el ID " + idAlquiler + " en la base de datos.");
						}
					}

					break;
				case "d":
					decision = Teclado.leerCadena("Desea ver todos los servicios (1) o filtrar por ID de alquiler (2)? (1/2)");
					if(decision.equals("1")) {
						AccesoServicios.LeerBD();
						lista = AccesoServicios.consultarServicios();
						if(!lista.isEmpty()) {
							for(ModeloServicio ms : lista) {
								System.out.println(ms.toString());
							}
							if(lista.size() == 1) {
								System.out.println("Se ha consultado un servicio de la base de datos.");
							}
							else if(lista.size() > 1) {
								System.out.println("Se han consultado " + lista.size() + " servicios de la base de datos.");
							}
						}
						else {
							System.out.println("No hay servicios en la base de datos.");
						}
					}
					else if(decision.equals("2")) {
						idAlquiler = Teclado.leerEntero("ID de alquiler a consultar servicios? ");
						alq = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
						if(alq != null) {
							listaString = AccesoServicios.consultarServiciosPorIdAlquiler(idAlquiler);
							if(!listaString.isEmpty()) {
								lista = AccesoServicios.convertirXmlAModeloServicios(listaString);
								for(ModeloServicio ms : lista) {
									System.out.println(ms.toString());
								}
								if(lista.size() == 1) {
									System.out.println("Se ha consultado un servicio de la base de datos.");
								}
								else if(lista.size() > 1) {
									System.out.println("Se han consultado " + lista.size() + " servicios de la base de datos.");
								}
							}
							else {
								System.out.println("El alquiler con id " + idAlquiler + " no tiene ningun servicio.");
							}
						}
						else {
							System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
						}
					}
					else {
						System.err.println("Error, por favor seleccione 1 o 2.");
					}
					break;
				case "e":
					AccesoServicios.LeerBD();
					servicio = AccesoServicios.servicioMasCaro();
					if(servicio != null) {
						System.out.println(servicio.toString());
					}
					else {
						System.out.println("No hay servicios en la base de datos.");
					}
					break;
				case "x":
					Principal.main(args);
					break;
				default:
					System.out.println("Opcion no valida.");
					break;

				}

			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("Error de clase no encontrada: " + cnfe.getMessage());
			} 
			catch (InstantiationException ie) {
				System.out.println("Error de instanciaci�n de base de datos XML: " + ie.getMessage());
			} 

			catch (IllegalAccessException iae) {
				System.out.println("Error de acceso ilegal: " + iae.getMessage());
			} 

			catch (XMLDBException xmldbe) {
				System.out.println("Error de base de datos XML: " + xmldbe.getMessage());
			}
			catch (Exception i) {
				System.out.println("Error: " + i.getMessage());
			}

		}
		while(!opcion.equals("x"));

	}

}
