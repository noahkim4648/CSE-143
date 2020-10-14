// Jin Byoun
// CSE 143
// TA: Karanbir Singh
//
// Uses Huffman coding to compress text data to make a file occupy less space

import java.util.*;
import java.io.*;

public class HuffmanCode {
   
   private HuffmanNode root;
   
   // Constructs a HuffmanCode object using an algorithm for making code 
   // from an array of frequencies. Uses a priority queue to 
   // build the HuffmanCode
   public HuffmanCode(int[] frequencies) {
      Queue<HuffmanNode> priority = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] >= 1) {
            priority.add(new HuffmanNode(frequencies[i], i));
         }
      }
      while (priority.size() > 1) {
         HuffmanNode first = priority.remove();
         HuffmanNode second = priority.remove();
         HuffmanNode sum = new HuffmanNode(first.frequency + second.frequency, 
         0, first, second);
         priority.add(sum);
      }
      root = priority.remove();
   }
   
   // input is not null
   // input is attached to a legal existing file in standard format
   // Initializes a new HuffmanCode object by reading from a
   // previously made code.
   public HuffmanCode(Scanner input) {
      this.root = new HuffmanNode(0, 0);
      while (input.hasNextInt()) {
         int letterChar = input.nextInt();
         String huffmanCode = "" + input.next();
         this.root = createTree(this.root, huffmanCode, letterChar);
      }
   }
   
   // Initializes a new HuffmanCode with a root, letter, and a code
   private HuffmanNode createTree(HuffmanNode newRoot, String code,
                                                   int character) {       
      if (code.length() != 0) {                      
         if (code.length() == 1) {
            if (code.charAt(0) == '0') {
               newRoot.left = createTree(new HuffmanNode(0, character),
                                         code.substring(1), character);
            } else {
               newRoot.right = createTree(new HuffmanNode(0, character),
                                          code.substring(1), character);
            }
         } else {
            if (code.charAt(0) == '0') {
               if (newRoot.left == null) {
                  newRoot.left = createTree(new HuffmanNode(0, character), 
                                            code.substring(1), character);
               } else {
                  newRoot.left = createTree(newRoot.left,
                           code.substring(1), character);
               }
            } else {
               if (newRoot.right == null) {
                  newRoot.right = createTree(new HuffmanNode(0, character),
                                             code.substring(1), character);
               } else {
                  newRoot.right = createTree(newRoot.right, 
                             code.substring(1), character);
               }
            }    
         }
      }
      return newRoot;    
   }
   
   // Stores the current tree to an output file in standard format
   public void save(PrintStream output) {
      String code = "";
      save(output, this.root, code);
   }
   
   // Writes the current tree to the given output in standard format
   private void save(PrintStream output, HuffmanNode overallRoot,
                                                   String code) {
      if (overallRoot.left == null && overallRoot.right == null) {
            output.println(overallRoot.character);
            output.println(code);  
      } else {
            save(output, overallRoot.left, code + 0);
            save(output, overallRoot.right, code + 1);
      } 
   }
      
   // Reads individual bits from the input  and writes the corresponding 
   // characters to the output. Stops when a input is empty. input must 
   // be the characters from this HuffmanCode's code
   public void translate(BitInputStream input, PrintStream output) {
      while (input.hasNextBit()) {
         translate(input, output, this.root);    
      } 
   }
   
   // Finds a certain character in the tree by following the pattern from input
   private void translate(BitInputStream input, PrintStream output,
                                            HuffmanNode totalRoot) {
      if (totalRoot.left == null && totalRoot.right == null) {
         output.write(totalRoot.character);
      } else {
         if (input.nextBit() == 0) {
            translate(input, output, totalRoot.left);
         } else {
            translate(input, output, totalRoot.right);
         }
      }
   }

}