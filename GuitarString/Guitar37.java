// Noah Kim
// 04/14/2020
// CSE 143 
// TA: Ketaki Deuskar
// Assignment 2: Guitar37.Java

// Guitar37 that implements Guitar interface and models a guitar with 37 strings
public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   private GuitarString[] Strings;              // Array of guitar strings
   private int tics;                            // the number of tics
  
// creates a new GuitarString array with the length of the Keyboard
// creates 37 guitar strings between the frequencies 110 to 880 Hz
   public Guitar37() {
      Strings = new GuitarString[KEYBOARD.length()]; 
      for(int i = 0; i < KEYBOARD.length(); i++) {
         Strings[i] = new GuitarString(440.0 * Math.pow(2, (i - 24.0)/12.0));
      }
   }

// Plays the note by plucking the string at the given pitch. starting pitch ConcertA begins
// when the "v" key is plucked which happens pitch + 24. If it cannot play the note it ignores it
   public void playNote(int pitch) {
      int index = pitch + 24;
      if((index >= 0) && (index < KEYBOARD.length())) {
         Strings[index].pluck();
      }   
   }

// If the character does not have a corresponding guitar string, throws IllegalArgumentException
// Plucks the string at the given character.   
   public void pluck(char string) {
      if(!hasString(string)) {
         throw new IllegalArgumentException("Not a string");
      }
      Strings[KEYBOARD.indexOf(string)].pluck();
   }

// checks to see whether the character has a corresponding string for the string
// otherwise returns it is not a character with a corresponding string.  
   public boolean hasString(char string) {
      return (KEYBOARD.indexOf(string) != -1);
   }

// returns the sum of the current samples using a for- each loop   
   public double sample() {
      double total = 0.0;
      for (GuitarString string: Strings) {
         total += string.sample();
      }
      return total;
   }
   
// counts the number of tics
// using a for-each loop applies Karplus-Strong update for the array of GuitarString Strings
// for each element in the array.
   public void tic() {
      for(GuitarString string: Strings) {
         string.tic();
      }
      tics++;
   }

// returns the time forward by the number of tics
   public int time() {
      return tics;
   } 
}