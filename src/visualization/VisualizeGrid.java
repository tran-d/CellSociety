package visualization;

import java.util.HashMap;
import java.util.Map;

import config.XMLReader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class initializes the grid for the simulation.
 * 
 * @author RyanChung, DavidTran
 *
 */
public class VisualizeGrid {

	private XMLReader myXML;
	private GridPane myGrid;
	private final int CELL_SIZE = 55;

	/**
	 * Constructor for VisualizeGrid Class.
	 */
	public VisualizeGrid() {
	}

	public GridPane getGrid() {
		return myGrid;
	}

	/**
	 * iterates through CellGrid and returns a GridPane with corresponding
	 * color(state) for each cell
	 * 
	 * @param xml
	 * 
	 * @return
	 */

	public GridPane makeGrid(XMLReader xml) {
		myGrid = new GridPane();
		Map<Integer, Color> myColorMap = xml.createColorMap();

		int[][] gridArray = xml.createCellGrid();

		colorGrid(myGrid, myColorMap, gridArray);
		myGrid.setGridLinesVisible(true);

		myGrid.setAlignment(Pos.CENTER);

		return myGrid;
	}

	private void colorGrid(GridPane myGrid, Map<Integer, Color> myColorMap, int[][] gridArray) {
		for (int i = 0; i < gridArray.length; i++) {
			for (int j = 0; j < gridArray.length; j++) {

				Color color = myColorMap.get(gridArray[i][j]);

				myGrid.add(new Rectangle(CELL_SIZE, CELL_SIZE, color), j, i);
			}
		}
	}

}
