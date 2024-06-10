package Xstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import Modelo.ModeloAlquiler;
import ObjectDB.AccesoAlquileres;

public class accesoXML {

	public static List<ModeloAlquiler> obtenerAlquileres() throws FileNotFoundException{
		XStream xstream = new XStream();
		xstream.alias("alquileres", ListaAlquileres.class);		
		xstream.alias("alquiler", ModeloAlquiler.class);
		xstream.aliasField("ID", ModeloAlquiler.class, "ID");
		xstream.aliasField("nombreInquilino", ModeloAlquiler.class, "NombreInquilino");
		xstream.aliasField("direccionPiso", ModeloAlquiler.class, "DireccionPiso");
		xstream.aliasField("importeAlquiler", ModeloAlquiler.class, "ImporteAlquiler");
		xstream.aliasField("duracionContrato", ModeloAlquiler.class, "DuracionContrato");
		xstream.addImplicitCollection(ListaAlquileres.class, "listaAlquiler");
		xstream.addPermission(AnyTypePermission.ANY);
		ListaAlquileres objetoListaAlquileres = 
				(ListaAlquileres) xstream.fromXML(new FileInputStream("data/alquileres.xml"));
		List<ModeloAlquiler> lista = objetoListaAlquileres.getListaAlquileres();

		return lista;
	}
	
	public static List<ModeloAlquiler> obtenerAlquileresBD() throws FileNotFoundException{
		List<ModeloAlquiler> lista = AccesoAlquileres.consultarAlquileres();
		return lista;
	}

	public static List<ModeloAlquiler>  importar() throws FileNotFoundException {
		XStream xstream = new XStream();
		xstream.alias("alquileres", ListaAlquileres.class);		
		xstream.alias("alquiler", ModeloAlquiler.class);
		xstream.aliasField("ID", ModeloAlquiler.class, "ID");
		xstream.aliasField("nombreInquilino", ModeloAlquiler.class, "NombreInquilino");
		xstream.aliasField("direccionPiso", ModeloAlquiler.class, "DireccionPiso");
		xstream.aliasField("importeAlquiler", ModeloAlquiler.class, "ImporteAlquiler");
		xstream.aliasField("duracionContrato", ModeloAlquiler.class, "DuracionContrato");
		xstream.addImplicitCollection(ListaAlquileres.class, "listaAlquiler");
		xstream.addPermission(AnyTypePermission.ANY);
		ListaAlquileres objetoListaAlquileres = 
				(ListaAlquileres) xstream.fromXML(new FileInputStream("data/alquileres.xml"));
		List<ModeloAlquiler> lista = objetoListaAlquileres.getListaAlquileres();

		for (ModeloAlquiler alq : lista) {
			alq.setID(0);
			AccesoAlquileres.insertar(alq);
		}

		return lista;
	}

	public static List<ModeloAlquiler> exportar() throws FileNotFoundException {

		List<ModeloAlquiler> lista = obtenerAlquileresBD();

		ListaAlquileres objetoListaAlquiler = new ListaAlquileres();
		objetoListaAlquiler.setListaAlquileres(lista);

		XStream xstream = new XStream();
		xstream.alias("alquileres", ListaAlquileres.class);		
		xstream.alias("alquiler", ModeloAlquiler.class);
		xstream.aliasField("ID", ModeloAlquiler.class, "ID");
		xstream.aliasField("nombreInquilino", ModeloAlquiler.class, "NombreInquilino");
		xstream.aliasField("direccionPiso", ModeloAlquiler.class, "DireccionPiso");
		xstream.aliasField("importeAlquiler", ModeloAlquiler.class, "ImporteAlquiler");
		xstream.aliasField("duracionContrato", ModeloAlquiler.class, "DuracionContrato");
		xstream.addImplicitCollection(ListaAlquileres.class, "listaAlquiler");
		xstream.addPermission(AnyTypePermission.ANY);

		xstream.toXML(objetoListaAlquiler, new FileOutputStream("data/alquileres2.xml"));
		return lista;
	}

}
