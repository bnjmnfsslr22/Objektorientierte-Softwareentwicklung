import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args)
    {
    
    Payment konstruktor_attribute_3 = new Payment("18.03.1995" , 1500.0, "Taschengeld");
    Payment konstruktor_attribute_alle = new Payment("15.02.2008" , 200.0, "Mensageld", 0, 0.5);
    Payment copy = new Payment(konstruktor_attribute_alle);
    Payment copy2 = new Payment(konstruktor_attribute_3);
    
    Transfer konstruktor_attribute_alle_transfer = new Transfer("12.12.2020", 33.0, "Überweisung Urlaubsgeld", "Weihnachtsmann", "Helfende_Elfe");
    Transfer konstruktor_attribute_3_transfer = new Transfer("02.03.2021", 65.0, "Überweisung an Freunde");
    Transfer copy_transfer = new Transfer(konstruktor_attribute_3_transfer);
    Transfer copy2_transfer = new Transfer(konstruktor_attribute_alle_transfer);
    
    
    System.out.println("Objekt mit 3 Attributen (Payment)");  
    konstruktor_attribute_3.printObject();
    System.out.println("\nObjekt mit allen Attributen (Payment)"); 
    konstruktor_attribute_alle.printObject();
    System.out.println("\nObjekt mit allen Attributen mit Copy Konstruktor (Payment)"); 
    copy.printObject();
    System.out.println("\nObjekt mit 3 Attributen mit Copy Konstruktor (Payment)"); 
    copy2.printObject();
    
    System.out.println("\n\nObjekt mit 3 Attributen (Transfer)"); 
    konstruktor_attribute_3_transfer.printObject();
    System.out.println("\nObjekt mit allen Attributen (Transfer)"); 
    konstruktor_attribute_alle_transfer.printObject();
    System.out.println("\nObjekt mit allen Attributen mit Copy Konstruktor (Transfer)"); 
    copy2_transfer.printObject();
    System.out.println("\nObjekt mit 3 Attributen mit Copy Konstruktor (Transfer)"); 
    copy_transfer.printObject();
    }
}
