package solutions;

import java.awt.geom.Rectangle2D;

/**
 * 
 * @author Michael Kalu
 * @category Project 2
 * CMSC 420
 *
 */
public class Rectangle {
	
	public Rectangle2D.Double rec;
	public String name;
	
	/**
	 * This constructor creates a 2D rectangle, along with the name of the rectangle
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param rname
	 */
	public Rectangle(double x, double y, double w, double h, String rname){
		this.rec = new Rectangle2D.Double(x, y, w, h);
		this.name = rname;
	}	
	
	/**
	 * This method compares two Rectangles 
	 * @param 
	 * @return
	 */
	public boolean compareTo(Rectangle two){
			if(this.rec.equals(two.rec)) return true;
			
			return false;
	}
	
	/**
	 * 
	 * @returns the name of the current rectangle
	 */
	public String getRecName(){
		return this.name;
	}
	
	/**
	 * 
	 * @returns the y coord of the current rectangle
	 */
	public double y(){
		return rec.y;
	}
	
	/**
	 * 
	 * @returns the x coord of the current rectangle
	 */
	public double x(){
		return rec.x;
	}
	
	/**
	 * 
	 * @returns the max x coord of the current rectangle
	 */
	public double maxX(){
		return rec.getMaxX();
	}
	/**
	 * 
	 * @returns the max y coord of the current rectangle
	 */
	public double maxY(){
		return rec.getMaxY();
	}
	/**
	 * 
	 * @returns the height of the current rectangle
	 */
	public double getHeight(){
		return rec.getHeight();
	}
	/**
	 * 
	 * @returns the width of the current rectangle
	 */
	public double getWidth(){
		return rec.getWidth();
	}
	

}
