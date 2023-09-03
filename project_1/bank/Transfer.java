package bank;

//Basisklasse Transfer wird im Kontext von Überweisungen verwendet
public class Transfer {

    //Attribute
    private String date; //Zeitpunkt der Ein- oder Auszahlung
    private String description; //zusätzliche Beschreibung des Vorgangs
    private double amount; //Geldmenge der Ein- oder Auszahlung
    private String sender; //Sender der Geldmenge
    private String recipient; // Empfänger der Geldmenge

    
    //Konstruktoren
    
    //Konstruktor mit 3 Attributwerten der Klasse Transfer
    public Transfer(String date, double amount, String description)
    {
       this.date = date;
       this.amount = amount;
       this.description = description;
    }
    
    //Konstruktor mit allen Attributwerten der Klasse Transfer
    public Transfer(String date, double amount, String description, String sender, String recipient)
    {
        this(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }
    
    //Copy Konstruktor
    public Transfer(Transfer transfer)
    {
        date = transfer.date;
        amount = transfer.amount;
        description = transfer.description;
        sender = transfer.sender;
        recipient = transfer.recipient;
    }
    
    
    //Methoden
   //Getter des Attributs sender
    public String getSender()
    {
       return sender;
    }
    
    //Getter des Attributs recipient
    public String getRecipient()
    {
        return recipient;
    }
    
    //Setter des Attributs sender
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    
    //Setter des Attributs recipient
    public void setRecipient(String recipient)
    {
        this.recipient =  recipient;
    }
    
   
    //Methode zur Ausgabe der Attributwerte eines Objekts
    public void printObject()
    {
    	 if(getSender() == null && getRecipient() == null)
    	 {
    		 System.out.println("Datum: " + date + "  Description: " + description + "  Amount: " + amount );
    	 }
    	 
    	 else {
    	 
        System.out.println("Datum: " + date + "  Description: " + this.description + "  Amount: " + amount
                + "  Sender: " + sender + "  Recipient: " + recipient);
    	 }
    }
  
    
    
}
