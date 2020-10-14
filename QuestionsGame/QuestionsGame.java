// Noah Kim
// 05/28/2020
// CSE 143
// TA: Ketaki Deuskar
// Assignment 7: QuestionsGame.Java

import java.util.*;
import java.io.*;

// This program will play the 20 questions game by taking in
// user input and tries to guess what word the user inputted by
// asking a series of yes or no questions and updating what the
// answer could be based on the answers to these questions.
public class QuestionsGame {
   
   private Scanner console;   // scanner to take in user input
   private QuestionNode root; // binary tree root to store Questions and answers
   
   // initializes the first object of the game called computer.
   public QuestionsGame() {
      root = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // Stores the current tree into the given output.
   // this method can be used to play another game with the computer
   // using questions stored in this tree.
   public void write(PrintStream output) {
      write(output, root);
   }
   
   // this method takes in the output and a QuestionNode and stores the contents
   // into an input file. it uses recursion to update the tree if the given node is not the answer
   // in a pre-order manner.
   private void write(PrintStream output, QuestionNode node) {
      if(isAnswerNode(node)) {
         output.println("A:");
         output.println(node.data);
         
      } else {
         output.println("Q:");
         output.println(node.data);
         write(output, node.yesNode);
         write(output, node.noNode);
      }
   }
   
   // Replaces the current tree by reading another tree that was inputted by the user
   // The method is under the assumption that the file is valid.
   public void read(Scanner input) {
         root = readNode(input);
   }
   
   // takes in the user input and returns a QuestionNode
   // While there is still lines of input the method will take in each line of input
   // and create an answer node (single leaf node) if it is the answer
   // otherwise it will create another question node
   private QuestionNode readNode(Scanner input) {
      String type = input.nextLine();
      String data = input.nextLine();
      QuestionNode root = new QuestionNode(data);
      
      if (type.contains("Q:")) {
         root.yesNode = readNode(input);
         root.noNode = readNode(input);
      }
      return root;
   }
   
   // plays a complete guessing game with the player using the current tree
   // asks the user a series of yes/no questions until it correctly or fails to guess
   // the object. if it fails it will ask what object they were thinking of, what
   // distinguishes between the object from the guess and whether the player's object
   // is the yes or no. if it is correct the game will end and the computer wins.
   public void askQuestions() {
      root = playGame(root);
   }
   
   // This method takes in a QuestionNode and plays the game. it will guess the object
   // correctly or incorrectly. if it is correct the game ends and the computer wins
   // if it isn't then it will expand the tree to include the new object and a new question
   // that distinguishes that object from others. takes in user input using a scanner.
   private QuestionNode playGame(QuestionNode node) {
      if(isAnswerNode(node)) {
         if(yesTo("Would your object happen to be " + node.data + "?")) {
            System.out.println("Great, I got it right!");
         } else {
            node = updatedNode(node);
         }
      } else {
         if(yesTo(node.data)) {
            node.yesNode = playGame(node.yesNode);
         } else {
            node.noNode = playGame(node.noNode);
         }
      }
      return node;
   }
   
   //If the computer guesses incorrectly, it will expand the tree by adding
   // a node created for the new object along with the question from the user
   // that distinguishes the object for others. If the user determines that it
   // not the answer for the object the node is put on the right side and if it
   // is correct it will create the node where the questions answer is the left node.
   // (single leaf node) and returns the node.
   private QuestionNode updatedNode(QuestionNode node) {
      System.out.print("What is the name of your object? ");
      QuestionNode answer = new QuestionNode(console.nextLine());
      System.out.println("Please give me a yes/no question that");
      System.out.println("distinguishes between your object");
      System.out.print("and mine--> ");
      String question = console.nextLine();
      if (yesTo("And what is the answer for your object?")) {
         node = new QuestionNode(question, answer, node);
      } else {
         node = new QuestionNode(question, node, answer);
      }
      return node;
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // returns whether the node is the answer node.
   private boolean isAnswerNode(QuestionNode node) {
      return (node.yesNode == null || node.noNode == null);
   }
   
   // this private class implements a binary tree system based off of yes/no
   // answers
   private static class QuestionNode {
      
      public String data;          //data that holds the object or question related to the object
      public QuestionNode yesNode; // node pointing to the correct answer
      public QuestionNode noNode;  // node pointed to an incorrect answer
      
      //this constructor initializes the answer node
      private QuestionNode(String data) {
         this(data,null,null);
      }
      
      //this constructor initializes the question node
      private QuestionNode(String data, QuestionNode yesNode,
      QuestionNode noNode) {
         this.data = data;
         this.yesNode = yesNode;
         this.noNode = noNode;
      }
   }
}
