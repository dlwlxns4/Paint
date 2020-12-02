import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class SimplePainterModel {

	public int 		nDrawMode;
	public Point 	pt1, pt2;
	public int 		nSize;
	public boolean 	bFill; 
	public Color 	selectedColor;
	public Vector<Point> vStart;
	
	
	public SimplePainterModel() {
		nDrawMode = Constants.NONE;
		pt1 = new Point();
		pt2 = new Point();
		nSize =1;
		bFill = false;
		selectedColor = Color.black;
		vStart = new Vector<Point>();
	}
	
	public SimplePainterModel(SimplePainterModel data) {
		nDrawMode = data.nDrawMode;
		pt1 = data.pt1;
		pt2 = data.pt2;
		nSize = data.nSize;
		bFill = data.bFill;
		selectedColor = data.selectedColor;

		vStart = new Vector<Point>(data.vStart); // free전용 arraylist생성
		
	}
	
	
}
