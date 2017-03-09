package solutions;

import java.util.*;

/**
 * 
 * @author Michael Kalu
 * @class CMSC 420
 * @project Project 1
 */
public class BinaryPatriciaTrie  {
	public Node root;
	public static boolean INSERTEDFLAG = false;
	public static boolean DELETEDFLAG = false;
	
	/**
	 * BinaryPatriciaTrie constructor
	 */
	public BinaryPatriciaTrie() {
		this.root = new Node("",1,null,null);
	}
	
	/**
	 * Check both left and right child to see if tree is empty
	 * @return
	 */
	public boolean isEmpty(){
		// returns true if empty otherwise false
		if(root.left == null && root.right == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * I create a static variable to handle my recursive method
	 * Its initialized to false at first. 
	 * @param s
	 * @return
	 */
	public boolean insert(String s) {
		INSERTEDFLAG = false;
		// if the key is found the we just return false otherwise to call the recursive method.
		if (!search(s)) {
			aux(null,root,s);
		}
		return INSERTEDFLAG;
	}
	
	public boolean aux(Node dad, Node start, String s) {
		// ROOT CASE: if we are at the root
				if (start.key.equals("") && start.index == 1) {		
					switch(s.charAt(0)){
					case '0':
						if (start.left == null) {
							// left
							start.left = new Node(s,s.length()+1,null,null);
							INSERTEDFLAG = true;
						}else {
							INSERTEDFLAG = aux(start,start.left, s);
						}
						break;
					case '1':
						if (start.right == null) {
							// left
							start.right = new Node(s,s.length()+1,null,null);
							INSERTEDFLAG = true;
						}else {
							INSERTEDFLAG = aux(start,start.right, s);
						}
						break;
					default:
						break;
					}
				}
				
				if (INSERTEDFLAG == true) return INSERTEDFLAG;

				// NODE CASE: if key exist
			if (!start.key.equals(""))	{
				int index = findDiff(s,start.key);
			 if (s.length() <= start.key.length()) {

				// if the key is smaller than the node key
				if (s.length() == index) {
					if(s.charAt(dad.index - 1) == '0') {
						dad.left = new Node(s,s.length()+1,null,null);
						if (start.key.charAt(dad.left.index - 1) == '0') {
							dad.left.left = start;
							return true;
						}else {
							dad.left.right = start;
							return true;
						}
					} else {
						dad.right = new Node(s,s.length()+1,null,null);
						if (start.key.charAt(dad.right.index - 1) == '0') {
							dad.right.left = start;
							return true;
						}else {
							dad.right.right = start;
							return true;
						}
					}
				} else {
					// key is smaller than current node but they are not the same key
					if (s.charAt(dad.index - 1) == '0') {
						dad.left = new Node("",index+1,null,null);
						if (start.key.charAt(dad.left.index - 1) == '0'){
							dad.left.left = start;
							dad.left.right = new Node(s,s.length()+1,null,null);
							return true;
						} else {
							dad.left.right = start;
							dad.left.left = new Node(s,s.length()+1,null,null);
							return true;
						}
					} else {
						dad.right = new Node("",index+1,null,null);
						if (start.key.charAt(dad.right.index - 1) == '0'){
							dad.right.left = start;
							dad.right.right = new Node(s,s.length()+1,null,null);
							return true;
						}else {
							dad.right.right = start;
							dad.right.left = new Node(s,s.length()+1,null,null);
							return true;
						}
					}
				}
			}else if (s.length() > start.key.length()){
				// if the key we want to enter is longer than current key and they are equal
				if (index == start.key.length()) {
					if (s.charAt(index) == '0') {
						if (start.left != null) {
							return aux(start,start.left,s);
						} else {
							start.left = new Node(s,s.length()+1,null,null);
							return true;
						}
					}else {
						if (start.right != null) {
							return aux(start,start.right,s);
						} else {
							start.right = new Node(s,s.length()+1,null,null);
							return true;
						}
					}
				}else {
					if (s.charAt(dad.index - 1) == '0'){
						 dad.left = new Node("", index+1, null, null);
	                        if (start.key.charAt(dad.left.index-1) == '0') {
	                            dad.left.left = start;
	                            dad.left.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.left.right = start;
	                            dad.left.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    } else {
	                        dad.right = new Node("", index+1, null, null);
	                        if (start.key.charAt(dad.right.index-1) == '0') {
	                            dad.right.left = start;
	                            dad.right.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.right.right = start;
	                            dad.right.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    }
					}
				}
			
			}else {
				// PREFIX CASE: key doesn't exist
				Node child = getChild(start);
				String pf = child.key.substring(0,start.index-1);
				// check if prefix matches
				if (pf.equals(s)){
					start.key = s;
					return true;
				}
				int index = findDiff(pf,s);
				// check inserted key length
				if (s.length() > pf.length()) {
					if (pf.length() == index) {
						if (s.charAt(index) == '0') {
							return aux(start,start.left, s);
						}else {
							return aux(start,start.right, s);
						}
					} else {
						// if inserted key is longer, and it doesnt equal current key
						if (s.charAt(dad.index - 1) == '0') {
							dad.left = new Node("", index+1, null, null);
	                        if (pf.charAt(dad.left.index-1) == '0') {
	                            dad.left.left = start;
	                            dad.left.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.left.right = start;
	                            dad.left.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    } else {
	                        dad.right = new Node("", index+1, null, null);
	                        if (pf.charAt(dad.right.index-1) == '0') {
	                            dad.right.left = start;
	                            dad.right.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.right.right = start;
	                            dad.right.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    }
					}
				}else {
					// if inserted key is smaller than the prefix and is the same
					if (index == s.length()) {
						if(s.charAt(dad.index - 1) == '0') {
							 dad.left = new Node(s,s.length()+1,null,null);
		                        if (pf.charAt(dad.left.index-1) == '0') {
		                            dad.left.left = start;
		                            return true;
		                        } else {
		                            dad.left.right = start;
		                            return true;
		                        }
		                    } else {
		                        dad.right = new Node(s,s.length()+1,null,null);
		                        if (pf.charAt(dad.right.index-1) == '0') {
		                            dad.right.left = start;
		                            return true;
		                        } else {
		                            dad.right.right = start;
		                            return true;
		                        }
		                    }
					}else {
						// if inserted key is smaller and not the same
						if (s.charAt(dad.index-1) == '0') {
	                        dad.left = new Node("", index+1, null, null);
	                        if (pf.charAt(dad.left.index-1) == '0') {
	                            dad.left.left = start;
	                            dad.left.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.left.right = start;
	                            dad.left.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    } else {
	                        dad.right = new Node("", index+1, null, null);
	                        if (pf.charAt(dad.right.index-1) == '0') {
	                            dad.right.left = start;
	                            dad.right.right = new Node(s,s.length()+1,null,null);
	                            return true;
	                        } else {
	                            dad.right.right = start;
	                            dad.right.left = new Node(s,s.length()+1,null,null);
	                            return true;
	                        }
	                    }
					}
				}
			}
			return false;		
		}
	
	/**
	 * Finds the index where two strings differ
	 * @param a
	 * @param b
	 * @return
	 */
	public int findDiff(String x, String y)  {
		int index = 0;
        try {
            while (x.charAt(index) == y.charAt(index)) {
                index++;
            }
        }
        catch (StringIndexOutOfBoundsException e) {
        }
        return index;
	}

	/**
	 *  Returns the child of a prefix
	 * @param start
	 * @return
	 */
	public Node getChild(Node start) {
		if (!start.key.equals("")) {
            return start;
        } else {
            if (start.right != null) {
                return getChild(start.right);
            } else {
                return getChild(start.left);
            }
        }
	}

	/**
	 *  I first check if the node we want to delete is present in the tree if not then we return false.
	 *  otherwise we make the recursive call
	 * @param s
	 * @return
	 */
	public boolean delete(String s) {
		DELETEDFLAG = false;
		if (!search(s)) {
			return false;
		}
		delete(s,root,root,"root");
		return DELETEDFLAG;
	}
	
	
	/**
	 * 
	 * @param s
	 * @param start
	 * @param parent
	 * @param direction
	 * @return
	 */
	public boolean delete(String s, Node start, Node parent,String direction) {
		boolean deletedR = false, deletedL = false; // I create two variable that are equal to the recursive method
		if (start == null) return false; // if first check if the current node we are on is null
		if (DELETEDFLAG == true) return DELETEDFLAG;
		
		// ROOT CASE:
		if (start.key.equals("") && direction.equals("root")) {
			if (start.left != null && start.key.equals(s)) {
				parent.left = null;
				DELETEDFLAG = true;
			}else if (start.left != null && start.key.equals(s)) {
				parent.right = null;
				DELETEDFLAG = true;
			}
		} else if (start.key.equals("") && !direction.equals("root")) {
			// PREFIX CASE: 
			if (start.left != null && start.left.equals(s)){
				if (direction.equals("left")) parent.left = start.right;
				else parent.right = start.right;
				DELETEDFLAG = true;
			}else if (start.right != null && start.right.equals(s)) {
				if (direction.equals("left")) parent.left = start.left;
				else parent.right = start.left;
				DELETEDFLAG = true;
			}
		} else if (start.key.equals(s)) {
			// ROOT WITH ONE CHILD:
			if(start.left != null && start.right == null) {
				if (direction.equals("left")) parent.left = start.left;
				else parent.right = start.left;
				DELETEDFLAG = true;
			}else if (start.right != null && start.left == null) {
				if (direction.equals("left")) parent.left = start.right;
				else parent.right = start.right;
				DELETEDFLAG = true;
			}else if (start.left != null && start.right != null) {
				// ROOT WITH TWO CHILDREN:
				start.key = "";
				DELETEDFLAG = true;
			}else if (start.left == null && start.right == null) {
				// ROOT WITH NO CHILDREN
				if (direction.equals("left")) parent.left = null;
				else parent.right = null;
				DELETEDFLAG = true;
			}
		}
		
		// we make a recursive call on both sides of the tre
		deletedL = delete(s,start.left,start,"left");
		deletedR = delete(s,start.right,start,"right");
		
		// we return true if one of the sided holds true
		return deletedL || deletedR;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	 public boolean search(String s) {
		 if (search(s,root) == 0) {
			return false;
		 }
		 return true;
	 }
	 
	 /**
	  * I check the entire tree, and add 1 if the string is in the tree, and dont add anything if the string is 
	  * not in the tree
	  * @param s
	  * @param start
	  * @return
	  */
	 public int search(String s, Node start){
		 if (start == null) return(0); 
		  else { 
			  if (start.key.equals(s)) return(search(s,start.left) + 1 + search(s,start.right)); 
			  else {
				  return(search(s,start.left)  + search(s,start.right));
			  }
		  }  
	 }
	/**
	 * Calls an aux method that does the heavy lifting
	 * @return
	 */
	public int getSize() {
		return (getSize(root));
	}
	
	/**
	 * if the node isn't null and has a key, I make a recursive call of both sides of the tree and adding one to it, else
	 * if its a prefix I still make the recursive call but I don't add one
	 * @param start
	 * @return
	 */
	public int getSize(Node start) {	
		  if (start == null) return(0); 
		  else { 
			  if (!start.key.equals("")) return(getSize(start.left) + 1 + getSize(start.right)); 
			  else {
				  return(getSize(start.left) + getSize(start.right));
			  }
		  }  
	}
	
	/**
	 * Returns the longest key
	 * @return
	 */
	public String getLongest() {
		// calls aux method 
		ArrayList<String> array = new ArrayList<String>();
		return getLongest(root,array);
	}
	
	/**
	 * Aux method that does the heavy lifting for getLongest method
	 * @param start
	 * @param longest
	 * @return
	 */
	public String getLongest(Node start,  ArrayList<String> array) {
		getOrder(array,start); // stores all the nodes keys in an array
		String longest = "";
		
		for (String str : array) {// iterate through array to find the longest key
			if (str.length() > longest.length()){
				longest = str;
			}else if (str.length() == longest.length()) { // if there are 2 keys same length, I find the difference 
				int i = findDiff(str,longest);            // and return the key with a '1' as the difference
				if (checkForException(str,i) == '1') {
					longest = str;
				}
			}
		}
		if (longest.equals("")) {
			return null;
		}
		return longest;
	}
	
	/**
	 * Returns in-order keys 
	 * @return
	 */
	public Iterator<String> inOrderTraversal() {
		ArrayList<String> array = new ArrayList<String>();
		if (root != null) {
			// if root is not null, call aux method
			getOrder(array,root);
		}
		
		Iterator<String> it = array.iterator();
		return it;	
	}
	
	/**
	 * Check entire tree and add all of the keys from left to right
	 * left - root - right
	 * @param arr
	 * @param start
	 * @return
	 */
	public void getOrder(ArrayList<String> arr, Node start) {	
			  // if node isn't a prefix add the key otherwise keep going 
			  if (start.left != null)getOrder(arr,start.left);
			  if (!start.key.equals("")) {
				  arr.add(start.key);
			  }
			  if (start.right != null)getOrder(arr,start.right); 
	}
	
	public char checkForException(String s, int index) {
		try {
			s.charAt(index);
		}catch(StringIndexOutOfBoundsException e){
			return s.charAt(index - 1);
		}
		return s.charAt(index);
	}
	
}

