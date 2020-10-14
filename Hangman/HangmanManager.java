// Noah Kim
// 04/30/2020
// CSE 143
// TA: Ketaki Deuskar
// Assignment 4: HangmanManager.Java
import java.util.*;
import java.io.*;

// this class HangmanManager creates the game Hangman taking in a dictionary,
// length of a word, and the max amount of guesses you can make. This version
// of hangman however is quite evil, it will only lower its possible words as you
// guess a letter, it will pick a word that does not include this letter until it has
// no other option.
// under the assumption all characters given are lowercase
public class HangmanManager {
   private int guessesLeft;
   private Set<String> words;
   private Set<Character> guesses;
   private String pattern;
   
   // takes in a dictionary containing a various amount of words, the length of the word
   // and the max amount of guesses you get. it constructs the empty pattern
   // of blanks for the desired length and fills a bank of words the hangman can choose from
   // if the length is less than one or the max is less than zero then the method will
   // throw an IllegalArgumentException
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if(length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      guessesLeft = max;
      words = new TreeSet<String>();
      guesses = new TreeSet<Character>();
      pattern = "";
      for(String word : dictionary) {
         if(word.length() == length) {
            words.add(word);
         }
      }
      for(int i = 0; i < length; i++) {
         pattern += "- ";
      }
   }
   
   // returns the current set of words the hangman is using for the game
   public Set<String> words() {
      return words;
   }
   
   // returns the amount of guesses the player has remaining
   public int guessesLeft() {
      return guessesLeft;
   }
   
   // returns the set of characters the player has already guessed
   public Set<Character> guesses() {
      return guesses;
   }
   
   // returns what the current pattern the hangman is on.
   // if the set of words is empty then it will throw an IllegalStateException
   public String pattern() {
      if(words.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   // takes in the guessed character and records whether or not it is in the pattern.
   // it transfers the guessed letter into the list of guesses the player has made.
   // if the guess is not in the word bank the player loses a guess
   // and if it is it returns the number of occurences that the guess showed up in the pattern.
   // if there is less than 1 guess left or the set of words is empty it will throw a
   // IllegalStateException,
   // if the guess was already guessed it will throw an IllegalArgumentException
   public int record(char guess) {
      if(guessesLeft < 1 || words.isEmpty()) {
         throw new IllegalStateException();
      } else if(guesses.contains(guess)) {
         throw new IllegalArgumentException();
      }
      int occurences = 0;
      guesses.add(guess);
      //constructs several word banks associated with different patterns
      Map<String, Set<String>> possiblePatterns = new TreeMap<String, Set<String>>();
      for(String word : words) {
         String newPattern = createPattern(guess, word);
         if(!possiblePatterns.containsKey(newPattern)) {
            Set<String> wordsInPattern = new TreeSet<String>();
            wordsInPattern.add(word);
            possiblePatterns.put(newPattern, wordsInPattern);
         } else {
            possiblePatterns.get(newPattern).add(word);
         }
      }
      //chooses the best (least amount of characters revealed) pattern and the word bank
      // associated with it
      String bestPattern = bestPattern(possiblePatterns);
      pattern = bestPattern;
      words = possiblePatterns.get(bestPattern);
      return numberOfOccurences(occurences, guess);
   }
   
   // takes in the guess and the word and creates a pattern for that word.
   private String createPattern(char guess, String word) {
      String creatingPattern = "";
      for(int i = 0; i < word.length(); i++) {
         if(guess != word.charAt(i)) {
            creatingPattern += pattern.substring(2*i,(2 * i) + 2);
            
         } else {
            creatingPattern += guess + " ";
         }
      }
      return creatingPattern;
   }
   
   // takes in all the patterns and chooses the pattern that has the least amount
   // of characters displayed. EX: - - - - over - a - - it will choose the empty
   // pattern
   private String bestPattern(Map<String, Set<String>> possiblePatterns) {
      int maximum = 0;
      String bestPattern = "";
      for(String word : possiblePatterns.keySet()) {
         if(possiblePatterns.get(word).size() > maximum) {
            maximum = possiblePatterns.get(word).size();
            bestPattern = word;
         }
      }
      return bestPattern;
   }
   
   // returns the number of occurences the guess appears in the pattern
   private int numberOfOccurences(int occurences, char guess) {
      for(int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            occurences++;
         }
      }
      if(occurences == 0) {
         guessesLeft--;
      }
      return occurences;
   }
}