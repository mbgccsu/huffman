
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

class Tree {
    public final int data;
    public final Tree leftChild;
    public final Tree rightChild;
    public final Character ch;

    //constructor
    private Tree(int d, Character c,  Tree left, Tree right) {
        data = d;
        leftChild = left;
        rightChild = right;
        ch = c;
    }

    static Tree leaf(char c, int f) {
        return new Tree(f, c, null, null);
    }

    static Tree combine(Tree a, Tree b) {
        return new Tree(a.data + b.data, null, a, b);
    }

    public boolean isLeaf() {return leftChild == null && rightChild == null;}
    

}

public class Huffman {

    public Map<Character, String> compute_coding(Map<Character, Integer> character_counts) {
    
    //TODO!!!
    int size = character_counts.size();
    Queue<Tree> priority = new PriorityQueue<Tree>(size, (left, right) -> Integer.compare(left.data, right.data));;
    
    for (char ch : character_counts.keySet()) {
    	priority.add(Tree.leaf(ch, character_counts.get(ch)));
    }
    
    Tree root = null;
    
    while (priority.size() > 1) {
    Tree first = priority.poll();
    Tree second = priority.poll();   
    Tree combine = Tree.combine(first, second);
    root = combine;
    priority.add(combine);
    }
    
    //String input = "";
    
    Map<Character, String> result = new HashMap<Character, String>(); 
    //code(result, root, input);  
    Tree left = root.leftChild;
    Tree right = root.rightChild;
    
    while (!left.isLeaf() && !right.isLeaf()) {
    	if (left.leftChild != null) {
    		result.put(left.ch, "0");
    	    left = left.leftChild;
    	}
    	if (right.rightChild != null) {
    		result.put(right.ch, "1");
    	    right = right.rightChild;
    	}
    }
    return result;
    }
    
	public void code(Map<Character, String> result, Tree root, String code) {
	    if (root.isLeaf()) {
	      result.put(root.ch, code);
	      return;
	    }
	    code(result, root.leftChild, code + "0");
	    code(result, root.rightChild, code + "1");
	  }
    

    public static void main(String[] args) {
        Map<Character, Integer> freqs = new HashMap<Character, Integer>(){{
            put('a', 15);
            put('e', 7);
            put('i', 30);
            put('o', 120);
            put('u', 11);
        }};
        Map<Character, String> codes = new Huffman().compute_coding(freqs);
        for (Character ch : freqs.keySet()) {
            String code_word = codes.get(ch);
            System.out.println(ch + ": " + code_word);
        }
    }
}
