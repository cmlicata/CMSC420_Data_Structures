package solutions;

/**
 * 
 * @author Michael Kalu
 *
 */
public class Node {
	
	public String key;
	public int index;
	public Node left;
	public Node right;
	
	/**
	 * 
	 * @param key
	 * @param index
	 * @param left
	 * @param right
	 */
	public Node(String key, int index, Node left, Node right) {
		this.key = key;
		this.index = index;
		this.left = left;
		this.right = right;
	}

	
}
