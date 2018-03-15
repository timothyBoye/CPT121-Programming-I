import java.util.Scanner;
/**
 * CPT121 Programming I.
 * Assignment 1.
 * 
 * PART 1: Enhanced Income Tax Calculator.
 * 
 * @author timothyboye
 * 
 * Student Number s3482043.
 *
 */
public class Part1
{
    public static final int TAX_RATE = 15;

    static String username = "default";
    static String taxFile = "default";
    static String financialYear = "default";
    static double grossIncome = 0;
    static double bankInterest = 0;
    static double superContribution = 0;
    static double deductions = 0;

    static double assesable = 0;
    static double offsets = 0;
    static double taxable = 0;
    static double taxPayable = 0;

    public static void main(String[] args)
    {
        System.out.println("\n*** Basic Income Tax Calculator ***");
        collectInfo();
        calculateTax();
        display();
    }

    /**
     * Prompts user for their tax information
     * then reads in their input to the global
     * variables ready for tax calculations.
     */
    private static void collectInfo()
    {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Name: ");
        username = sc.nextLine();
        System.out.print("\nEnter Tax File Number (TFN): ");
        taxFile = sc.nextLine();
        System.out.print("\nEnter financial year: ");
        financialYear = sc.nextLine();
        System.out.print("\nEnter assessable income for period " + financialYear
                + ": ");
        grossIncome = sc.nextDouble();
        System.out.print("\nEnter bank interest accrued: ");
        bankInterest = sc.nextDouble();
        System.out.print("\nEnter pre-tax superannuation "
                + "contribution for period " + financialYear + ": ");
        superContribution = sc.nextDouble();
        System.out.println("\nEnter claimable deduction(s) for period "
                + financialYear + ": ");
        deductions = sc.nextDouble();
    }
    
    /**
     * Reads the values in the global user data variables
     * then uses that information to calculate the tax payable
     * by the user.
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
        else
        {
            taxPayable = taxable / 100 * TAX_RATE;
        }
    }

    /**
     * Displays the users tax report based on the data
     * calculateTax() produced.
     */
    private static void display()
    {
        System.out.println("\n      *** Final Tax Statement ***\n");
        System.out.printf("%-22s %s %n", "Name:", username);
        System.out.printf("%-22s %s %n", "Tax File Number:", taxFile);
        System.out.printf("%-22s %s %n", "Financial Year:", financialYear);
        System.out.println("");
        System.out.printf("%-22s %s %13.2f %n", "Assessable Income:", "$", assesable);
        System.out.printf("%-22s %s %13.2f %n", "Minus Tax Offsets:", "$", -offsets);
        System.out.printf("%-22s %s %13.2f %n", "Taxable Income:", "$", taxable);
        System.out.printf("%-22s %s %13.2f %n", "Tax Payable:", "$", taxPayable);
    }

}
