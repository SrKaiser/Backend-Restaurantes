package arso.proyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		String parameters = "lang=es&postalcode=" + codigoPostal + "&country=" + codigoPais
				+ "&radius=10&username=arso";
		URL url = new URL(API + parameters);
		Document documento = analizador.parse(url.openStream());

		System.out.println("--             Tarea 1             --");
		List<String> titulos = consultarTitulos(documento);

		System.out.println();

		System.out.println("--             Tarea 2             --");
		for (int i = 0; i < titulos.size(); i++) {
			titulos.set(i, titulos.get(i).replace(" ", "_"));
		}
		titulos.stream().forEach(u -> System.out.println(u));

		for (String URL : titulos) {
			System.out.println(URL);
			try {
				URL urlJson = new URL("https://es.dbpedia.org/data/" + URL + ".json");
				InputStreamReader reader = new InputStreamReader(urlJson.openStream());
				JsonReader jsonReader = Json.createReader(reader);
				// Creo un objeto JsonObject a partir del contenido de la URL
				JsonObject obj = jsonReader.readObject();
				JsonObject resourceObject = obj.getJsonObject("http://es.dbpedia.org/resource/" + URL);
				// Acceder a la propiedad deseada utilizando su clave
				JsonArray array = resourceObject.getJsonArray("http://dbpedia.org/ontology/abstract");
				String value;
				if (array != null) {
					JsonObject abstractObject = array.getJsonObject(0);
					value = abstractObject.getString("value");
					System.out.println("Resumen: " + value);
				} else {
					System.out.println("No hay resumen");
				}

				array = resourceObject.getJsonArray("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
				if (array != null) {
					for (JsonObject categoria : array.getValuesAs(JsonObject.class)) {
						value = categoria.getString("value");
						System.out.println("Categoria: " + value);
					}
				} else {
					System.out.println("No hay categorías");
				}

				array = resourceObject.getJsonArray("http://dbpedia.org/ontology/wikiPageExternalLink");
				if (array != null) {
					for (JsonObject enlace : array.getValuesAs(JsonObject.class)) {
						value = enlace.getString("value");
						System.out.println("Enlace: " + value);
					}
				} else {
					System.out.println("No hay enlaces");
				}
				
				array = resourceObject.getJsonArray("http://es.dbpedia.org/property/imagen");
				if (array != null) {
					for (JsonObject imagen : array.getValuesAs(JsonObject.class)) {
						value = imagen.getString("value");
						System.out.println("Imagen: " + value);
					}
				} else {
					System.out.println("No hay imágenes");
				}

			} catch (IOException e) {
				System.err.println("Error al leer el archivo JSON: " + e.getMessage());
			}
			System.out.println();
		}
	}

	public static List<String> consultarTitulos(Document documento) {
		LinkedList<String> titulosString = new LinkedList<>();
		NodeList elementos = documento.getElementsByTagName("entry");
		for (int i = 0; i < elementos.getLength(); i++) {
			Element elemento = (Element) elementos.item(i);
			NodeList titulos = elemento.getElementsByTagName("title");
			Element titulo = (Element) titulos.item(0);
			String tituloString = titulo.getTextContent();
			System.out.println(tituloString);
			titulosString.add(tituloString);
		}
		return titulosString;
	}

}
