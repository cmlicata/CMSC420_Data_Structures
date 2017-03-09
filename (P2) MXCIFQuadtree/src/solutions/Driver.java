package solutions;

public class Driver {
	public static void main(String[] args) {

		MXCIFQuadTree quad = new MXCIFQuadTree(0, 0, 128, 128);
		quad.insert(10, 10, 10, 100, "A");
		quad.insert(12, 100, 70, 20, "E");

		quad.insert(70, 8, 40, 16, "B");
		quad.insert(100, 15, 16, 45, "C");
		quad.insert(66, 42, 54, 18, "D");
		
		quad.insert(68, 82, 52, 10, "G");
		quad.insert(100, 68, 20, 10, "F");
		
		quad.delete(68, 82, 52, 10); // G
		quad.delete(100, 68, 20, 10); // F
		
		if (quad.getHeight() == 2){
			System.out.println("height Passed");
		}else {
			System.out.println("height failed");
		}
		if (quad.getSize() == 5){
			System.out.println("size passed");
		}else {
			System.out.println("size failed");
		}
		
		quad.levelOrderTraversalQTInOrderBST();
		
		
//		MXCIFQuadTree quad = new MXCIFQuadTree(10, 10, 256, 256);
//		quad.insert(170.0, 75.0, 30.0, 100.0, "AtRoot");
//		quad.insert(170.0, 75.0, 30.0, 100.0, "AtRoot");

//		quad.insert(70.0, 150.0, 100.0, 50.0, "AtRoot");
//		
//		
//		quad.insert(70.0, 75.0, 100.0, 90.0, "AtRoot");
//		quad.insert(15.0, 20.0, 66.0, 77.25, "NW1");
//		quad.insert(15.0,150.0,10.0,10.0,"SW1");
//		quad.insert(170.0, 25.0, 70.0, 55.25, "NE1");
		
//		quad.delete(70.0, 75.0, 100.0, 90.0);
//		quad.delete(15.0, 20.0, 66.0, 77.25);
//		quad.delete(15.0,150.0,10.0,10.0);
//		quad.delete(170.0, 25.0, 70.0, 55.25);

		
		
	}
}
