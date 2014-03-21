/*
 *  Source program for Homework Assignment #9 for CS 1A
 *  Written by David Dossantos, 3/9/2014
 *  This is a program that takes a pizza order for S/M/L size with toppings
 */



import java.util.*;

public class Foothill
{
   static final Scanner inputStream = new Scanner(System.in);
   
   
   public static void main(String args[])
   {
      PizzaOrder clientPizza = new PizzaOrder();
      char pizzaSize;
      int toppingNumber, pizzaSizeInt;
      
      pizzaSize = getSizeFromUser();
      while (pizzaSize != 'q')
      {
         
         if (pizzaSize == 's')
            pizzaSizeInt = 0;
         else if (pizzaSize == 'm')
            pizzaSizeInt = 1;
         else 
            pizzaSizeInt = 2;
         clientPizza.setPizzaSize(pizzaSizeInt);
         
         while (true) 
         {
            clientPizza.displayPizza();
            displayMainMenu();
            toppingNumber = inputStream.nextInt();
            if (toppingNumber == 0)
               
               break;
            if (!clientPizza.addTopping(toppingNumber))
               System.out.print("Error, enter a valid number\n");
            
         }
         //get current pizza
         clientPizza.displayPizza();
         //get final price
         System.out.format("\n TotalPrice is $%.2f\n", 
               clientPizza.getPrice()); 
         //reset the topping list for next order
         clientPizza.resetTopping();
         
         pizzaSize = getSizeFromUser();
      } 
      
      // prove a mutator
      if ( clientPizza.setPizzaSize('G') )
         System.out.println("G accepted as pizza size");
      else
         System.out.println("G rejected as pizza size");
      
      
   }
   
   static void displayMainMenu()
   {
      System.out.println("Select an item by number (0 when done):");
      
      for (int i = 0; i < PizzaOrder.TOPPINGS_OFFERED.length; i++)
         System.out.format("    %d. %s\n", i + 1, 
               PizzaOrder.TOPPINGS_OFFERED[i]);
      System.out.print("Selection: ");
   }
   
   static char getSizeFromUser()
   {
      String userStr;
      char userSize;
      
      System.out.print("Size of pizza ('s', 'm', 'l') or 'q' to quit: ");
      userStr = inputStream.next();
      userSize = Character.toLowerCase(userStr.charAt(0));   // store in letter
      
      
      while  ((userSize != 's') && (userSize != 'm') 
                  && (userSize != 'l') && (userSize != 'q'))
      {
         System.out.print("Size of pizza ('s', 'm', 'l') or 'q' to quit: ");
         userStr = inputStream.next();
         userSize = Character.toLowerCase(userStr.charAt(0));
         
      }
      
      return userSize;
   }
      
}

class PizzaOrder
{
   //private members
   public final int DEFAULT_PIZZA_SIZE = 0;   
   private int size;
   private String toppings[] = new String[MAX_TOPPINGS];
   int numToppings;
   
   //static class members
   public static final int MAX_TOPPINGS = 20;
   public static final String TOPPINGS_OFFERED[] 
         = { "onions", "bell peppers", "olives", "pepperoni","salami","ham" };
   public static final double TOPPINGS_BASE_COST = 2.00;
   public static final double BASE_PRICE = 7.00;
   
      
   
   //default constructor
   PizzaOrder()
   {
      setPizzaSize(DEFAULT_PIZZA_SIZE);
      numToppings = 0;
   }
   
   //constructor that takes size
   PizzaOrder(int size)
   {
      if (!setPizzaSize(size))
         setPizzaSize(DEFAULT_PIZZA_SIZE);
      numToppings = 0;
   }
   
   public boolean setPizzaSize(int size)
   {
      if ((size < 0) || (size > 2))
         return false;
      this.size = size;
      
      return true;
   }
   
   public int getSize()
   {
      return this.size;
   }
   
   public boolean addTopping(String topping)
   {
      if (numToppings >= MAX_TOPPINGS)
         return false;
         
      for (int i = 0; i < TOPPINGS_OFFERED.length; i++)
      {
         if (topping == TOPPINGS_OFFERED[i])
         {
            toppings[numToppings++] = topping;
            return true;
         }
      }
      
      return false;
   }
   
   public boolean addTopping(int toppingNumber)
   {

      if ((toppingNumber < 1) || (toppingNumber > TOPPINGS_OFFERED.length)
               || (numToppings >= MAX_TOPPINGS))
            return false;
      
      toppings[numToppings++] = TOPPINGS_OFFERED[toppingNumber-1];
      return true;
   }
   
   public double getPrice()
   {
      double priceIncrease;
      
      
      if (this.size == 0)
         priceIncrease = 1.0;
      else if (this.size == 1)
         priceIncrease = 1.15;
      else  
         priceIncrease = 1.25;
                
      
      return (BASE_PRICE + TOPPINGS_BASE_COST*numToppings)*priceIncrease;
   }
   
   public String stringizeSize()
   {
      switch (size)
      {
         case 0 : return "small";
         case 1 : return "medium";
         case 2 : return "large";
         default : return "";
      }
   }
   
   public String getToppings()
   {
      String listOfToppings = "";
      
      for (int i = 0; i < numToppings; i++)
      {
         
         if ((i != 0) && (i != (numToppings)))
            listOfToppings += " + ";
         
         listOfToppings += toppings[i];
      }
      
      return listOfToppings;
   }
   
   void displayPizza()
   {
      
      System.out.println("\nCurrent Pizza: " + stringizeSize() + " + "
            + getToppings());
   }
   
   void resetTopping()
   {
      for (int i = 0; i < numToppings; i++)
         toppings[i] = "";
      numToppings = 0;
   }
}