import java.io.PrintWriter;

/*
 * class Employee
 * 
 * This class is a demonstration of how the Employee type could
 * have been implemented so as to fulfill the requirements set out
 * in stage 1 of the Assignment 3 specification.
 * 
 * You are expected to work off the implementation of this Employee
 * class for your program in Assignment 3.
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
 */
public class Employee
{
   // instance variables - requirement A)
   private String employeeNumber;
   private String name;
   private String role;
   private char level;

   // constants representing the salaries for different pay scale
   // levels - note that these were not a requirement in this assignment
   private static final double LEVEL_A_SALARY = 40000.0;
   private static final double LEVEL_B_SALARY = 45000.0;
   private static final double LEVEL_C_SALARY = 55000.0;
   private static final double LEVEL_D_SALARY = 65000.0;
   private static final double LEVEL_E_SALARY = 75000.0;

   // constructor - requirement B)
   public Employee(String employeeNumber, String name, 
                   String role, char level)
   {
      this.employeeNumber = employeeNumber;
      this.name = name;
      this.role = role;
      this.level = level;
   }
   
   /**
    * Constructs an employee object based on a String array passed to
    * it (for importing data from file). The constructor assumes:
    * 
    * index 0: tag representing type of employee class
    * index 1: Employee number
    * index 2: Employee's Name
    * index 3: Employee's job title/role
    * index 4: Employee's pay scale level
    * index 5: not used in this class.
    * 
    * @param data
    */
   public Employee(String[] data)
   {
      this.employeeNumber = data[1];
      this.name = data[2];
      this.role = data[3];
      this.level = data[4].charAt(0);
   }
   
   /**
    * This method saves the instance of this class that calls it to
    * a file by accepting a PrintWriter stream and saving a colon 
    * separated line of data in the format:
    * 
    * <class tag>:<employee Number>:<name>:<role>:<level>
    * 
    * @param pw
    */
   public void saveToFile(PrintWriter pw)
   {
      pw.print(getEmployeeType());
      pw.print(":");
      pw.print(employeeNumber);
      pw.print(":");
      pw.print(name);
      pw.print(":");
      pw.print(role);
      pw.print(":");
      pw.print(level);
   }
   /**
    * Used to tag the data being saved to file by saveToFile(PrintWriter pw);
    * allowing easier error free importing of data.
    * 
    * @return class tag as String
    */
   public String getEmployeeType()
   {
      return "Employee";
   }

   // accessors - requirement C)
   public String getEmployeeNumber()
   {
      return employeeNumber;
   }

   public String getName()
   {
      return name;
   }

   public String getRole()
   {
      return role;
   }

   public char getLevel()
   {
      return level;
   }


   // simple mutators - requirement D)
   
   public void setRole(String role)
   {
      this.role = role;
   }
   
   
   /**
    * Accepts a char variable between A through E and a through E, normalises that
    * data calling toUpperCase() then attempts to update the employees pay scale
    * if the data falls outside the accepted parametres the method throws a 
    * PayScaleException
    * 
    * @param level char between A through E or a through e representing pay level
    * @throws PayScaleException indicates the method was given input outside
    * acceptable parametres
    */
   public void updateLevel(char level) throws PayScaleException
   {
      // normalize input
      level = ("" + level).toUpperCase().charAt(0);
      
      // check for invalid pay scale level
      if (level < 'A' || level > 'E')
      {
         throw new PayScaleException("Error, pay scale level invalid." +
                  "\nAcceptable payscale levels are between A and E.");
      }
      else
      {
         this.level = level;
      }
   }

   // getEmployeeSalary() - requirement E) (ii)
   //
   // Returns the salary for the employee based on their
   // current pay scale level - note that we are assuming
   // the current pay scale level will be within the valid
   // range of pay scale levels (ie. A' to 'E')
   
   public double getSalary()
   {
      double salary;

      // check the Employee's current pay scale level and
      // note down corresponding salary for an employee at
      // the specified level
      if (level == 'A')
      {
         salary = LEVEL_A_SALARY;
      }
      else if (level == 'B')
      {
         salary = LEVEL_B_SALARY;
      }
      else if (level == 'C')
      {
         salary = LEVEL_C_SALARY;
      }
      else if (level == 'D')
      {
         salary = LEVEL_D_SALARY;
      }
      else
      {
         salary = LEVEL_E_SALARY;
      }

      // return the salary corresponding to the Employee's
      // current pay scale level
      return salary;
   }

   // printEmployeeDetails() - requirement F)
   //
   // Helper method which displays the details for the current
   // Employee to the screen.
   public void printDetails()
   {
      // print basic employee details
      System.out.printf("%-18s%s\n", "Employee Number:", employeeNumber);

      // use the accessor for name so that the overridden version can
      // be invoked polymorphically for an AcademicEmployee later on
      System.out.printf("%-18s%s\n", 
                        "Employee Name:", getName());

      System.out.printf("%-18s%s\n", "Employee Role:", role);
      System.out.printf("%-18s%c\n", "Pay Scale Level:", level);

      // get the salary for the employee and print it to the screen
      System.out.printf("%-18s$%.2f\n", "Employee Salary:", getSalary());
   }

}
