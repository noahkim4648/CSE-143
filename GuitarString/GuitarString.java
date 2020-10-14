// Noah Kim
// 04/14/2020
// CSE 143 
// TA: Ketaki Deuskar 
// Assignment 2: GuitarString.Java

// This class will construct a guitar string of a given frequency 
import java.util.*;
public class GuitarString {

// DECAY_FACTOR is a constant double integer given by 0.996
// ringbuffer is initialized as a Queue  
   public static final double DECAY_FACTOR = 0.996;
   Queue<Double> ringbuffer;
   
// constructs a guitar string for a given frequency. It creates a LinkedList ringbuffer
// of the capacity required by taking the sample_rate divided by the frequency and rounding it 
// to the nearest integer
// if the frequency is less than or equal to 0 or the size is less than 2 it will throw
// IllegalArgumentException.
// the ringbuffer adds 0.0 for the entry for the given capacity for each entry.   
   public GuitarString(double frequency) {
      ringbuffer = new LinkedList<>();
      int size = (int) Math.round((StdAudio.SAMPLE_RATE/frequency));
      if (frequency <= 0 || size < 2) {
         throw new IllegalArgumentException("Frequency and Size too low");
      }
      for (int i = 0; i < size; i++) {
         ringbuffer.add(0.0);
      }
   }
   
// Creates the guitar string and initializes values into the ringbuffer from the init array.
// throws IllegalArgumentException if the init array has a length of less than 2    
   public GuitarString(double[] init) {   
      if (init.length < 2) {
         throw new IllegalArgumentException("Not enough entries");   
      }
      ringbuffer = new LinkedList<>();
      for (Double entries : init) {
         ringbuffer.add(entries);
      }
   }

// replaces the N elements in the ring buffer with a random value between
// -0.5(inclusive) and 0.5 (Exclusive)    
   public void pluck() {
      int size = ringbuffer.size();
      Random rand = new Random();
      for ( int i = 0; i < size; i++) {
         double random = rand.nextDouble() - 0.5;
         ringbuffer.add(random);
         ringbuffer.remove();
      }
   }

// Applies Karplus-Strong update.
// Removes the first sample and looks at the second sample and averages the two
// then multiplies it constant DECAY_FACTOR and adds it to the  back of the ringbuffer.   
   public void tic() {
      double first = ringbuffer.remove();
      double second = ringbuffer.peek(); 
      ringbuffer.add(((first + second) / 2) * DECAY_FACTOR);   
   }

// returns the current sample value.    
   public double sample() {
      return ringbuffer.peek();
   }
}