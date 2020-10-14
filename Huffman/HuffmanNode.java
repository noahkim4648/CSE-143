         // Jin Byoun
// CSE 143
// TA: Karanbir Singh
//
// constructs a HuffmanNode object which is used to decode/encode files 

public class HuffmanNode implements Comparable<HuffmanNode> {
   
   public final int frequency;
   public final int character;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // Constructs a new HuffmanNode with a given frequency
   public HuffmanNode(int frequency) {
      this(frequency, 0, null, null);
   }
    
   // Constructs a new HuffmanNode with a given frequency and character
   public HuffmanNode(int frequency, int character) {
      this.frequency = frequency;
      this.character = character;
   }
    
   // Constructs a new HuffmanNode with a given frequency, character, 
   // with given left and right HuffmanNode
   public HuffmanNode(int frequency, int character, HuffmanNode left,
                                                 HuffmanNode right) {
      this.frequency = frequency;
      this.character = character;
      this.left = left;
      this.right = right;
   }
    
   // Compares the frequencies between two HuffmanNodes
   public int compareTo(HuffmanNode other) {
      return ((Integer)this.frequency).compareTo(other.frequency);
   }
}