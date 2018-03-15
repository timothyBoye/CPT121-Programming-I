import java.io.PrintWriter;

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
public class AcademicEmployee extends Employee
{
   // stores education level of employee
   private boolean hasPHD;
      
   // class variables storing salary levels
   private static final double LEVEL_A_SALARY = 50000.0;
   private static final double LEVEL_B_SALARY = 60000.0;
   private static final double LEVEL_C_SALARY = 70000.0;
   private static final double LEVEL_D_SALARY = 80000.0;
   private static final double LEVEL_E_SALARY = 90000.0;
   
   // class variables storing corresponding roles for pay levels
   private static final String LEVEL_A_ROLE = "Associate Lecturer";
   private static final String LEVEL_B_ROLE = "Lecturer";
   private static final String LEVEL_C_ROLE = "Senior Lecturer";
   private static final String LEVEL_D_ROLE = "Associate Professor";
   private static final String LEVEL_E_ROLE = "Professor";
   
   /**
    * Basic constructor which takes an employee number and
    * name to create a new entry level academic employee. 
    * Academic Employees all start at Associate Lecturer
    * level with corresponding A level pay.
    * 
    * @param employeeNumber unique String representing the employee.
    * @param name employees full name.
    */
   public AcademicEmployee(String employeeNumber, String name)
   {
      // Send parameters and defaults to super
      super(employeeNumber, name, "Associate Lecturer", 'A');
      // set default value for PHD (better to be explicit)
      hasPHD = false;
   }
   
   
   /**
    * Constructs an object based on a String array passed to it
    * (for importing data from file). The constructor assumes:
    * 
    * index 0: tag representing type of employee class
    * index 1: Employee number
    * index 2: Employee's Name
    * index 3: Employee's job title/role
    * index 4: Employee's pay scale level
    * index 5: PHD qualified (true, false)
    * 
    * @param data
    */
   public AcademicEmployee(String[] data)
   {
      // send data stream to super class for main parameters
      super(data);
      
      // save PHD status to variable from file
      this.hasPHD = data[5].equals("true") ? true : false;
   }
   
   @Override
   /**
    * This method saves the instance of this class that calls it to
    * a file by accepting a PrintWriter stream and saving a colon 
    * separated line of data in the format:
    * 
    * <class tag>:<employee Number>:<name>:<role>:<level>:<PHD? true or false>
    * 
    * @param pw
    */
   public void saveToFile(PrintWriter pw)
   {
      // send PrintWriter to super class for main variables
      super.saveToFile(pw);
      // append PHD status to PrintWriter
      pw.print(":");
      pw.print(hasPHD);
   }
   
   
   @Override
   /**
    * Used to tag the data being saved to file by saveToFile(PrintWriter pw);
    * allowing easier error free importing of data.
    * 
    * @return class tag as String
    */
   public String getEmployeeType()
   {
      return "Academic";
   }
   
   @Override
   /**
    * Returns name of employee prefaced with Dr IFF the
    * employee has a PHD.
    * 
    * @return the employee's name as a String
    * 
    */
   public String getName()
   {
      // check if employee has a PHD return name
      // with or without Dr prefix as appropriate
      if (hasPHD)
         return "Dr " + super.getName();
      else
         return super.getName();
   }
   
   @Override
   /**
    * Accepts a char between A through E and a through e and 
    * then sets that pay level IFF the new level is one level
    * up from the previous level, is between A and E or a and e.
    * If these requirements are not met the method throws a 
    * PayScaleException with appropriate message else the level
    * and corresponding role are updated.
    * 
    * @param
    */
   public void updateLevel(char level) throws PayScaleException
   {
      // normalize input
      level = ("" + level).toUpperCase().charAt(0);
      
      // throw error if input out of range
      if (level < 'A' || level > 'E')
      {
         throw new PayScaleException("Error, pay scale level invalid." +
                  "\nAcceptable payscale levels are between A and E.");
      }
      // throw error if input is equal to the current level
      else if (level == super.getLevel())
      {
         throw new PayScaleException("Error, employee is already level " + level + ".");
      }
      // throw error if new level isn't one above old
      else if (level != super.getLevel() + 1)
      {
         throw new PayScaleException("Error, pay scale level invalid." +
                  "\nAccademic employees may only rise one level at a time.");
      }
      // passed checks update level by calling super class
      else
      {
         // update level
         super.updateLevel(level);
         // update role based on level
         switch (level)
         {
            case 'A':
               super.setRole(LEVEL_A_ROLE);
               break;
            case 'B':
               super.setRole(LEVEL_B_ROLE);
               break;
            case 'C':
               super.setRole(LEVEL_C_ROLE);
               break;
            case 'D':
               super.setRole(LEVEL_D_ROLE);
               break;
            case 'E':
               super.setRole(LEVEL_E_ROLE);
               break;
         }
      }
   }
   
   @Override
   /**
    * Returns the salary of the employee based on their 
    * pay scale level.
    * 
    * @return double salary based on pay level
    */
   public double getSalary()
   {
      if (super.getLevel() == 'A')
      {
         return LEVEL_A_SALARY;
      }
      else if (super.getLevel() == 'B')
      {
         return LEVEL_B_SALARY;
      }
      else if (super.getLevel() == 'C')
      {
         return LEVEL_C_SALARY;
      }
      else if (super.getLevel() == 'D')
      {
         return LEVEL_D_SALARY;
      }
      else
      {
         return LEVEL_E_SALARY;
      }
   }
   
   /**
    * Records the employees PHD if they don't have one,
    * else returns false as the employee already had a
    * PHD (no further change is made).
    * 
    * @return boolean true if update successful false if previously supplied
    */
   public boolean recordPHD()
   {
      if (hasPHD)
         return false;
      else
      {
         hasPHD = true;
         return true;
      }
   }
   
   @Override
   /**
    * Prints the employees details to the console
    */
   public void printDetails()
   {
      // print basic employee details by calling super class
      // (polymorphism means that the super class will call
      // this classes get methods which is what we want).
      super.printDetails();
      
      // print employees PHD status
      if (hasPHD)
         System.out.printf("%-18s%s\n", "Qualifications:", "Posseses PHD");
      else
         System.out.printf("%-18s%s\n", "Qualifications:", "Does not possess PHD");
   }
}
