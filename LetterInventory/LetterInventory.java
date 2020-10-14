// Noah Kim
// 4/5/2020
// Section BH: Ketaki Deuskar
// Assignment 1: LetterInventory.Java

// Class LetterInventory that stores letters to a string in an arraylist. It has
// the methods get, set, add, subtract, isEmpty, toString, and size. 
public class LetterInventory {


   public static final int ALPHABET_SIZE = 26;  // number of possible characters going into the list
   private int[] inventory;                     // list of characters 
   private int size;                            //current number of elements in the list
   
   //post: constructs an empty string LetterInventory.
   private LetterInventory() {
      this("");
   }
   
   //pre: Takes in a string of data and lowercases the letters
   // constructor ignores non-alphabetic characters
   //post: creates a letter inventory of the string and a count of its size.
   public LetterInventory(String data) {
      data = data.toLowerCase();
      inventory = new int[ALPHABET_SIZE];
      for (int i = 0; i < data.length(); i++) {
         if (data.charAt(i) >= 'a') {
            inventory[data.charAt(i) - 'a']++;
            size++;
         }   
      }
   }
   
   //pre: requires a character if not character (throws IllegalArgumentException)
   //post: returns an integer of the count of the letter in the inventory.
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      checkLetter(letter);
      return inventory[letter - 'a'];
   }
   
   //pre: requires a character, if not a character (throws IllegalArgumentException if not)
   //post: Checks to see if the given character is a letter.
   private void checkLetter(char letter) {
     if (!Character.isLetter(letter)) {
        throw new IllegalArgumentException("This is not a Character" + letter); 
     } 
   }

   //pre: Requires a character and integer value. 
   //Throws IllegalArgumentException when the character is not in the alphabet and is negative
   //post: changes the number of count for a single letter.
   public void set(char letter, int value) {
      checkLetter(letter);
      if (value < 0) {
         throw new IllegalArgumentException("Number is negative" + value);
      }
      int index = Character.toLowerCase(letter) - 'a';
      size = (size - inventory[index]) + value;
      inventory[index] = value;
   }
   
   // returns the current number of elements in the list 
   public int size() {
      return size;
   }
   
   //post: returns true if the inventory is zero, otherwise returns false. 
   public boolean isEmpty() {
      return size == 0;
   }
   
   //pre: Takes in all the characters in the inventory and lowercases and sorts it in alphabetical
   //     order
   //post: returns a string result of the characters in lowercase and alphabetical order
   //      if there is nothing in the inventory it will return an empty bracket.  
   public String toString() {
      String result = "[";
         for (int i = 0; i < ALPHABET_SIZE; i++) {
            for(int j = 0; j < inventory[i]; j++) {
               result +=  (char)(i + 'a');
            }
         }
         result += "]";
         return result;
   }
   
   //pre: requires a LetterInventory. Constructs a new LetterInventory by adding 
   //the original inventory to another inventory by adding the other inventory to the end.
   //post: returns a LetterInventory with the original inventory and other inventory added
   //to the end
   public LetterInventory add(LetterInventory other) {
      LetterInventory adding = new LetterInventory();
      for (int i = 0; i < ALPHABET_SIZE; i++) {
         adding.inventory[i] = inventory[i] + other.inventory[i];
      }
      adding.size = size + other.size;
      return adding;
   }
   
   //pre: requires a LetterInventory other. Constructs a new LetterInventory named subtracting by
   //  subtracting the given inventory by the other inventory.  
   //null if LetterInventory contains any letter with a difference less than 0.
   //post: returns the subtracting LetterInventory
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory subtracting = new LetterInventory();
      for (int i = 0; i < ALPHABET_SIZE; i++) {
         subtracting.inventory[i] = inventory[i] - other.inventory[i];
         if (subtracting.inventory[i] < 0) {
            return null; 
         }
      }
      subtracting.size = size - other.size;
      return subtracting;
   }
}