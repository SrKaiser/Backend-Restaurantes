package arso.proyecto;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.events.StartElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//Scanner sc = new Scanner(System.in);
//System.out.print("Introduzca un código de país: ");
//codigoPais = sc.nextLine();
//System.out.print("Introduzca un código postal: ");
//codigoPostal = sc.nextLine();
//sc.close();

public class Tarea12 {

	public static void main(String[] args) throws Exception {
		String codigoPostal, codigoPais;
		codigoPostal = "30001";
		codigoPais = "ES";

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		// 3. Analizar el documento
		String API = "http://api.geonames.org/findNearbyWikipedia?";
		String parameters = "lang=es&postalcode=" + codigoPostal + "&country=" + codigoPais + "&radius=10&username=arso";
		URL url = new URL(API + parameters);
		Document documento = analizador.parse(url.openStream());
		
		System.out.println("--             Tarea 1             --");
		consultarTitulos(documento);
		
//		// 1. Construye la factoría de transformación y obtiene un transformador
//		TransformerFactory tFactoria = TransformerFactory.newInstance();
//		Transformer transformacion = tFactoria.newTransformer();
//		// 2. Establece la entrada, como un árbol DOM
//		Source input = new DOMSource(documento);
//		// 3. Establece la salida, un fichero en disco
//		File fichero = new File("xml/codigopostal.xml");
//		Result output = new StreamResult(fichero);
//		// 4. Aplica la transformación
//		transformacion.transform(input, output);
		
		System.out.println();
		
//		System.out.println("--             Tarea 2             --");
//		List<String> urlDBPedia = obtenerURLDBPedia(documento);
//		urlDBPedia.stream().forEach(u -> System.out.println(u));
//		
//		for(int i=0; i<urlDBPedia.size(); i++) {
//			URL urlJson = new URL(urlDBPedia.get(i));
//			downloadFile(urlJson, "json/aux"+i+".json");
//			InputStreamReader fuente = new FileReader("json/aux"+i+".json"); 
//			JsonReader jsonReader = Json.createReader(fuente);
//			JsonObject obj = jsonReader.readObject();
//			JsonArray resumen = obj.getJsonArray("http://dbpedia.org/ontology/abstract");
//			System.out.println(resumen); 
//			for (JsonObject valor : resumen.getValuesAs(JsonObject.class)) { 
//	        	
//	          	if (valor.containsKey("value"))
//	                System.out.println("Value: " + valor.getString("value"));
//
//	        }
//		}
	}

	
	public static void consultarTitulos(Document documento) {
		NodeList elementos = documento.getElementsByTagName("entry");
		for (int i = 0; i < elementos.getLength() ; i++) {
			Element elemento = (Element) elementos.item(i);
			NodeList titulos = elemento.getElementsByTagName("title");
			Element titulo = (Element) titulos.item(0);
			String tituloString = titulo.getTextContent();
			System.out.println(tituloString);
		}
	}
	
	public static List<String> obtenerURLDBPedia(Document documento) {
		LinkedList<String> URLDBPedia = new LinkedList<>();
		String urlDefecto = "https://es.dbpedia.org/data/";
		NodeList elementos = documento.getElementsByTagName("entry");
		for (int i = 0; i < elementos.getLength() ; i++) {
			Element elemento = (Element) elementos.item(i);
			NodeList urls = elemento.getElementsByTagName("wikipediaUrl");
			Element url = (Element) urls.item(0);
			String urlString = url.getTextContent();
			String[] split = urlString.split("https://es.wikipedia.org/wiki/"); 
			URLDBPedia.add(urlDefecto + split[1]+".json");
		}
		return URLDBPedia;
	}

}
