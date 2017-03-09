package solutions;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * @author Michael Kalu
 * @category Project 2
 * CMSC 420
 *
 */
public class BST {

	// We start with a parent and a leftSon and rightSon child
    public BST father;
    public BST leftSon;
    public BST rightSon;

    // Min, Max, Middle vales
    public double min;
    public double max;
    public double median;

    // Number of rectangles contained in this BST
    public int numOfRects;

    public ArrayList<Rectangle> rectangles;

    /**
     * Each BST Node contains a list of rectangles to store the inserted rectangles
     * @param min
     * @param max
     * @param median
     */
    public BST(double min, double max, double median) {
    	
        this.min = min;
        this.max = max;
        this.median = median;
        
        this.father = null;
        this.leftSon = null;
        this.rightSon = null;
        
        this.numOfRects = 0;
        this.rectangles = new ArrayList<Rectangle>();
      
    }

    /**
     * 
     * @param min
     * @param max
     * @param insertRec
     * @return
     */
    public boolean insert(double min, double max, Rectangle insertRec) {
    	/*
    	 * First we check to make sure that the rectangle to insert is not already in the current node.
    	 * If so we just return false.
    	 */
        if (min < this.median && max > this.median) {
            for (Rectangle rec : this.rectangles) {
                if (rec.compareTo(insertRec)) {
                    return false;
                }
            }
            // when the median of the current rectangle is in-between the min and max, and is not in the node
            // Then we add it to the ArrayList of rectangles and increment the number of rectangles and return true.
            rectangles.add(insertRec);
            setNumRectangles(this,"+");
            return true;
        } else {
        	/*
        	 * IF the max value is less then the median we go left otherwise we go right.
        	 * 
        	 * if the left son or right son is null, the we create a new BST node as we go, using a helper method to find the new mid value
        	 */
            if (max < this.median) {       
                if (this.leftSon == null) this.leftSon = new BST(this.min, this.max, findLeftMid());
                
                this.leftSon.father = this;
                return this.leftSon.insert(min, max, insertRec);
            } else {
                if (this.rightSon == null) this.rightSon = new BST(this.min, this.max, findRightMid());
                
                this.rightSon.father = this;
                return this.rightSon.insert(min, max, insertRec);
            }
        }
    }


    /**
     * Delete is similar to insert
     * @param min
     * @param max
     * @param deleteRec
     * @return
     */
    public boolean delete(double min, double max, Rectangle deleteRec) {
    	
        if (min < this.median && max > this.median) {
            
        	for (Rectangle rec : this.rectangles) {
                if (rec.compareTo(deleteRec)) {
                    this.rectangles.remove(rec);
                    setNumRectangles(this,"-");
                    if (this.rectangles.isEmpty()) {
                        deleteHelper(this.father);
                    }
                    return true;
                }
            }
        	
            return false;
        }else if (max < median) {
            if (this.leftSon != null) return this.leftSon.delete(min, max, deleteRec);
        } else if (min > median) {
            if (this.rightSon != null) return this.rightSon.delete(min, max, deleteRec);
        } 
        return false;
    }
    
    /**
     * 
     * @param node
     */
    public void deleteHelper(BST node) {
        if (node == null) {
            return;
        } else {
            if (node.leftSon != null) {
                if (node.leftSon.rectangles.isEmpty()) {
                    node.leftSon = null;
                }
            }
            if (node.rightSon != null) {
                if (node.rightSon.rectangles.isEmpty()) {
                    node.rightSon = null;
                }
            }
            deleteHelper(node.father);
        }

    }

    /*
     *  ------------------------------------- HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @return
     */
    public ArrayList<Rectangle> getAllRectangles() {
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        Stack<BST> stack = new Stack<BST>();

        stack.push(this);
        while(!stack.isEmpty()){
            BST node = stack.pop();
            while(node != null) {
                rectangles.addAll(node.rectangles);
                if(node.rightSon != null){
                    stack.push(node.rightSon);
                }
                node = node.leftSon;
            }
        }

        return rectangles;
    }
    
    /**
     * 
     * @return
     */
    public double findLeftMid() {
        if (this.father == null) {
            return this.min+((this.median-this.min)/2);
        } else {
            BST gp = this.father;
            while(gp != null && this.median < gp.median) {
                gp = gp.father;
            }
            if (gp == null) {
                return this.min+((this.median-this.min)/2);
            } else {
                return (this.median+gp.median)/2;
            }
        }
    }

    /**
     * 
     * @return
     */
    public double findRightMid() {
        if (this.father == null) {
            return (this.median+this.max)/2;
        } else {
            BST gp = this.father;
            while(gp != null && this.median > gp.median) {
                gp = gp.father;
            }
            if (gp == null) {
                return (this.median+this.max)/2;
            } else {
                return (this.median+gp.median)/2;
            }
        }
    }
    
    /**
     * 
     * @param node
     * @param sign
     */
    public void setNumRectangles(BST node, String sign) {
        if (node == null)  return; 
        if (sign.equals("-")){
            node.numOfRects--;
            setNumRectangles(node.father, sign);
        }else {
        	node.numOfRects++;
        	setNumRectangles(node.father,sign);
        }
    }

}
