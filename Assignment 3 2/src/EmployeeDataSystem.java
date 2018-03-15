import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * class EmployeeDataSystem
 * 
 * This class represents the portion of the Employee Data System 
 * program that has been completed by the previous programmer.
 * 
 * You are expected to complete the implementation of this class
 * progressively as you work through the various stages in the
 * assignment specification.
 * 
 * Note that it is ok to add additional helper methods to handle
 * common tasks (eg. searching for an object) at your discretion.
 * 
 * Also note that some helper methods have already been declared
 * in which you can implement the corresponding features in the
 * program if you wish.
 * 
 * Written by Craig Hamilton (April 2014)
 */

/**
 * University Employee Database Program
 * CPT121 / COSC2135 Programming 1
 * Assignment 3
 * SP1 2014
 * 
 * Student ID s3482043
 * 
 * @author timothyboye
 * 
 * The program uses an ArrayList to store the employees rather than
 * a standard array and count variable as this simplifies the main
 * programs code, removes the potential for errors such as forgetting
 * to update the count and has built in array size increasing 
 * functionality.
 * 
 * The program loads from and saves to a standard text file denoted by the
 * File variable extBackup (to make sure there is only one place this is 
 * defined) and in the tabular format:
 * <indicates mandatory field> [indicates AcademicEmployee only field]
 * 
 * <tag>:<employeeNumber>:<name>:<role>:<level>:[hasPHD]
 *
 */
public class EmployeeDataSystem
{

   // Employee array for the main program (accessible anywhere in this class).
   private static ArrayList<Employee> employees = new ArrayList<Employee>();

   // also declaring a Scanner here for shared use
   private static final Scanner sc = new Scanner(System.in);
   
   // file declaration for loading and saving the database (accessible anywhere in this class).
   private static final File extBackup = new File("employeeData.txt");
   
   
   
   /**
    * Controls the flow of the main program, accepting user input and calling
    * the relevant method as requested.
    * 
    * @param args all command line arguments are ignored
    */
   public static void main(String[] args)
   {
      // load data from file if data file exists
      loadDatabase();
      
//      // Create fake array of employees for testing
//      testEmployees();
      
      //
      // MAIN MENU
      //
      char selection = '\0';
      String userInput;
            
      // repeat until the user selects the "Exit" option
      do
      {
         // display menu options to the screen
         printMenu();
         
         // prompt the user to enter their selection
         userInput = sc.nextLine();
         System.out.println();
         
         if (userInput.length() != 1)
         {
            System.out.println("Error - invalid selection!");
         }
         else
         {
            // extract user's selection from the input String
            selection = userInput.charAt(0);
            
            // convert selection to upper case to make menu case-insensitive
            selection = Character.toUpperCase(selection);
            
            // process selection
            switch (selection)
            {
               case 'A': // create new standard employee
                  addNewEmployee();
                  break;
                  
               case 'B': // display all employee data in database
                  displayEmployeeSummary();
                  break;
                  
               case 'C': // update an employees data
                  updateEmployeePayScaleAndRole();
                  break;
                  
               case 'D': // create new academic employee
                  addNewAcademicEmployee();
                  break;
                  
               case 'E': // record PHD for an academic employee
                  recordPHDforAcademicEmployee();
                  break;                
               
               case 'X': // save and exit
                  saveDatabase();
                  break;
                  
               default:
                  System.out.println("Error - invalid selection!");
            }
         }
         
         System.out.println();
         
      } while (selection != 'X');
      
      System.out.println("Thank you.\n" +
               "The program will now terminate.");

   }
   
   
   
   /**
    * When called this method will set up the print writer to save a copy
    * of the programs state then call the save method on each object
    */
   private static void saveDatabase()
   {
      System.out.println("Saving employee data to " + extBackup.getName() + "...");

      try
      {
         // buffered printwriter for saving the file to extBackup
         PrintWriter pw = new PrintWriter(
                                 new BufferedWriter(
                                     new FileWriter(extBackup)));
         
         // loop through all objects and get them to save their own info to file
         for (int i = 0; i < employees.size(); i++)
         {
            employees.get(i).saveToFile(pw);
            pw.println();
         }
         
         // remember to close the file when you have finished writing!
         pw.close();
      }
      catch (IOException e)
      {
         System.out.println("Error - could not open file " + extBackup.getName() + " for writing.");
         System.out.println("The database was not saved/updated.");
      }
      
      System.out.println("Save complete.");

   }

   
   
   /**
    * When called this method will load data from File extBackup and create
    * the array of Employees
    */
   private static void loadDatabase()
   {
      System.out.println("Importing employee database " + extBackup.getName() + "...");
      try
      {
         // sets up the scanner to read the file extBackup
         Scanner fileScanner = new Scanner(new FileReader(extBackup));
         
         // loop through the file creating a new object from each lines arguments
         while(fileScanner.hasNextLine())
         {
            // get objects arguments
            String[] data = fileScanner.nextLine().split(":");
            
            // check the tag and call the appropriate Class / constructor
            //// Employee class
            if (data[0].equals("Employee"))
            {
               employees.add(new Employee(data));
            }
            //// AcademicEmployee class
            else if (data[0].equals("Academic"))
            {
               employees.add(new AcademicEmployee(data));
            }
            //// if the tag is an unknown word there has been an error
            else
            {
               System.out.println("An unknown error has occured, the database file may be corrupt.");
            }
         }
         
         // close the file!
         fileScanner.close();
         System.out.println("Import successful.\n");
         
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Database file " + extBackup.getName() + " not found - " +
                  "starting system in default (empty) state.\n");
      }
   }

   
   
   /**
    * Prints the main program menu for the users reference
    */
   public static void printMenu()
   {
      System.out.println("***** Employee Management System Menu *****");
      System.out.println();
      
      System.out.println("A. Add New Employee");
      System.out.println("B. Display Employee Summary");
      System.out.println("C. Update Employee Pay Scale Level / Role");
      System.out.println("D. Add New Academic Employee");
      System.out.println("E. Record PhD for Academic Employee"); 
      System.out.println("X. Exit");
      
      System.out.println();
      System.out.print("Enter your selection: ");
   }
   
   
   
   /**
    * Add New Employee
    * 
    * When called this method creates a new standard employee by
    * prompting the user for all the required information and checking
    * the validity of the users input before proceeded to add the user
    * to the database, print the new user for the users assurance then
    * returning.
    */
   public static void addNewEmployee()
   {
      System.out.println("** Add New Employee Feature Selected **");
      System.out.println();
      
      // Temporary variables to store users input
      String name;
      String eNum;
      String role;
      String payLvl;
      
      // error flag for the do while loops
      boolean repeatDueToError = false;
      
      // Prompt user for employee Name and keep looping until they enter
      // an actual value to avoid blank entries.
      do
      {
         System.out.println("Please enter the new employee's name or type 'x' to cancel:");
         name = sc.nextLine();
         
         // if nothing was entered display error
         if (name.isEmpty())
         {
            repeatDueToError = true;
            System.out.println("Sorry, you don't appear to have entered a name.");
         }
         // if x was entered return to main menu
         else if (name.toUpperCase().equals("X"))
            return;
         // All seems good accept input by breaking out of loop
         else
            repeatDueToError = false;
      } while (repeatDueToError);
      
      // Employee number with empty string check to avoid blank entries and
      // a further check to make sure employee numbers are unique as
      // I assume this later in searching by employee number.
      do
      {
         System.out.println("Please enter their employee number or type 'x' to cancel:");
         eNum = sc.nextLine();
         
         // If nothing is entered display error
         if (eNum.isEmpty())
         {
            repeatDueToError = true;
            System.out.println("Sorry, you don't appear to have entered an " +
                     "employee number.");
         }
         // if x is entered return to menu
         else if (eNum.toUpperCase().equals("X"))
            return;
         // All seems good so far, check if employee number has been used before
         else
         {
            // loop through all employees in the system and check if the
            // new employees number has been used before.
            repeatDueToError = false;
            for (int i = 0; i < employees.size(); i++)
            {
               // if found print error exit the loop then loop back to reprompt
               if (eNum.equalsIgnoreCase(employees.get(i).getEmployeeNumber()))
               {
                  repeatDueToError = true;
                  System.out.println("Sorry, you appear to have entered an " +
                           "employee number that is already in use.");
                  break; // as a preexisting employee was found we don't need to keep checking
               }
            }
         }
      } while (repeatDueToError);
      
      // Employee role with empty string check to avoid blank entries
      do
      {
         System.out.println("Please enter their role or type 'x' to cancel:");
         role = sc.nextLine();
         
         // if nothing entered display error
         if (role.isEmpty())
         {
            repeatDueToError = true;
            System.out.println("Sorry, you don't appear to have entered an " +
                     "employee role.");
         }
         // if x entered return to menu
         else if (role.toUpperCase().equals("X"))
            return;
         // else break out of loop as all is good
         else
            repeatDueToError = false;
      } while (repeatDueToError);
      
      // Employee pay level with empty string check to avoid blank entries, too many 
      // characters or characters not in the range of a through e or A through E
      do
      {
         System.out.println("Finally please enter their pay level (A-E) or type 'x' to cancel:");
         payLvl = sc.nextLine().toUpperCase();
         
         // if x entered return to menu
         if (payLvl.toUpperCase().equals("X"))
            return;
         // check for invalid input then loop again if invalid
         else if (payLvl.length() != 1 || payLvl.charAt(0) < 'A' 
                                 || payLvl.charAt(0) > 'E')
         {
            System.out.println("Sorry, that selection was invalid, " +
                     "please enter a pay level between A and E.");
            repeatDueToError = true;
         }
         // all good exit loop
         else
            repeatDueToError = false;
      } while (repeatDueToError);
      
      // Enter the details for the new employee into the ArrayList of employees
      employees.add(new Employee(eNum, name, role, payLvl.charAt(0)));
      
      // Print copy of new employee then exit to menu
      System.out.println();
      System.out.println("Thank you, the new employee has been entered into " +
               "the system: \n");
      employees.get(employees.size()-1).printDetails();
      System.out.println("\nNow returning you to the main menu.");
      
      System.out.println();
   }

   
   
   /**
    * Display Employee Summaries
    * 
    * This method loops through the database calling each objects
    * printDetails method to provide the user with a copy of all employees
    */
   public static void displayEmployeeSummary()
   {
      System.out.println("** Display Employee Summary Feature Selected **");
      System.out.println();
      
      // Run through all employee elements and print them to the console
      for(int i = 0; i < employees.size(); i++)
      {
         employees.get(i).printDetails();
         System.out.println();
      }
   }
   
   /**
    * Update any employees pay scale and role
    * 
    * This method prompts the user for an employee number then loops through
    * to find the employee once found the user is prompted for the pay scale 
    * update and if the employee is not an academic prompts again for a new 
    * role.
    */
   public static void updateEmployeePayScaleAndRole()
   {
      System.out.println("** Update Employee Pay Scale Level / Role Feature" +
               " Selected **");
      System.out.println();
      
      // prompt for employee number to edit employee
      System.out.println("Please enter the employees ID number: ");
      String userSearch = sc.nextLine();
      
      // verify there is a number to search for
      // if there is search else return error message
      if (userSearch.length() > 0)
      {
         // search for the employee matching the users input
         // store for later
         int empFound = -1;
         for (int i = 0; i < employees.size(); i++)
         {
            if (employees.get(i).getEmployeeNumber().equalsIgnoreCase(userSearch))
            {
               empFound = i;
               break;
            }
         }
         
         // if an employee was found prompt to update details
         // else print error.
         if (empFound > -1)
         {
            System.out.println("Employee found: " + employees.get(empFound).getName());
            
            // prompt for new level
            System.out.println("Please enter their new pay level (A-E): ");
            String payLvl = sc.nextLine();
            
            // if input was valid continue to update details 
            // else print error
            if (payLvl.length() == 1)
            {
               // Try to update the employees level
               try
               {
                  employees.get(empFound).updateLevel(payLvl.charAt(0));
                  
               }
               catch (PayScaleException e)
               {
                  System.out.println(e.getMessage());
                  System.out.println();
                  System.out.println("Now returning you to the main menu.");
                  
                  // There was an error we should not continue
                  // return to the calling method (assume main menu)
                  return;
               }
               
               //check class type
               boolean notAcademic = 
                        !(employees.get(empFound) instanceof AcademicEmployee);
               
               // if not an academic prompt for new employee role
               // else we're done return
               if (notAcademic)
               {
                  // prompt for role
                  System.out.println("Please enter the new role or press return to " +
                           "leave unchanged: ");
                  String role = sc.nextLine();
                  
                  // check role is not empty
                  if (!role.isEmpty())
                     // update role
                     employees.get(empFound).setRole(role);
               }
               System.out.println();
               System.out.println("Thank you, the employee "       +
                                  employees.get(empFound).getName()       + 
                                  "'s pay level and role is now "  +
                                  employees.get(empFound).getLevel()      +
                                  " "                              +
                                  employees.get(empFound).getRole()       +
                                  ".\nNow returning you to the main menu.");
            }
            else
            {
               System.out.println("Sorry, you don't appear to have entered appropriate data. " +
                        "\nNow returning you to the main menu.");
            }
         }
         else
         {
            System.out.println("Sorry, no employee was found. " +
                     "\nNow returning you to the main menu.");
         }
      }
      else
      {
         System.out.println("Sorry, you did not enter an employee number. " +
                  "\nNow returning you to the main menu.");
      }
      
   }
   
   /**
    * Add a new Academic Employee
    * 
    * This method prompts the user for academic employee data
    * validates it then creates a new employee in the database
    * before returning.
    */
   public static void addNewAcademicEmployee()
   {
      System.out.println("** Add New Academic Employee Feature Selected **");
      System.out.println();
      
      boolean repeatDueToError;
      String name;
      String eNum;
      
      // Employee Name with error check to avoid blank entries
      do
      {
         System.out.println("Please enter the new employee's name or type 'x' to cancel:");
         name = sc.nextLine();
         
         // if nothing entered display error and then reprompt
         if (name.isEmpty())
         {
            repeatDueToError = true;
            System.out.println("Sorry, you don't appear to have entered a name.");
         }
         // if x entered return to main menu
         else if (name.toUpperCase().equals("X"))
            return;
         // else all is good exit loop
         else
            repeatDueToError = false;
      } while (repeatDueToError);
      
      // Employee number with error check to avoid blank entries and
      // a further check to make sure employee numbers are unique as
      // we assume this later in searching by employee number.
      do
      {
         System.out.println("Please enter their employee number or type 'x' to cancel:");
         eNum = sc.nextLine();
         
         // if nothing entered print error then loop again
         if (eNum.isEmpty())
         {
            repeatDueToError = true;
            System.out.println("Sorry, you don't appear to have entered an " +
                     "employee number.");
         }
         // if x entered return to main menu
         else if (eNum.toUpperCase().equals("X"))
            return;
         // else all is good check employee number is new then exit if is.
         else
         {
            repeatDueToError = false;
            for (int i = 0; i < employees.size(); i++)
            {
               if (eNum.equalsIgnoreCase(employees.get(i).getEmployeeNumber()))
               {
                  repeatDueToError = true;
                  System.out.println("Sorry, you appear to have entered an " +
                           "employee number that is already in use.");
                  break;
               }
            }
         }
      } while (repeatDueToError);
      
      // Enter the details for the new employee into the ArrayList of employees
      employees.add(new AcademicEmployee(eNum, name));
      
      // Exit to menu
      System.out.println();
      System.out.println("Thank you, the new employee has been entered into " +
               "the system: \n");
      employees.get(employees.size()-1).printDetails();
      System.out.println("\nNow returning you to the main menu.");
      System.out.println();
   }
   
   /**
    * Record a PHD qualification for an Academic Employee
    * 
    * This method merely prompts the user for an employee number then
    * adds a PHD qualification if one was not already allocated to 
    * that employee.
    */
   private static void recordPHDforAcademicEmployee()
   {
      System.out.println("** Record PhD for Academic Employee Feature Selected **");
      System.out.println();
      
      // Prompt user for employee ID
      System.out.println("Please enter the academic employees ID number: ");
      String userSearch = sc.nextLine();
      
      // search for the employee then save the reference
      Employee empFound = null;
      for (int i = 0; i < employees.size(); i++)
      {
         if (employees.get(i).getEmployeeNumber().equalsIgnoreCase(userSearch))
         {
            empFound = employees.get(i);
            break;
         }
      }
      
      // check if an employee was found
      if (empFound != null)
      {
         // check if employee is an academic
         boolean isAcademic = empFound instanceof AcademicEmployee;
         if (isAcademic)
         {
            // academic employee was found set phd and check
            // if it goes through or if they already had one
            if (((AcademicEmployee)empFound).recordPHD())
            {
               System.out.println("The PHD for "            + 
                        empFound.getName()                  +
                        " has been recorded."               +
                        "\nNow returning you to the main menu.");
            }
            else
            {
               System.out.println("The PHD for "                           + 
                        empFound.getName()                                 +
                        " was recorded previously so no changes have been" +
                        " made."                                           +
                        "\nNow returning you to the main menu.");
            }
         }
         else
         {
            System.out.println("Sorry, cannot record PHD for non-academic "+
                     "staff. " +
                     "\nNow returning you to the main menu.");
         }
      }
      else
      {
         System.out.println("Sorry, no employee was found. " +
                  "\nNow returning you to the main menu.");
      }
      
   }
   
   
   
   /**
    * Filled Employee Array for Testing
    * 
    * This method is used for filling the Employees ArrAyList with
    * fake employee data to facilitate testing.
    */
   @SuppressWarnings("unused")
   private static void testEmployees()
   {
      System.out.println("Creating database for testing...");

      employees.add(new Employee("e111", "Tim Boye", "Programmer", 'E'));
      employees.add(new Employee("e112", "Jamie Oliver", "Administrator", 'A'));
      employees.add(new Employee("e113", "Boris Yeltsem", "Janetor", 'C'));
      employees.add(new Employee("e114", "Tony Abbott", "Supreme overlord of the little people", 'E'));
      try
      {
      employees.add(new AcademicEmployee("a111", "Hodor"));
      employees.add(new AcademicEmployee("a112", "Alexandre Dumas"));
      employees.get(employees.size()-1).updateLevel('B');
      employees.get(employees.size()-1).updateLevel('C');
      employees.get(employees.size()-1).updateLevel('D');
      employees.get(employees.size()-1).updateLevel('E');
      ((AcademicEmployee)employees.get(employees.size()-1)).recordPHD();
      employees.add(new AcademicEmployee("a113", "Jules Verne"));
      employees.get(employees.size()-1).updateLevel('B');
      employees.get(employees.size()-1).updateLevel('C');
      employees.get(employees.size()-1).updateLevel('D');
      employees.add(new AcademicEmployee("a114", "Kim Possible"));
      employees.get(employees.size()-1).updateLevel('B');
      
      System.out.println("Creation Successful.");
      }
      catch (PayScaleException e)
      {
         System.out.println(e.getMessage() + "\nIssue creating test data.");
      }
      
   }
   
}
