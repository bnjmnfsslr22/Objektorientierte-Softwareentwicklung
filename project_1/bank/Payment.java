package bank;

//Basisklasse Payment repräsentiert Ein- und Auszahlungen
public class Payment {

    //Attribute
    private String date; //Zeitpunkt der Ein- oder Auszahlung
    private String description; //zusätzliche Beschreibung des Vorgangs
    private double amount; //Geldmenge der Ein- oder Auszahlung
    private double incomingInterest; // Zinsen, die bei einer Einzahlung anfallen
    private double outgoingInterest; //Zinsen, die bei einer Auszahlung anfallen 

    
    //Konstruktoren
    
    //Konstruktor mit 3 Attributwerten der Klasse Payment
    public Payment(String date, double amount, String description)
    {
        this.date = date; 
        this.amount = amount;
        this.description = description;
    }
    
    //Konstruktor mit allen Attributwerten der Klasse Payment
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest)
    {
        this(date, amount, description);
        this.incomingInterest= incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }
    
    //Copy Konstruktor
    public Payment(Payment payment)
    {
    date = payment.date;
    amount = payment.amount;
    description = payment.description;
    incomingInterest= payment.incomingInterest;
    outgoingInterest = payment.outgoingInterest;
    }

    
    //Methoden
    
    //Getter des Attributs date
    public String getDate()
    {
    return date;
    }
    
    //Getter des Attributs description
    public String getDescription()
    {
    return description;
    }
    
    //Getter des Attributs description
    public double getAmount()
    {
    return amount;
    }
    
    //Getter des Attributs incomingInterest
    public double getIncomingInterest()
    {
    return incomingInterest;
    }
    
    //Getter des Attributs outgoingInterest
    public double getOutgoingInterest()
    {
    return outgoingInterest;
    }
    
    //Setter des Attributs date
    public void setDate(String date)
    {
        this.date =  date;
    }
    
    //Setter des Attributs description
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    //Setter des Attributs amount
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    //Setter des Attributs incomingInterest
    public void setIncomingInterest(double incomingInterest)
    {
        this.incomingInterest = incomingInterest;
    }
    
    //Setter des Attributs outgoingInterest
    public void setOutgoingInterest(double outgoingInterest)
    {
        this.outgoingInterest = outgoingInterest;
    }

    //Methode zur Ausgabe der Attributwerte eines Objekts
     public void printObject()
    {
    	 if(getIncomingInterest() == 0 && getOutgoingInterest() == 0)
    	 {
    		 System.out.println("Datum: " + date + "  Description: " + description + "  Amount: " + amount );
    	 }
    	 
    	 else {
    	 System.out.println("Datum: " + date + "  Description: " + description + "  Amount: " + amount
                + "  outgoing: " + outgoingInterest + "  incoming: " + incomingInterest);
    	 }
    }
}
