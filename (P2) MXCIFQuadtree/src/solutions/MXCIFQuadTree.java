package solutions;

import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.Stack;


/**
 * 
 * @author Michael Kalu
 *
 */
public class MXCIFQuadTree {

    public Node root;
    public static boolean INSERTFLAG, DELETEFLAG;

    /**
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public MXCIFQuadTree(double x, double y, double w, double h) {
        this.root = new Node(x, y, w, h);
        INSERTFLAG = false;
        DELETEFLAG = false;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     * @param rname
     * @return
     */
    public boolean insert(double x, double y, double w, double h, String rname) {
        Rectangle insertRec = new Rectangle(x,y,w,h,rname);
        if (checkRecBounds(insertRec)) return aux_insert(root, insertRec);
        
        return INSERTFLAG;
    }
    
    /*
     * ---------------------------- INSERT HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @param insertRec
     * @return
     */
    public boolean checkRecBounds(Rectangle insertRec){
        if ((insertRec.rec.x >= root.quad.x) && (insertRec.rec.x+insertRec.rec.width) <= (root.quad.x+root.quad.width)){
            if ((insertRec.rec.y >= root.quad.y) && (insertRec.rec.y+insertRec.rec.height) <= (root.quad.y+root.quad.height)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param node
     * @param insertRec
     * @return
     */
    public boolean aux_insert(Node node, Rectangle insertRec) {
        if (crossXAxis(node, insertRec)) {
            if (node.XAxis.insert(insertRec.rec.x, insertRec.rec.x+insertRec.rec.width, insertRec)) {     
                setNumRectangles(node,"+");
                INSERTFLAG = true;
                return INSERTFLAG;
            }
        } else if (crossYAxis(node, insertRec)) {
            if (node.YAxis.insert(insertRec.rec.y, insertRec.rec.y+insertRec.rec.height, insertRec)) {     
                setNumRectangles(node,"+");
                INSERTFLAG = true;
                return INSERTFLAG;
            }
        } else {

            if (insertRec.rec.y + insertRec.rec.height <= node.x_axis) { 
            	if (insertRec.rec.x >= node.y_axis) {  
            		if (node.NE == null) {
                        node.NE = new Node(node.y_axis, node.quad.y, node.quad.width / 2.0, node.quad.height / 2.0);
                        node.NE.father = node;
                    }
                    return aux_insert(node.NE, insertRec);
                } else {
                    if (node.NW == null) {
                        node.NW = new Node(node.quad.x, node.quad.y, node.quad.width / 2.0, node.quad.height / 2.0);
                        node.NW.father = node;
                    }
                    return aux_insert(node.NW, insertRec);
                }
            } else {
                if (insertRec.rec.x >= node.y_axis) {
                    if (node.SE == null) {
                        node.SE = new Node(node.y_axis, node.x_axis, node.quad.width / 2.0, node.quad.height / 2.0);
                        node.SE.father = node;
                    }
                    return aux_insert(node.SE, insertRec);
                } else {
                    if (node.SW == null) {
                        node.SW = new Node(node.quad.x, node.x_axis, node.quad.width / 2.0, node.quad.height / 2.0);
                        node.SW.father = node;
                    }
                    return aux_insert(node.SW, insertRec);
                }
            }
        }
        return INSERTFLAG;
    }
    
    /**
     * 
     * @param node
     * @param insertRec
     * @return
     */
    public boolean crossXAxis(Node node, Rectangle insertRec){
        if ((insertRec.rec.y+insertRec.rec.height) > node.x_axis && insertRec.rec.y < node.x_axis) return true;

        return false;
    }
    
    /**
     * 
     * @param node
     * @param insertRec
     * @return
     */
    public boolean crossYAxis(Node node, Rectangle insertRec){
        if (insertRec.rec.x < node.y_axis && (insertRec.rec.x+insertRec.rec.width) > node.y_axis) return true;
       
        return false;
    }
    
    /*
     * ---------------------------- /INSERT HELPER METHODS -------------------------------------
     */

    /**
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public boolean delete(double x , double y , double w, double h) {
        Rectangle deleteRec = new Rectangle(x,y,w,h,"");
        if (checkRecBounds(deleteRec)) return aux_delete(root, deleteRec);
        
        return DELETEFLAG;
    }
    
    /*
     * ---------------------------- DELETE HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @param node
     * @param deleteRec
     * @return
     */
    public boolean aux_delete(Node node, Rectangle deleteRec) {
        if (crossXAxis(node, deleteRec)) {
            if (node.XAxis.delete(deleteRec.rec.x, deleteRec.rec.x+deleteRec.rec.width, deleteRec)) {
                setNumRectangles(node,"-");
                deleteHelper(node.father);
                DELETEFLAG = true;
                return DELETEFLAG;
            }
        } else if (crossYAxis(node, deleteRec)) {
            if (node.YAxis.delete(deleteRec.rec.y, deleteRec.rec.y+deleteRec.rec.height, deleteRec)) {
                setNumRectangles(node,"-");
                deleteHelper(node.father);
                DELETEFLAG = true;
                return DELETEFLAG;
            }
        } else {
            if (deleteRec.rec.y + deleteRec.rec.height <= node.x_axis) {
            	
                if (deleteRec.rec.x >= node.y_axis) if (node.NE != null) return aux_delete(node.NE, deleteRec);
                if (node.NW != null)  return aux_delete(node.NW, deleteRec);
               
            } else {
                if (deleteRec.rec.x >= node.y_axis) if (node.SE != null) return aux_delete(node.SE, deleteRec);
                if (node.SW != null) return aux_delete(node.SW, deleteRec);
                   
            }
            return DELETEFLAG;
        }
        return DELETEFLAG;
    }
    
    /**
     * 
     * @param node
     */
    public void deleteHelper(Node node) {
        if (node == null) return;
        
            if (node.NW != null) if (node.NW.numOfRect == 0) node.NW = null;           
            if (node.NE != null) if (node.NE.numOfRect == 0) node.NE = null;          
            if (node.SW != null) if (node.SW.numOfRect == 0) node.SW = null;            
            if (node.SE != null) if (node.SE.numOfRect == 0) node.SE = null;
               
            deleteHelper(node.father);
    }
    
    /**
     * 
     * @param node
     */
    public void setNumRectangles(Node node, String sign) {
        if (node == null) return;
        
        if (sign.equals("-")) {
            node.numOfRect--;
            setNumRectangles(node.father, sign);
    	} else {
    		node.numOfRect++;
            setNumRectangles(node.father,sign);
    	}
    }


    /*
     * ---------------------------- /DELETE HELPER METHODS -------------------------------------
     */

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
    	return getSize() == 0;
    }

    /**
     * 
     * @return
     */
    public int getSize() {
        return this.root.numOfRect;
    }

    /**
     * 
     * @return
     */
    public int getHeight() {
        return (this.root != null) ? aux_getHeight(root) : 1;
    }
    
    /*
     * ---------------------------- HEIGHT HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @param node
     * @return
     */
    public int aux_getHeight(Node node) {
        if (node == null) return 0;

        int NW = aux_getHeight(node.NW);      
        int NE = aux_getHeight(node.NE);      
        int SW = aux_getHeight(node.SW);      
        int SE = aux_getHeight(node.SE);

            
     return 1 + Math.max(Math.max(NW, NE), Math.max(SE, SW));
     }

    /*
     * ---------------------------- /HEIGHT HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public ArrayList<String> intersect(double x, double y){
        ArrayList<String> recList = new ArrayList<String>();

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        
        while(!stack.isEmpty()){
            Node node = stack.pop();
            while(node != null) {
                recList.addAll(getContainedRecs(node, x, y));
                if(node.NE != null && x > node.y_axis && y < node.x_axis) stack.push(node.NE);                
                if(node.SW != null && x < node.y_axis && y > node.x_axis) stack.push(node.SW);               
                if(node.SE != null && x > node.y_axis && y > node.x_axis ) stack.push(node.SE);
                
                node = node.NW;
            }
        }
        return recList;
    }

    /**
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public ArrayList<String> intersect(double x, double y, double w, double h) {
        Rectangle rec = new Rectangle(x, y, w, h,"");
        ArrayList<String> recList = new ArrayList<String>();

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        
        while(!stack.isEmpty()){
            Node node = stack.pop();
            while(node != null) {
                recList.addAll(getIntersectedRecs(node, rec));
                if(node.NE != null)stack.push(node.NE);              
                if(node.SW != null)stack.push(node.SW);             
                if(node.SE != null)stack.push(node.SE);
                
                node = node.NW;
            }
        }
        return recList;
    }
    
    /*
     * ---------------------------- INTERSECT HELPER METHODS -------------------------------------
     */
    
    /**
     * 
     * @param node
     * @param rec
     * @return
     */
    public ArrayList<String> getIntersectedRecs(Node node, Rectangle rec) {
        ArrayList<String> rectsThatIntersect = new ArrayList<String>();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
        
        rectList.addAll(node.XAxis.getAllRectangles());
        rectList.addAll(node.YAxis.getAllRectangles());

        for (Rectangle rectangle : rectList) {
            if (aux_intersect(rectangle, rec)) rectsThatIntersect.add(rectangle.getRecName());
        }

        return rectsThatIntersect;
    }
    
    /**
     * 
     * @param node
     * @param x
     * @param y
     * @return
     */
    public ArrayList<String> getContainedRecs(Node node, double x, double y) {
        ArrayList<String> containedRecs = new ArrayList<String>();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
        
        rectList.addAll(node.XAxis.getAllRectangles());
        rectList.addAll(node.YAxis.getAllRectangles());

        for (Rectangle rec : rectList) {
            if (inside(rec.rec, x, y)) containedRecs.add(rec.getRecName());
        }
        return containedRecs;
    }
    
    /**
     * 
     * @param one
     * @param two
     * @return
     */
    public boolean aux_intersect(Rectangle one, Rectangle two) {
        if (one.rec.y+one.rec.height <= two.rec.y || two.rec.y+two.rec.height <= one.rec.y) return false;   
        if (one.rec.x+one.rec.width <= two.rec.x || two.rec.x+two.rec.width <= one.rec.x) return false;
        
        return true;
    }

    
    
    /**
     * 
     * @param rect
     * @param x
     * @param y
     * @return
     */
    public boolean inside(Rectangle2D.Double rect, double x, double y) {
        if (rect.x < x && (rect.x+rect.width) > x) if (rect.y < y && (rect.y+rect.height) > y) return true;

        return false;
    }
    
    /*
     * ---------------------------- /INTERSECT HELPER METHODS -------------------------------------
     */

    /**
     * 
     * @return
     */
    public Iterator<String> levelOrderTraversalQTInOrderBST() {
        Queue<Node> ll = new LinkedList<Node>();
        List<String> list = new ArrayList<String>();

        ll.add(root);
        while(!ll.isEmpty()){
            Node node = ll.remove();
            if (node != null) {
                for (Rectangle rectWithName : node.XAxis.getAllRectangles()) {
                	list.add(rectWithName.getRecName());
                }
                for (Rectangle rectWithName : node.YAxis.getAllRectangles()) {
                	list.add(rectWithName.getRecName());
                }
                if(node.NW != null) ll.add(node.NW);
                if(node.NE != null) ll.add(node.NE);
                if(node.SW != null) ll.add(node.SW);
                if(node.SE != null) ll.add(node.SE);
            }
        }
        Iterator<String> it = list.iterator();
        return it;
    }

}



