import java.util.Scanner;

/**
 * CPT121 Programming I.
 * Assignment 1.
 * 
 * PART 3: Final Income Tax Calculator.
 * 
 * Student Number s3482043.
 * 
 * @author Timothy Boye
 * 
 * NOTES TO MARKER:
 * I originally had a number of methods to encapsulate similar functionalities
 * outside the main method as I find that easier to read and follow plus
 * it allows greater flexibility and easier reprogramming later, however
 * due to the numerous warnings in the live chats and assignment spec I
 * moved most of these back into the main method which is why the main
 * method is now quite crowded. I hope this is more akin to what we were
 * being asked for.
 * The calculateTax method was NOT moved back into main as this would
 * have made my unit testing of the tax system much harder (I know that
 * unit testing is beyond the scope of this assignment I just wanted to
 * make sure it worked). The unit tests however were not included as they
 * used an additional library (JUnit) which was explicitly forbidden in
 * the final submission.
 * There are two further helper methods which I hope were in the spirit
 * of what is allowed, these are merely to put the scanner and scanner bug
 * code in one place rather than repeat it.
 * Therefore main is reserved for program structure and user output/prompting,
 * all calculations and input are handled by the three external methods.
 */

public class Part3
{
   private static Scanner scanner = new Scanner(System.in);
   // tax rates
   private static final double MEDICARE_RATE = 1.5;
   // Threshold 1
   private static final int T1_LIMIT = 10000;
   private static final int T1_BASE_TAX = 0;
   private static final int T1_TAX_RATE = 0;
   // Threshold 2
   private static final int T2_LIMIT = 40000;
   private static final int T2_BASE_TAX = 0;
   private static final int T2_TAX_RATE = 15;
   // Threshold 3
   private static final int T3_LIMIT = 80000;
   private static final int T3_BASE_TAX = 4500;
   private static final int T3_TAX_RATE = 30;
   // Threshold 4
   private static final int T4_LIMIT = 120000;
   private static final int T4_BASE_TAX = 16500;
   private static final int T4_TAX_RATE = 40;
   // Threshold 5
   private static final int T5_BASE_TAX = 32500;
   private static final int T5_TAX_RATE = 45;

   // user data
   private static String username = "default";
   private static String taxFile = "default";
   private static String financialYear = "default";
   private static double grossIncome = 0;
   private static double bankInterest = 0;
   private static double superContribution = 0;
   private static double deductions = 0;
   private static boolean privateHC = false;
   private  static String taxAgent = "default";

   // tax calculation variables
   private static double assesable = 0;
   private static double offsets = 0;
   private static double taxable = 0;
   private static double taxPayable = 0;
   private static double medicarePayable = 0;

   public static void main(String[] args)
   {
      System.out.println("\n*** Final Income Tax Calculator ***");

      // Basic user details
      System.out.print("\nEnter Name: ");
      username = readLine();
      System.out.print("\nEnter Tax File Number (TFN): ");
      taxFile = readLine();
      System.out.print("\nEnter financial year: ");
      financialYear = readLine();

      // Enter looped main menu system
      /*
       * Uses a while loop and switch case to display the main menu, wait for
       * selection then read in appropriate input before repeating until x is
       * pressed.
       */
      boolean loop = true;
      while (loop)
      {
         char selection;

         System.out.print("\n--------\n");
         System.out.print("\n*** Taxation Data Entry System ***\n"
                          + "\nA - Add assessable income"
                          + "\nB - Add interest accrued from bank account"
                          + "\nC - Add pre-tax superannuation contribution"
                          + "\nD - Add claimable deduction"
                          + "\nX - Exit and compile final taxation statement\n"
                          + "\nEnter your selection: ");
         selection = scanner.nextLine().toLowerCase().charAt(0);
         switch (selection)
         {
            case 'a':
               System.out.print("\nEnter assessable income for period " +
                                financialYear + ": $");
               grossIncome += readDouble();
               break;
            case 'b':
               System.out.print("\nEnter bank interest accrued: $");
               bankInterest += readDouble();
               break;
            case 'c':
               System.out.print("\nEnter pre-tax superannuation contribution" +
                                " for period " + financialYear + ": $");
               superContribution += readDouble();
               break;
            case 'd':
               System.out.print("\nEnter claimable deduction(s) for period " +
                                financialYear + ": $");
               deductions += readDouble();
               break;
            case 'x':
               System.out.print("\nTaxation data entry complete.\n "
                                + "\n--------\n");
               loop = false;
               break;
            default:
               System.out.print("\n--------\n\n"
                                + "Please enter a VALID argument: "
                                + "a, b, c, d or x \n");
         }
      }

      // Further user details
      /*
       * Private health insurance check do while loop surrounding a switch case
       * to check input and keep asking for valid input until y or n is entered.
       */
      boolean medLoop;
      do
      {
         medLoop = false;
         System.out.print("\nDoes the taxpayer currently have "
                          + "private health insurance? (Y/N): ");
         char medicareTemp = readLine().toLowerCase().charAt(0);
         switch (medicareTemp)
         {
            case 'n':
               privateHC = false;
               break;
            case 'y':
               privateHC = true;
               break;
            default:
               System.out.print("\nPlease enter a VALID argument: y or n\n");
               medLoop = true;
         }
      } while (medLoop);
      // Tax agent
      System.out.print("\nEnter tax agent name: ");
      taxAgent = readLine();

      // Calculate tax payable
      //// Separate method to allow easy unit testing of the tax system
      calculateTax();

      // Display final tax report
      System.out.println("\n      *** Final Tax Statement ***\n");
      System.out.printf("%-22s %s %n", "Name:", username);
      System.out.printf("%-22s %s %n", "Tax File Number:", taxFile);
      System.out.printf("%-22s %s %n", "Financial Year:", financialYear);
      System.out.println("");
      System.out.printf("%-22s %s %13.2f %n", "Assessable Income:", "$",
                        assesable);
      System.out.printf("%-22s %s %13.2f %n", "Minus Tax Offsets:", "$",
                        -offsets);
      System.out.printf("%-22s %s %13.2f %n", "Taxable Income:", "$", taxable);
      System.out.println("");
      System.out.printf("%-22s %s %13.2f %n", "Tax Payable:", "$", taxPayable);
      if (medicarePayable > 0)
         System.out.printf("%-22s %s %13.2f %n", "Medicare levy:", "$",
                           medicarePayable);
      System.out.println("");
      System.out.printf("%-22s %s %n", "Tax Agent:", taxAgent);
   }

   /**
    * Reads the values in the global user data variables then uses that
    * information to calculate the tax payable by the user. This method is
    * public to make life easier testing the tax calculations with my unit
    * tests.
    */
   public static void calculateTax()
   {
      // tax prework
      assesable = grossIncome + bankInterest;
      offsets = superContribution + deductions;

      taxable = assesable - offsets;

      // taxable income check and tax payable calculation
      if (taxable <= 0)
      {
         taxable = 0;
         taxPayable = 0;
      }
      else if (taxable <= T1_LIMIT)
      {
         taxPayable = T1_BASE_TAX + (taxable / 100 * T1_TAX_RATE);
      }
      else if (taxable <= T2_LIMIT)
      {
         taxPayable = T2_BASE_TAX + ((taxable - T1_LIMIT) / 100 * T2_TAX_RATE);
      }
      else if (taxable <= T3_LIMIT)
      {
         taxPayable = T3_BASE_TAX + ((taxable - T2_LIMIT) / 100 * T3_TAX_RATE);
      }
      else if (taxable <= T4_LIMIT)
      {
         taxPayable = T4_BASE_TAX + ((taxable - T3_LIMIT) / 100 * T4_TAX_RATE);
      }
      else
      {
         taxPayable = T5_BASE_TAX + ((taxable - T4_LIMIT) / 100 * T5_TAX_RATE);
      }

      // Medicare calculation
      if (privateHC)
         medicarePayable = 0;
      else
         medicarePayable = taxable / 100 * MEDICARE_RATE;
   }

   /**
    * Reads double from scanner and applies 'scanner bug' fix in one go rather
    * than apply the scanner bug fix multiple times after reading scanner
    * directly.
    * 
    * @return double
    */
   private static double readDouble()
   {
      double result = scanner.nextDouble();
      if (scanner.hasNextLine()) // scanner bug fix
         scanner.nextLine(); // scanner bug fix
      return result;
   }

   /**
    * As the nextDouble required a separate method for convenience, an
    * equivalent method for reading lines from scanner was implemented to avoid
    * confusion within the code.
    * 
    * @return String
    */
   private static String readLine()
   {
      String result = scanner.nextLine();
      return result;
   }
}
