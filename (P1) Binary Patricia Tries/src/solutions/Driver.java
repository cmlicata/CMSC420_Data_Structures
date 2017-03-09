package solutions;

public class Driver {
	public static void main(String[] args) {
		BinaryPatriciaTrie bpt = new BinaryPatriciaTrie();
		bpt.insert("00001");
		bpt.insert("010010");
		bpt.insert("010111");
		bpt.insert("011011");
		bpt.insert("011101");
		bpt.insert("01111");
		bpt.insert("10010");
		bpt.insert("10110");
		bpt.insert("11001");
		
		bpt.inOrderTraversal();
		
		if (bpt.delete("00001")) {
			System.out.println("1");
		}
		if (bpt.delete("010010")){
			System.out.println("2");
		}
		if (bpt.delete("010111")){
			System.out.println("3");
		}
		if (bpt.delete("011011")){
			System.out.println("4");
		}
		if (bpt.delete("011101")){
			System.out.println("5");
		}
		if (bpt.delete("01111")){
			System.out.println("6");
		}
		if (bpt.delete("10010")){
			System.out.println("7");
		}
		if (bpt.delete("10110")){
			System.out.println("8");
		}
		if (bpt.delete("11001")){
			System.out.println("9");
		}
		
		bpt.inOrderTraversal();

		
		
		

	}
}
