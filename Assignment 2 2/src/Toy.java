/**
 * CPT121 Programming I.
 * Assignment 2.
 *
 * Student Number s3482043.
 * 
 * @author Timothy Boye
 * 
 * Class Toy
 * 
 * A representation of a Toy that is sold in a toy store.
 * 
 */

public class Toy
{
   private String productCode;
   private String description;
   private int quantity;
   private double unitPrice;
   
   public Toy(String _code, String _desc, double _price, int _qty)
   {
      productCode = _code;
      description = _desc;
      quantity = _qty;
      unitPrice = _price;
   }
   
   /**
    * This method sell(units) should be invoked when selling items to a 
    * customer, it takes the number of units required as input, returns
    * the total cost and updates the inventory level. A return value of 
    * Double.NaN is returned when their are not enough units in inventory
    * and the inventory level is unaffected.
    * 
    * @param _units, number of units required as int
    * @return cost as double or Double.NaN if insufficient quantity available
    */
   public double sell (int _units)
   {
      if (_units <= quantity)
      {
         quantity -= _units;
         return _units * unitPrice;
      }
      else
         return Double.NaN;
   }
   
   /**
    * Accepts number of units to add to inventory as argument
    * then updates the inventory.
    * 
    * @param _units number of units to add to inventory
    */
   public void restock(int _units)
   {
      quantity += _units;
   }
   /**
    * Returns a formated string including product code, description, 
    * unit price and quantity in inventory.
    * 
    * @return formated string %-10s %-45s $ %-8.2f %-5d
    */
   public String toString()
   {
      return String.format("%-10s %-45s $ %-8.2f %-5d", productCode, 
                           description, unitPrice, quantity);
   }
   
   // Accessors //
   public String getProductCode()
   {
      return productCode;
   }
   public String getDescription()
   {
      return description;
   }
   public int getQuantity()
   {
      return quantity;
   }
   public double getUnitPrice()
   {
      return unitPrice;
   }
}
