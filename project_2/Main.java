import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args)
    {
    Payment konstruktor_attribute_alle_einzahlung = new Payment("15.02.2008" , 1000.0, "Mensageld", 0.05, 0);
    Payment konstruktor_attribute_alle_auszahlung = new Payment("13.03.2020", -1000.0,"abhebung", 0, 0.1);
    Payment copy_payment = new Payment(konstruktor_attribute_alle_einzahlung);
    Payment konstruktor_attribute_3 = new Payment("12.12.20", 300.0, "abhebung2");
    
    Transfer konstruktor_attribute_alle_transfer = new Transfer("12.12.2020", 33.0, "Überweisung Urlaubsgeld", "Weihnachtsmann", "Helfende_Elfe");
    Transfer konstruktor_attribute_3_transfer = new Transfer("13.3.1800", 42.5, "Überweisung Weihnachtsferien");
    Transfer copy_transfer = new Transfer(konstruktor_attribute_alle_transfer);
    
    
    System.out.println("\nObjekt mit allen Attributen Einzahlung (Payment)"); 
    System.out.println( konstruktor_attribute_alle_einzahlung.toString());
    System.out.println("\nObjekt mit allen Attributen Auszahlung (Payment)"); 
    System.out.println( konstruktor_attribute_alle_auszahlung.toString());
    System.out.println("\nObjekt mit allen Attributen mit Copy Konstruktor (Payment)"); 
    System.out.println(copy_payment.toString());
    System.out.println("\nObjekt mit 3 Attributen (Payment)");
    System.out.println(konstruktor_attribute_3.toString());
    
    System.out.println("\n\nObjekt mit allen Attributen (Transfer)"); 
    System.out.println(konstruktor_attribute_alle_transfer.toString());
    System.out.println("\nObjekt mit 3 Attributen (Transfer)");
    System.out.println(konstruktor_attribute_3_transfer.toString());
    System.out.println("\nObjekt mit allen Attributen mit Copy Konstruktor (Transfer)"); 
    System.out.println(copy_transfer.toString());
    
   
}
}
