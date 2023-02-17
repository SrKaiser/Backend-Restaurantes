package arso.proyecto;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Tarea1 {

	public static void main(String[] args) throws Exception {
		String codigoPostal, codigoPais;
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un código de país: ");
		codigoPais = sc.nextLine();
		System.out.print("Introduzca un código postal: ");
		codigoPostal = sc.nextLine();

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		// 3. Analizar el documento
		String API = "http://api.geonames.org/findNearbyPostalCodes?";
		String parameters = "postalcode=" + codigoPostal + "&country=" + codigoPais + "&radius=10&username=arso";
		URL url = new URL(API + parameters);
		downloadFile(url, "xml/codigopostal.xml");
		Document documento = analizador.parse("xml/codigopostal.xml");

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
	}

	public static void downloadFile(URL url, String fileName) throws Exception {
		File archivo = new File(fileName);
		if (archivo.exists()) {
		    archivo.delete();
		}

		try (InputStream in = url.openStream()) {
			Files.copy(in, Paths.get(fileName));
		}
	}

}
