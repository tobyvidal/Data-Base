package eXistDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import Modelo.ModeloServicio;
import java.io.*;



public class AccesoServicios {

	public static boolean insertar(ModeloServicio Mservicio) throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, XMLDBException {
		Collection coleccion = null;

		Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
		Database database = (Database) cl.newInstance();
		DatabaseManager.registerDatabase(database);

		String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
		coleccion = DatabaseManager.getCollection(url, "admin", "admin");
		if (coleccion == null) {
			return false;
		}
		else {
			XPathQueryService servicio = 
					(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

			if (consultarServicioPorID(Mservicio.getId())) {
				return false;
			}
			else {
				String sentenciaInsertarProducto = 
						"update insert " +
								"<Servicio>" +
								"<ID>" + Mservicio.getId()+ "</ID>" +
								"<TipoServicio>" + Mservicio.getTipoServicio() + "</TipoServicio>" +
								"<CostoMensual>" + String.format("%.2f", Mservicio.getCostoMensual()) + "</CostoMensual>" +
								"<IdAlquiler>" + Mservicio.getIdAlquiler() + "</IdAlquiler>" +
								"</Servicio> " +
								"into /ServiciosAsociados";
				ResourceSet resultados2 = servicio.query(sentenciaInsertarProducto);
				coleccion.close();
				return true;
			}

		}	

	}

	public static boolean consultarServicioPorID (int id) throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, XMLDBException {
		Collection coleccion = null;

		Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
		Database database = (Database) cl.newInstance();
		DatabaseManager.registerDatabase(database);

		String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
		coleccion = DatabaseManager.getCollection(url, "admin", "admin");
		if (coleccion == null) {
			return false;
		}
		else {
			String sentenciaBuscarServicioPorID = 
					"for $serv in /ServiciosAsociados/Servicio " +
							" where $serv/ID = " + id +
							" return $serv";
			XPathQueryService servicio = 
					(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorID);

			ResourceIterator iterador = resultados.getIterator();
			if (iterador.hasMoreResources()) {
				return true;
			}
			else {
				return false;
			}
		}

	}

	public static List<String> consultarServiciosPorIdAlquiler(int idAlquiler) throws Exception {
		Collection coleccion = null;
		List<String> listaServicios = new ArrayList<>();

		Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
		Database database = (Database) cl.newInstance();
		DatabaseManager.registerDatabase(database);

		String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
		coleccion = DatabaseManager.getCollection(url, "admin", "admin");
		if (coleccion == null) {
			return listaServicios;
		}
		else {
			String sentenciaBuscarServicioPorIdAlquiler = 
					"for $serv in /ServiciosAsociados/Servicio " +
							" where $serv/IdAlquiler = " + idAlquiler +
							" return $serv";
			XPathQueryService servicio = 
					(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorIdAlquiler);

			ResourceIterator iterador = resultados.getIterator();
			while (iterador.hasMoreResources()) {
				XMLResource resource = (XMLResource) iterador.nextResource();
				listaServicios.add((String) resource.getContent());
			}
			return listaServicios;
		}
	}

	public static List<ModeloServicio> convertirXmlAModeloServicios(List<String> listaXml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		List<ModeloServicio> listaServicios = new ArrayList<>();
		for (String xml : listaXml) {
			InputSource is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			Element element = doc.getDocumentElement();

			int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
			String tipoServicio = element.getElementsByTagName("TipoServicio").item(0).getTextContent();
			String costoMensualStr = element.getElementsByTagName("CostoMensual").item(0).getTextContent().replace(",",".");
			double costoMensual = Double.parseDouble(costoMensualStr);
			int idAlquiler = Integer.parseInt(element.getElementsByTagName("IdAlquiler").item(0).getTextContent());
			ModeloServicio servicio = new ModeloServicio(id, tipoServicio, costoMensual, idAlquiler);
			listaServicios.add(servicio);
		}

		return listaServicios;
	}

	public static void LeerBD () throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, XMLDBException, IOException {
		File file = new File("C:\\Estudio\\Acceso a Datos\\TrabajoFinal\\data\\ServiciosAsociados2_eXist.xml");
		Collection coleccion = null;
		List<String> lista = new ArrayList<>();
		Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
		Database database = (Database) cl.newInstance();
		DatabaseManager.registerDatabase(database);

		String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
		coleccion = DatabaseManager.getCollection(url, "admin", "admin");
		if (coleccion == null) {

		}
		else {
			String sentenciaBuscarServicioPorID = 
					"for $serv in /ServiciosAsociados " +
							" return $serv";
			XPathQueryService servicio = 
					(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorID);

			ResourceIterator iterador = resultados.getIterator();
			while (iterador.hasMoreResources()) {
				XMLResource resource = (XMLResource) iterador.nextResource();
				lista.add((String) resource.getContent());
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			for (String str : lista) {
				writer.write(str);
				writer.newLine();
			}
			writer.close();
		}

	}

	public static String readXMLFile() {
		StringBuilder data = new StringBuilder();
		try {
			File inputFile = new File("C:\\Estudio\\Acceso a Datos\\TrabajoFinal\\data\\ServiciosAsociados2_eXist.xml");
			Scanner myReader = new Scanner(inputFile);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				data.append(line).append("\n");
			}
			myReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.toString();
	}

	public static List<ModeloServicio> consultarServicios() throws Exception {
		File file = new File("C:\\Estudio\\Acceso a Datos\\TrabajoFinal\\data\\ServiciosAsociados2_eXist.xml");
		File file2 = new File("C:\\Estudio\\Acceso a Datos\\TrabajoFinal\\data\\ServiciosAsociados_eXist.xml");
		String xml = readXMLFile();
		List<ModeloServicio> lista = new ArrayList<>();
		DocumentBuilderFactory  factory = DocumentBuilderFactory .newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document documento = builder.parse(new InputSource(new StringReader(xml)));
		documento.getDocumentElement().normalize();
		NodeList listadoNodosServicio = documento.getElementsByTagName("Servicio");

		for(int i = 0; i < listadoNodosServicio.getLength(); i++) {
			Node nodoServicio = listadoNodosServicio.item(i);
			if(nodoServicio.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodoServicio;
				int id = Integer.parseInt(elemento.getElementsByTagName("ID").item(0).getTextContent());
				String tipoServicio = elemento.getElementsByTagName("TipoServicio").item(0).getTextContent();
				String costoMensualStr = elemento.getElementsByTagName("CostoMensual").item(0).getTextContent().replace(",",".");
				double costoMensual = Double.parseDouble(costoMensualStr);
				int idAlquiler = Integer.parseInt(elemento.getElementsByTagName("IdAlquiler").item(0).getTextContent());
				ModeloServicio servicio = new ModeloServicio(id, tipoServicio, costoMensual, idAlquiler);
				lista.add(servicio);
			}
		}
		file2.delete();
		file.renameTo(file2);
		return lista;
	}
	
	public static ModeloServicio servicioMasCaro() throws Exception {
		List<ModeloServicio> lista = consultarServicios();
		ModeloServicio s = null;
		double precio = 0;
		
		for(ModeloServicio serv : lista) {
			if(precio < serv.getCostoMensual()) {
				precio = serv.getCostoMensual();
				s = serv;
			}
		}
		return s;
		
	}

	public static boolean actualizarServicio(ModeloServicio s) {
		Collection coleccion = null;
		String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
		try {
			coleccion = DatabaseManager.getCollection(url, "admin", "admin");
			if (coleccion == null) {
				return false;
			} else {
				String sentenciaBuscarServicioPorID = 
						"for $serv in /ServiciosAsociados/Servicio " +
								" where $serv/ID = " + s.getId() +
								" return $serv";

				XPathQueryService servicio = 
						(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
				ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorID);

				ResourceIterator iterador = resultados.getIterator();
				if (iterador.hasMoreResources()) {
					String sentenciaActualizarServicio = 
							"update replace " +
									"/ServiciosAsociados/Servicio[ID = " + s.getId() + "] with " +
									"<Servicio>" +
									"<ID>" + s.getId() + "</ID>" + 
									"<TipoServicio>" + s.getTipoServicio() + "</TipoServicio>" + 
									"<CostoMensual>" + String.format("%.2f", s.getCostoMensual()) + "</CostoMensual>" + 
									"<IdAlquiler>" + s.getIdAlquiler() + "</IdAlquiler>" +
									"</Servicio>";        
					ResourceSet resultados2 = servicio.query(sentenciaActualizarServicio);
					return true;
				} else {
					return false;
				}
			}
		} catch (XMLDBException e) {
			// Handle exception
			e.printStackTrace();
			return false;
		} finally {
			if (coleccion != null) {
				try {
					coleccion.close();
				} catch (XMLDBException e) {
					// Handle exception
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean eliminarServicio(int id) {
		Collection coleccion = null;
		try {
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);

			String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
			coleccion = DatabaseManager.getCollection(url, "admin", "admin");
			if (coleccion == null) {
				return false;
			}
			else {
				String sentenciaBuscarServicioPorID = 
						"for $serv in /ServiciosAsociados/Servicio " +
								" where $serv/ID = " + id +
								" return $serv";

				XPathQueryService servicio = 
						(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
				ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorID);

				ResourceIterator iterador = resultados.getIterator();
				if (iterador.hasMoreResources()) {
					String sentenciaEliminarServicio = 
							"update delete " +
									"/ServiciosAsociados/Servicio[ID = " + id + "]";
					ResourceSet resultados2 = servicio.query(sentenciaEliminarServicio);

					return true;
				}
				else {
					return false;
				}

			}
		} 
		catch (ClassNotFoundException cnfe) {

		} 
		catch (InstantiationException ie) {

		} 
		catch (IllegalAccessException iae) {

		} 
		catch (XMLDBException xmldbe) {

		}
		finally {
			if (coleccion != null) {
				try {
					coleccion.close();
				} 
				catch (XMLDBException xmldbe) {

				}
			}
		}
		return false;
	}

	public static boolean eliminarServicioPorIdAlquiler(int idAlquiler) {
		Collection coleccion = null;
		try {
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);

			String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db/coleccionServicios";
			coleccion = DatabaseManager.getCollection(url, "admin", "admin");
			if (coleccion == null) {
				return false;
			}
			else {
				String sentenciaBuscarServicioPorID = 
						"for $serv in /ServiciosAsociados/Servicio " +
								" where $serv/IdAlquiler = " + idAlquiler +
								" return $serv";

				XPathQueryService servicio = 
						(XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
				ResourceSet resultados = servicio.query(sentenciaBuscarServicioPorID);

				ResourceIterator iterador = resultados.getIterator();
				if (iterador.hasMoreResources()) {
					String sentenciaEliminarServicio = 
							"update delete " +
									"/ServiciosAsociados/Servicio[IdAlquiler = " + idAlquiler + "]";
					ResourceSet resultados2 = servicio.query(sentenciaEliminarServicio);

					return true;
				}
				else {
					return false;
				}

			}
		} 
		catch (ClassNotFoundException cnfe) {

		} 
		catch (InstantiationException ie) {

		} 
		catch (IllegalAccessException iae) {

		} 
		catch (XMLDBException xmldbe) {

		}
		finally {
			if (coleccion != null) {
				try {
					coleccion.close();
				} 
				catch (XMLDBException xmldbe) {

				}
			}
		}
		return false;
	}
}