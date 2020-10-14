// Noah Kim
// 05/15/2020
// CSE 143
// TA: Ketaki Deuskar
// Assignment 5: GrammarSolver.Java

import java.io.*;
import java.util.*;

// this program will will take in a file that has grammar in the BNF format as a list of Strings
// and generate random variations of grammar when given a command. example:<noun>
// will produce a random noun object from the given file and <sentence> will produce
// a random sentence formed by words in the file. 
public class GrammarSolver {
   
   // stores the non-terminals and associated terminals
   private SortedMap<String, String[]> GrammarSorter;
   private Random r;
   
   // takes in grammar as a list of strings and
   // separates  terminal and non-terminal in the BNF format and sorts 
   // terminals to the correct non-terminals.
   // if the list of rules is empty it will throw an IllegalArgumentException
   // If there are multiple occurrences of a non-terminal, it will throw an
   // IllegalArgumentException.
   public GrammarSolver(List<String> rules) {
      if(rules.isEmpty()) {
         throw new IllegalArgumentException();
      }
      GrammarSorter = new TreeMap<String, String[]>();
      for(String word : rules) {
         String[] splitTerminal = word.split("::=");
         String nonTerminal = splitTerminal[0];
         String[] rule = splitTerminal[1].trim().split("\\|");
         if(GrammarSorter.containsKey(nonTerminal)) {
            throw new IllegalArgumentException();
         }
         GrammarSorter.put(nonTerminal, rule);
      }
      
   }
   
   // checks to see if the symbol is non-terminal
   public boolean grammarContains(String symbol) {
      return GrammarSorter.containsKey(symbol);
   }
   
   // prints out all the non-terminal symbols that are sorted, enclosed
   // by brackets, and separated by commas.
   public String getSymbols() {
      return GrammarSorter.keySet().toString();
   }
   
   // returns random occurrences of the given symbol for the given amount of times
   // if the amount of time is less than 0 or the symbol is not a non-terminal it will
   // throw an IllegalArgument Exception.
   public String[] generate(String symbol, int times) {
      if(times < 0 || !GrammarSorter.containsKey(symbol)) {
         throw new IllegalArgumentException();
      }
      String[] String = new String[times];
      for(int i = 0; i < times; i++) {
         String[i] = generateString(symbol);
      }
      return String;
   }
   
   // if the symbol is terminal, it will return the symbol.
   // otherwise constructs random occurrences of the given symbol
   // and returns random strings from the given non-terminal symbol
   // and each of the rules given have a equal probabilty of being chosen
   private String generateString(String symbol) {
      if(!grammarContains(symbol)) {
         return symbol;
      }  
      String finalGrammar = "";
      String [] terminal = GrammarSorter.get(symbol);
      Random r = new Random(); 
      int randIndex = r.nextInt(terminal.length);
      String[] randTerminal = terminal[randIndex].split("\\s+");
      for(String rules : randTerminal) {
         if(!GrammarSorter.containsKey(rules)) {
            finalGrammar += rules + " ";
         } else {
            finalGrammar += generateString(rules);
         }
      }
      return finalGrammar;
   }
} 