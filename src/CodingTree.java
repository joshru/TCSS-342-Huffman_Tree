import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;


/**
 * The Class CodingTree.
 * Forms a huffman tree of individual characters from a string
 */
public class CodingTree {
	
	/** The root node. */
	public Node root;
	
	/** The priority queue. */
	public PriorityQueue<Node> myQ;
	
	/** The map of characters and their frequency. */
	public Map<Character, Integer> frequencyMap;
	
	/** The map of characters and their binary code. */
	public Map<Character, String> codes;
	
	/**
	 * Instantiates a new coding tree.
	 * 
	 * @param theMessage the string to encode
	 * O(n) - makes 4 calls to O(n) methods but this can be considered a constant and ignored. 
	 */
	public CodingTree(String theMessage) {
		Comparator<Node> comp = new Node();
		
		root = null;
		myQ = new PriorityQueue<Node>(comp);
		frequencyMap = new TreeMap<Character, Integer>();
		codes = new TreeMap<Character, String>();
		
		createFrequencyMap(theMessage);
		populatePQ();
		buildTree();
		recursiveEncode(root, "");
		

	}
	
	/**
	 * Creates the frequency map.
	 *
	 * @param theStr the string to encode
	 * O(n) - loops through every element in the string once.
	 */
	private void createFrequencyMap(String theStr) {
		
		for (char key : theStr.toCharArray()) {
			
			if(!frequencyMap.containsKey(key)) {
				frequencyMap.put(key, 1);
				
			} else if (frequencyMap.containsKey(key)){
				int count = frequencyMap.get(key);
				frequencyMap.put(key, ++count);
			}
		}
		
	}
	
	/**
	 * Populates the priority queue.
	 * O(n) - loops through every element in the frequency map
	 */
	private void populatePQ() {
		for (char ch : frequencyMap.keySet()) {
			int freq = frequencyMap.get(ch);
			Node temp = new Node(ch, freq);
			myQ.offer(temp);
		}
	}
	
	/**
	 * Builds the Huffman tree by merging the front two nodes in the PQ until there is only one left.
	 * O(n) - combines every element in the priority queue
	 */
	private void buildTree() {
		while (myQ.size() > 1) {
			Node left = myQ.poll();
			Node right = myQ.poll();
			myQ.offer(new Node(left, right));
			
		}
		root = myQ.poll();
	}
	
	/**
	 * Recursive encode.
	 *
	 * @param root the root
	 * @param code the code
	 * O(n) - Traverses through every node exactly 1 time, cant be O(log(n)) because the tree will not be balanced
	 */
	private void recursiveEncode(Node root, String code) {
		if (root.left != null) {
			recursiveEncode(root.left, code + '0');
		}
		if (root.right != null) {
			recursiveEncode(root.right, code + '1');
		}
		if (root.isLeaf()) {
			codes.put(root.myChar, code);
		}
	}
	
	/**
	 * The Class Node.
	 */
	private class Node implements Comparator<Node> {
		
		/** The my char. */
		public char myChar;
		
		/** The my freq. */
		public int myFreq;
		
		/** The left. */
		public Node left;
		
		/** The right. */
		public Node right;

		/**
		 * Instantiates a new node.
		 *
		 * @param theChar the the char
		 * @param theFreq the the freq
		 * O(1)
		 */
		public Node(char theChar, int theFreq) {
			myChar = theChar;
			myFreq = theFreq;
		}
		
		/**
		 * Instantiates a new node.
		 *
		 * @param n1 the n1
		 * @param n2 the n2
		 * O(1)
		 */
		public Node(Node n1, Node n2) {
			left = n1;
			right = n2;
			myFreq = n1.myFreq + n2.myFreq;
					
		}
		
		/**
		 * Instantiates a new empty node.
		 * O(1)
		 */
		public Node() {
			//Creates a dummy node for the comparator
		}
		
		/**
		 * Checks if node is a leaf.
		 *
		 * @return true, if node is a leaf
		 * O(1)
		 */
		private boolean isLeaf() {
			return (left == null && right == null);
		}
		
		/**
		 * Returns a string containing the nodes character and frequency
		 * O(1)
		 */
		public String toString() {
			return "" + "'" + myChar + "'" + "=" + myFreq;
		}

		/**
		 * Compares the frequency of two nodes
		 * 
		 * @param n1 the first node to compare
		 * @param n2 the second node to compare
		 * O(1)
		 */
		@Override
		public int compare(Node n1, Node n2) {
			return n1.myFreq - n2.myFreq;
		}
	}

}
