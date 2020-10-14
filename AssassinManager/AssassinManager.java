// Noah Kim
// 04/23/2020
// CSE 143
// TA: Ketaki Deuskar
// Assignment 3: AssassinManager.Java

//A program that manages a game called Assassins where it takes a list of players and
//forms a kill ring where each person stalks another. Eventually when there is one remaining
// killer that person is the winner and the game is over.
import java.util.*;
public class AssassinManager {
   
   // stores front of the kill ring and the graveyard
   private AssassinNode killRing;
   private AssassinNode graveyard;
   
   // takes a list of names and constructs a kill ring
   // pre: list of names has to not be empty otherwise throws IllegalArugmentException
   // post: builds a kill ring of the individual names
   public AssassinManager(List<String> names) {
      if(names.isEmpty() || names == null) {
         throw new IllegalArgumentException("The list is empty");
      }
      for(int i = names.size() - 1; i >= 0; i--) {
         killRing = new AssassinNode(names.get(i), killRing);
      }
   }
   
   // prints out the kill ring of killers and who they are stalking
   public void printKillRing() {
      AssassinNode current = killRing;
      while(current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRing.name);
   }
   
   //prints out the name of the victim and who they were killed by in the graveyard
   public void printGraveyard() {
      AssassinNode current = graveyard;
      if(graveyard == null) {
         System.out.print("");
      } else {
         while(current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
         }
      }
   }
   
   // pre: takes in a list of names and checks to see if that name is in the list
   // if the list is empty it will default to false 
   // ignores the casing of the name so each word only has one reference. ex: HiGh = high
   // post: returns whether the name is in the list or not
   private boolean checkName(AssassinNode check, String name) {
      if(check == null) {
         return false;
      }
      AssassinNode current = check;
      while(current != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // checks to see if the name is in the kill ring or not
   // returns whether the given name is in the kill ring or not
   // ignores casing of the given name
   public boolean killRingContains(String name) {
      return checkName(killRing, name);
   }
   
   //checks to see whether the name is in the graveyard or not
   // returns whether the given name is in the graveyard or not.
   // ignores casing of the given name
   public boolean graveyardContains(String name) {
      return checkName(graveyard, name);
   }
   
   // checks to see if the game is over by seeing if the kill ring has another person
   public boolean gameOver() {
      return (killRing.next == null);
   }
   
   // returns the winner of the game
   // if the game is not over null will be returned
   public String winner() {
      if(gameOver()) {
         return killRing.name;
      } else {
         return null;
      }
   }
   
   // moves the given name from the kill ring and puts them into the graveyard
   // and records who killed them
   // If the game is over it will throw an IllegalStateException
   // if the name is not in the kill Ring it will throw an IllegalArgumentException
   // if both these conditions are true the throw IllegalStateException will supercede
   // the IllegalArgumentException
   // ignores the casing of the given name, any variation of capitalization will be
   // considered the same. EX: HiGh = high
   public void kill(String name) {
      if(gameOver() == true || (gameOver() == true && !killRingContains(name))) {
         throw new IllegalStateException("The game is over");
      }
      if(!killRingContains(name)) {
         throw new IllegalArgumentException("Player does not exist");
      }
      AssassinNode killed = killRing;
      AssassinNode current = killRing;
      if(killRing.name.equalsIgnoreCase(name)) {  //given name is front of kill ring
         AssassinNode previous = current;
         while(current != null) {
            previous = current;
            current = current.next;
         }
         killed.killer = previous.name;
         killRing = killRing.next;
         if(graveyard != null) {
            killed.next = graveyard;
         } else {
            killed.next = null;
         }
         graveyard = killed;
      } else {
         while(!current.next.name.equalsIgnoreCase(name)) { //given name is not at the front
            current = current.next;
         }
         killed = current.next;
         killed.killer = current.name;
         if(current.next.next != null) {
            current.next = current.next.next;
         } else {
            current.next = null;
         } 
         killed.next = graveyard;
         graveyard = killed;
      }   
   }
}