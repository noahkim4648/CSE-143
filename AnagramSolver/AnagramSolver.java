// Noah Kim
// 05/22/2020
// CSE 143
// TA: Ketaki Deuskar
// Assignment 6: AnagramSolver.Java

import java.io.*;
import java.util.*;
// This program will taken in the users dictionary and create anagrams of the given text the user inputs.
// to do this the program will use LetterInventorys in which the words will be laid out alphabetically. 
// for example: toast -> letterinventory would be [aostt].

public class AnagramSolver {
   
   
   private Map<String, LetterInventory> anagramSorter;   // connects words with their equivalent letter inventory.
   private List<String> dictionary;    // client inputted word bank
   
   
   // Takes in a list of words from the user and uses it as a dictionary
   // then it links the words with their corresponding letter inventory 
   // in advance to creating anagrams.
   // The list does not change in any way.
   // Assumption: it is a non-empty collection of 
   // non-empty sequences of letters and contains no duplicates
   public AnagramSolver(List<String> dictionary) {
      this.dictionary = dictionary;
      anagramSorter = new HashMap<String, LetterInventory>();
      for(String word : dictionary) {
         anagramSorter.put(word, new LetterInventory(word));   
      }
   }
   
   // takes in a text from the user and a max number and prints out 
   // all the anagrams of text that include at most the max number of words.
   // if max is 0 the number of words is unlimited.
   // if max is less than 0 it will throw an IllegalArgumentException.
   public void print(String text, int max) {
      if(max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory avaliableAnagram = new LetterInventory(text);
      List<String> extractedKeys = new ArrayList<String>();
      extractedKeys = extractKeys(avaliableAnagram, extractedKeys);
      Stack<String> stack = new Stack<String>();
      printOut(avaliableAnagram, max, stack, extractedKeys);
   }
   
   // Extracts the usable words from the given letter inventory and puts it into a list.
   // these words are the words that can be used to create anagrams using the dictionary
   // and user inputted text. 
   private List<String> extractKeys(LetterInventory Inventory, List<String> extractedKeys) {
         for(String word : dictionary) {
            LetterInventory usableKeys = Inventory.subtract(anagramSorter.get(word));
            if(usableKeys != null) {
               extractedKeys.add(word);
            }
         }
         return extractedKeys;
   }
   
   // prints out the anagrams when given the user inputted text that is converted into a letter
   // inventory and a list of words containing the possible words and respective letter inventories
   // and creates anagrams with them.
   // if the given letter inventory is empty it will print out [].
   private void printOut(LetterInventory inventory, int max, Stack<String> stack, 
                         List<String> list) {
      if(inventory.isEmpty()) {
         System.out.println(stack);
      } 
      if(max == 0 || max != stack.size()) {
         for(String word : list) {
            LetterInventory choiceInventory = inventory.subtract(anagramSorter.get(word));
            if(choiceInventory != null) {
               stack.push(word);
               printOut(choiceInventory, max, stack, list);
               stack.pop();
            }
         }
      }                                                                 
   }
}



   private QuestionNode readNode(Scanner input) {
      QuestionNode node = null;
      if(input.hasNextLine()) {
         String data = input.nextLine();
         if(data.contains("A:")) {
            node = new QuestionNode(data);
         } else {
            node = new QuestionNode(data, readNode(input), readNode(input));
         }
      }
      return node;
   }