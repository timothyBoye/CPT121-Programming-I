/**
 * CPT121 Programming I.
 * Assignment 2.
 *
 * Student Number s3482043.
 * 
 * @author Timothy Boye
 * 
 * class ToyStore
 * 
 * Implements the data storage and functional requirements
 * for a program that manages the inventory in a toy store.
 */

import java.util.*;

public class ToyStore
{

   private static final Toy[] inventory = new Toy[8];
   private static final Scanner sc = new Scanner(System.in);
   
   /**
    * Initialises the database of Toys and implements the
    * main menu system, calling helper methods to perform
    * each task.
    * 
    * @param args none
    */
   public static void main(String[] args)
   {
      // Initialises the toy stores database values.
      inventory[0] = 
               new Toy("LEG-301", "Lego Star Wars Super Pack", 119.99, 2);
      inventory[1] =
               new Toy("LEG-210", "Lego Friends Sunshine Ranch", 79.99, 1);
      inventory[2] =
               new Toy("TOY-005", "Toy Story - Talking Sheriff Woody", 54.99,
                       12);
      inventory[3] = 
               new Toy("NER-020", "Nerf Rapid N-Strike Kit", 38.00, 15);
      inventory[4] = 
               new Toy("HOT-101", "Hot Wheels Cascade Blitz", 99.99, 10);
      inventory[5] =
               new Toy("LEG-401", "Lego Movie Evil Business Lord's Lair",
                       69.99, 10);
      inventory[6] = 
               new Toy("LEG-420", "Lego Movie Clock - Bad Cop", 24.99, 0);
      inventory[7] =
               new Toy("TOY-008", "Toy Story - Jessie the Talking Cowgirl",
                       39.99, 15);

      String userInput;

      // initialise selection variable to ascii nul to keep compiler happy
      char selection = '\0';

      do
      {
         // print menu to screen
         System.out.println("Toy Store Inventory Program");
         System.out.println("---------------------------");
         System.out.println();

         System.out.printf("%-25s%s\n", "Display Inventory", "A");
         System.out.printf("%-25s%s\n", "Print Reorder List", "B");
         System.out.printf("%-25s%s\n", "Product Range Search", "C");
         System.out.printf("%-25s%s\n", "Accept Delivery", "D");
         System.out.printf("%-25s%s\n", "Make Sale", "E");
         System.out.printf("%-25s%s\n", "Exit Program", "X");
         System.out.println();

         // prompt user to enter selection
         System.out.print("Enter selection: ");
         userInput = sc.nextLine();

         System.out.println();

         // validate selection input length
         if (userInput.length() != 1)
         {
            System.out.println("Error - invalid selection!");
         }
         else
         {
            // make selection "case insensitive"
            selection = Character.toUpperCase(userInput.charAt(0));

            // process user's selection
            switch (selection)
            {
               case 'A': // display inventory
                  System.out.println("Display Inventory Feature Selected");
                  displayInventory();
                  break;

               case 'B': // low stock list
                  System.out.println("Print Reorder List Feature Selected");
                  displayReorderList(5);
                  break;

               case 'C': // description search
                  System.out.println("Product Range Search Feature Selected");
                  doKeywordSearch();
                  break;

               case 'D': // delivery
                  System.out.println("Accept Delivery Feature Selected");
                  doDelivery();
                  break;

               case 'E': // sale
                  System.out.println("Make Sale Feature Selected");
                  doSale();
                  break;

               case 'X': // exit
                  System.out.println("Exiting the program...");
                  break;

               default:
                  System.out.println("Error - invalid selection!");
            }
         }
         System.out.println();

      } while (selection != 'X');

   }
   /**
    * Displays all Toys in the inventory including all
    * of their data (code, description, price and qty)
    */
   public static void displayInventory()
   {
      System.out.printf("%n%-10s %-45s %-10s %5s %n", "Code", "Description",
                        "Price", "Stock Level");
      System.out.println("--------------------------------------"
                         + "-----------------------------------------");
      // Loop through inventory and print their toString() value
      for (int i = 0; i < inventory.length; i++)
         System.out.println(inventory[i].toString());
   }
   
   /**
    * Displays all items below (and including) a minimum
    * quantity threshold that should be reordered.
    * 
    * @param _minLevel Quantity threshold for items before
    * reordering is required.
    */
   public static void displayReorderList(int _minLevel)
   {
      System.out.printf("%n%-10s %-45s %-5s %n", "Code", "Description",
                        "Stock Level");
      System.out.println("--------------------------------------"
                         + "------------------------------");
      boolean foundLowStock = false;
      // Loop through ALL inventory Toys
      for (int i = 0; i < inventory.length; i++)
      {
         // check if currently selected Toy is below qty threshold
         if (inventory[i].getQuantity() <= _minLevel)
         {
            // found low stock so print details of item
            System.out.printf("%-10s %-45s %-5s %n", inventory[i]
                                       .getProductCode(), inventory[i]
                                       .getDescription(),
                              inventory[i].getQuantity());
            // set flag to tell program low stock was found
            foundLowStock = true;
         }
      }
      // if no low stock was found print "no results"
      if (!foundLowStock)
         System.out.printf("%-10s %16s %n%n", " ", "No results found");
   }

   /**
    * Prompts user to input a keyword after which it will search
    * all Toy descriptions and display those Toys containing 
    * that keyword.
    */
   public static void doKeywordSearch()
   {
      System.out.print("\nPlease enter the keyword or string to "
                       + "search for: ");
      String KeywordSearch = sc.nextLine().toLowerCase();
      System.out.printf("%n%-45s %-10s %-15s %n", "Description", "Price",
                        "In Stock?");
      System.out.println("--------------------------------------"
                         + "-------------------------------");
      
      boolean foundKeyword = false;
      // loop through ALL Toys in inventory
      for (int i = 0; i < inventory.length; i++)
      {
         // check if current Toy's description matches the search term 
         // (not case sensitive)
         if (inventory[i].getDescription().toLowerCase().indexOf(KeywordSearch)
                  >= 0)
         {
            // check stock level, to appropriately print Toy 
            // details + in stock/out of stock
            if (inventory[i].getQuantity() > 0)
               System.out.printf("%-45s %-10s %-15s %n", inventory[i]
                                          .getDescription(),
                                 "$ " + inventory[i].getUnitPrice(),
                                 "In Stock");
            else
               System.out.printf("%-45s %-10s %-15s %n", inventory[i]
                                          .getDescription(),
                                 "$ " + inventory[i].getUnitPrice(),
                                 "Out of Stock");
            // set flag to tell program stock  matching the search was found
            foundKeyword = true;
         }
      }
      // if no stock was found print "no results"
      if (!foundKeyword)
         System.out.printf("%16s %n%n", "No results found");
   }

   /**
    * Prompts user to input an item code and quantity for the 
    * item being delivered then adds those items to the current
    * stock.
    */
   public static void doDelivery()
   {
      System.out.print("\n" + "Please enter the item code for the item: ");
      String stockSearch = sc.nextLine().toLowerCase();
      System.out.printf("%n%-10s %-45s %-10s %5s %n", "Code", "Description",
                        "Price", "Stock Level");
      System.out.println("--------------------------------------"
                         + "-----------------------------------------");
      
      boolean foundStock = false;
      // Loop through all the Toys to find matching code
      for (int i = 0; i < inventory.length; i++)
      {
         // check if current toy matches stock code (not case sensitive)
         if (inventory[i].getProductCode().toLowerCase().indexOf(stockSearch)
                  >= 0)
         {
            // ask for qty then submit to Toy class and display completion
            System.out.println(inventory[i].toString());
            System.out.print("\n" + "How many units are being delivered? ");
            inventory[i].restock(sc.nextInt());
            sc.nextLine(); // scanner "bug" fix
            System.out.println("\n" + "Delivery has been accepted, " +
                               "the stock level for " +
                               inventory[i].getDescription() + " is now " +
                               inventory[i].getQuantity());
            // set flag to indicate Toy was found then leave loop no
            // point wasting time in the loop once we know we're done
            foundStock = true;
            break;
         }
      }
      // if stock code wasnt found display "no results"
      if (!foundStock)
         System.out.printf("%-10s %16s %n%n", " ", "No results found");
   }

   /** 
    * Prompts user to enter item code and quantity for the item
    * they wish to buy, the item is then sold to the user if 
    * the code is valid and there is enough stock of that item.
    */
   public static void doSale()
   {
      System.out.print("\n" + "Please enter the item code for the item: ");
      String itemCodeSearch = sc.nextLine().toLowerCase();
      System.out.printf("%n%-10s %-45s %-10s %5s %n", "Code", "Description",
                        "Price", "Stock Level");
      System.out.println("--------------------------------------"
                         + "-----------------------------------------");
      
      boolean foundPurchase = false;
      // Loop through all Toys to find submitted stock code
      for (int i = 0; i < inventory.length; i++)
      {
         // check if Toy has the right code (not case sensitive)
         if (inventory[i].getProductCode().toLowerCase()
                  .indexOf(itemCodeSearch) >= 0)
         {
            // ask user for how many, submit to Toy class
            System.out.println(inventory[i].toString());
            System.out.print("\n" + "How many units are being purchased? ");
            double cost = inventory[i].sell(sc.nextInt());
            sc.nextLine(); // scanner "bug" fix
            // check if sale successful then print the result
            if (Double.isNaN(cost))
               System.out.println("\n"
                        + "There was insufficient quantity of this"
                        + " item, no purchase has been made.");
            else
               System.out.println("\n" + "Final cost of purchase is: \n$ " +
                        cost);
            // set flag to item found then break from loop no
            // point wasting time in loop if we know we're done
            foundPurchase = true;
            break;
         }
      }
      // if item wasnt found display "no results"
      if (!foundPurchase)
         System.out.printf("%-10s %16s %n%n", " ", "No results found");
   }
}
