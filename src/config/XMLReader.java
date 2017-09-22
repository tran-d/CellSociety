package config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cell.Cell;
import javafx.scene.paint.Color;

import org.w3c.dom.Node;

/**
 * This is the class that parses the input XML file.
 * 
 * @author DavidTran
 *
 */
public class XMLReader {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File xmlFile;

	private String simulationType;
	private String edgeType;
	private Map<Integer, Color> colorMap;
	private Map<String, Double> parameterMap;
	private int[][] stateGrid;
	private int numRows;
	private int numCols;

	/**
	 * Initialize DOMParser, colorMap, cellStateGrid, simulationType;
	 * 
	 * @param xmlInput
	 *            file to be chosen by user.
	 * 
	 */
	public XMLReader(File xmlInput) {

		xmlFile = xmlInput;
		
		initDOMParser();
		
		simulationType = setSimulationType();
		System.out.println("Simulation Type: " + simulationType);
		
		edgeType = setEdgeType();
		System.out.println("Edge Type: " + edgeType);
		
		colorMap = createColorMap();
		
		parameterMap = createParameterMap();

		stateGrid = createCellGrid();

	}

	/**
	 * Initialize XML file parser.
	 */
	private void initDOMParser() {

		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with XML File");
			// add more "error" code later
		}
	}
	
	/**
	 * Retrieve the name of the simulation type.
	 */
	public String setSimulationType() {
		
		return doc.getDocumentElement().getNodeName();
	}
	
	/**
	 * Retrieve the name of the edge type.
	 */
	public String setEdgeType() {
		
		NodeList nList = doc.getElementsByTagName("edge");
		Element element = (Element) nList.item(0);
		
		return element.getAttribute("type");
	}

	/**
	 * Generate a mapping of cell state and color.
	 */
	public Map<Integer, Color> createColorMap() {

		colorMap = new HashMap<Integer, Color>();
		NodeList nList = doc.getElementsByTagName("colormap");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);
			Element eElement = (Element) nNode;

			Integer state = Integer.parseInt(eElement.getAttribute("cellState"));
			Color color = Color.valueOf(eElement.getAttribute("color"));
			colorMap.put(state, color);
		}
		
		return colorMap;
	}

	/**
	 * Generate a mapping of parameter name and value.
	 * 
	 * @return
	 */
	public Map<String, Double> createParameterMap() {

		parameterMap = new HashMap<String, Double>();
		NodeList nList = doc.getElementsByTagName("parametermap");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);
			Element eElement = (Element) nNode;

			String name = eElement.getAttribute("name");
			Double value = Double.parseDouble(eElement.getAttribute("value"));
			parameterMap.put(name, value);
		}
		
		System.out.println(parameterMap);
		return parameterMap;
	}
	
	
	/**
	 * Generate a grid of cell states.
	 */
	public int[][] createCellGrid() {
		
		NodeList nList = doc.getElementsByTagName("row");

		numRows = nList.getLength();
		numCols = numRows;
		stateGrid = new int[numRows][numCols];

		// iterate through row entries
		for (int i = 0; i < numRows; i++) {

			Node currentRow = nList.item(i);

			String row = ((Element) currentRow).getAttribute("cellStates");
			List<String> colStates = Arrays.asList(row.toString().split(","));
			System.out.println(colStates);

			// iterate through each column in for current row
			for (int j = 0; j < numCols; j++) {
				// can create cell grid
				stateGrid[i][j] = Integer.parseInt(colStates.get(j));
			}
		}
//		System.out.println(Arrays.deepToString(stateGrid));

		return stateGrid;
	}

	/**
	 * Tests the XML reader for parsing.
	 */
	public static void main(String args[]) {
		File xml = new File("/Users/DavidTran/eclipse-workspace/cellsociety_team10/src/resources/segregation.xml");
		XMLReader reader = new XMLReader(xml);
	}
	
	
}
