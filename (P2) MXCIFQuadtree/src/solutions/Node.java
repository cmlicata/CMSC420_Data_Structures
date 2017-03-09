package solutions;

import java.awt.geom.Rectangle2D;

/**
 * 
 * @author Michael Kalu
 * @category Project 2
 * CMSC 420
 *
 */
public class Node {
	// The rectangle representing this current node
    public Rectangle2D.Double quad;

    public Node father;
    
    // Each node has a child for each quadrant
    public Node NW;
    public Node NE;
    public Node SW;
    public Node SE;
    
    // These two fields are to keep a number for where the axis's are
    public double x_axis;        
    public double y_axis;        
    
    // Each node has a X and Y axis BST
    public BST XAxis;
    public BST YAxis;
    
    // This field will keep count of the number of rectangles inserted in this node
    public int numOfRect;


    /**
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public Node(double x, double y, double w, double h){
        this.quad = new Rectangle2D.Double(x, y, w, h);
        
        this.XAxis = new BST(x, x+w, x+(w/2));       
        this.YAxis = new BST(y, y+h, y+(h/2));
        
        this.x_axis = y + (h/2.0);
        this.y_axis = x + (w/2.0);
        
        this.NW = null;
        this.NE = null;
        this.SW = null;
        this.SE = null;
             
        this.numOfRect = 0;

    }
    
    /**
     * 
     * @returns The quad for the current node
     */
    public Rectangle2D.Double getQuad(){
    	return this.quad;
    }
    
    /**
     * 
     * @returns the X axis BST for the current node
     */
    public BST getXAxisBST(){
    	return this.XAxis;
    }
    
    /**
     * 
     * @returns the Y axis BST for the current node
     */
    public BST getYAxisBST(){
    	return this.YAxis;
    }
    
    /**
     * 
     * @returns the vale of the x axis value for the current node
     */
    public double getXAxisValue(){
    	return this.x_axis;
    }
    
    /**
     * 
     * @returns the vale of the y axis value for the current node
     */
    public double getYAxisValue(){
    	return this.y_axis;
    }

    /**
     * 
     * @returns number of rectangles contained by the current node
     */
  public int getNumOfRectangles(){
	  return this.numOfRect;
  }


}
