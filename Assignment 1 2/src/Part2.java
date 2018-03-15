import java.util.Scanner;

/**
 * CPT121 Programming I.
 * Assignment 1.
 * 
 * PART 2: Enhanced Income Tax Calculator.
 * 
 * Student Number s3482043.
 * 
 * @author timothyboye
 */
public class Part2
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
   static String username = "default";
   static String taxFile = "default";
   static String financialYear = "default";
   static double grossIncome = 0;
   static double bankInterest = 0;
   static double superContribution = 0;
   static double deductions = 0;
   static boolean privateHC = false;
   static String taxAgent = "default";

   // tax calculation variables
   static double assesable = 0;
   static double offsets = 0;
   static double taxable = 0;
   static double taxPayable = 0;
   static double medicarePayable = 0;

   public static void main(String[] args)
   {
      System.out.println("\n*** Enhanced Income Tax Calculator ***");
      collectInfo();
      calculateTax();
      display();
   }

   private static double readDouble()
   {
      double result = scanner.nextDouble();
      if (scanner.hasNextLine()) // scanner bug fix
         scanner.nextLine(); // scanner bug fix
      return result;
   }

   private static String readLine()
   {
      String result = scanner.nextLine();
      return result;
   }

   /**
    * Prompts user for their tax information then reads in their input to the
    * global variables ready for tax calculations.
    */
   private static void collectInfo()
   {
      // User details
      System.out.print("\nEnter Name: ");
      username = readLine();
      System.out.print("\nEnter Tax File Number (TFN): ");
      taxFile = readLine();
      System.out.print("\nEnter financial year: ");
      financialYear = readLine();

      // Tax Information
      System.out.print("\nEnter assessable income for period "
                       + financialYear + ": ");
      grossIncome = readDouble();
      System.out.print("\nEnter bank interest accrued: ");
      bankInterest = readDouble();
      System.out.print("\nEnter pre-tax superannuation "
                       + "contribution for period " + financialYear + ": ");
      superContribution = readDouble();
      System.out.print("\nEnter claimable deduction(s) for period "
                       + financialYear + ": ");
      deductions = readDouble();

      // Medicare
      System.out
               .print("\nDoes the taxpayer currently have private health insurance? (Y/N): ");
      String medicareTemp = readLine();
      if (medicareTemp.charAt(0) == 'n' || medicareTemp.charAt(0) == 'N')
         privateHC = false;
      else if (medicareTemp.charAt(0) == 'y' || medicareTemp.charAt(0) == 'Y')
         privateHC = true;

      // Tax agent
      System.out.print("\nEnter tax agent name: ");
      taxAgent = readLine();
   }

   /**
    * Reads the values in the global user data variables then uses that
    * information to calculate the tax payable by the user.
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
         taxPayable = T2_BASE_TAX
                      + ((taxable - T1_LIMIT) / 100 * T2_TAX_RATE);
      }
      else if (taxable <= T3_LIMIT)
      {
         taxPayable = T3_BASE_TAX
                      + ((taxable - T2_LIMIT) / 100 * T3_TAX_RATE);
      }
      else if (taxable <= T4_LIMIT)
      {
         taxPayable = T4_BASE_TAX
                      + ((taxable - T3_LIMIT) / 100 * T4_TAX_RATE);
      }
      else
      {
         taxPayable = T5_BASE_TAX
                      + ((taxable - T4_LIMIT) / 100 * T5_TAX_RATE);
      }

      // Medicare calculation
      if (privateHC)
         medicarePayable = 0;
      else
         medicarePayable = taxable / 100 * MEDICARE_RATE;
   }

   /**
    * Displays the users tax report based on the data calculateTax() produced.
    */
   private static void display()
   {
      System.out.println("\n      *** Final Tax Statement ***\n");
      System.out.printf("%-22s %s %n", "Name:", username);
      System.out.printf("%-22s %s %n", "Tax File Number:", taxFile);
      System.out.printf("%-22s %s %n", "Financial Year:", financialYear);
      System.out.println("");
      System.out.printf("%-22s %s %13.2f %n", "Assessable Income:", "$",
                        assesable);
      System.out.printf("%-22s %s %13.2f %n", "Minus Tax Offsets:", "$",
                        -offsets);
      System.out
               .printf("%-22s %s %13.2f %n", "Taxable Income:", "$", taxable);
      System.out.println("");
      System.out
               .printf("%-22s %s %13.2f %n", "Tax Payable:", "$", taxPayable);
      if (medicarePayable > 0)
         System.out.printf("%-22s %s %13.2f %n", "Medicare levy:", "$",
                           medicarePayable);
      System.out.println("");
      System.out.printf("%-22s %s %n", "Tax Agent:", taxAgent);
   }

}
