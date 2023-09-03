package bank;

/**
 * <p>Struktur von Payment</p>
 * <p>Payment erbt von der Klasse Transaction und definiert die Ein- und Auszahlung von Beträgen bei der Bank</p>
 * <p>Die Methoden toString und equals wurden implementiert</p>
 * 
 * 
 * @author Benjamin Füßler
 * @version 15.11.2021 
 */


public class Payment extends Transaction {

   /**
    *incomingInterest ist Gebühr-Einzahlung-Prozent
    *outgoingInterest ist Gebühr-Auszahlung-Prozent
    */
    private double incomingInterest; 
    private double outgoingInterest;  
    
    
    public Payment(String date, double amount, String description)
    {
    	super(date,amount,description);
    }
    
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest)
    {
        super(date, amount, description);
        this.incomingInterest= incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    
    public Payment(Payment payment)
    { 	
    date = payment.date;
    amount = payment.amount;
    description = payment.description;
    incomingInterest = payment.incomingInterest;
    outgoingInterest = payment.outgoingInterest;
    }

    /**
     * Gibt den Prozentsatz der Gebühren einer Einzahlung zurück
     * @return incomingInterest Gebühr-Einzahlung-Prozent
     */
    public double getIncomingInterest()
    {
    return incomingInterest;
    }
    
    /**
     * Gibt den Prozentsatz der Gebühren einer Auszahlung zurück
     * @return outgoingInterest Gebühr-Auszahlung-Prozent
     */
    public double getOutgoingInterest()
    {
    return outgoingInterest;
    }
  
    /**
     * Der Einzahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
     * @param incomingInterest Gebühr-Auszahlung-Prozent
     */
    public void setIncomingInterest(double incomingInterest)
    {
        this.incomingInterest = incomingInterest;
    }
    
   /**
    * Der Auszahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
    * @param outgoingInterest Gebühr-Einzahlung-Prozent
    */
    public void setOutgoingInterest(double outgoingInterest)
    {
        this.outgoingInterest = outgoingInterest;
    }
    
    /**
     * Standardmethode
     * Das aktuelle Objekt wird in einen String umgewandelt
     * @return  String: Datum = date
	 *                  Beschreibung = description
	 *                  Betrag = amount
	 *                  Gebühr-Einzahlung-Prozent = incomingInterest
	 *                  Gebühr-Auszahlung-Prozent = outgoingInterest
     */
    @Override
    public String toString()
    {
    	 return("Datum: " + date + "  Description: " + description + "  Amount: " + this.calculate()
                + "  Gebühr-Auszahlung-Prozent: " + outgoingInterest + " Gebühr-Einzahlung-Prozent: " + incomingInterest);
    	 
    }
    
    /**
     * Standardmethode
     * @param obj Benutzer liefert das Objekt, dessen Inhalte mit dem aktuellen Objekt verglichen werden sollen
	 * @return true gleich oder false ungleich
     */
    @Override
    public boolean equals(Object obj)
    {
    	if (obj instanceof Payment) {
    		Payment obj2 = (Payment)obj;
    		return (super.equals(obj2)) &&
    			(this.incomingInterest == obj2.incomingInterest) &&
    			(this.outgoingInterest == obj2.outgoingInterest);
    	}
    	else {
    		return false;
    	}
    }

    /**
     * Gibt den berechneten Gesamtbetrag inklusive verrechnete Gebühr bei Einzahlung oder Auszahlung aus
     * @return  Zahl >= 0 bei Einzahlung und Zahl < 0 bei Auszahlung 
     */
     public double calculate()
     {
    	 if (this.amount >= 0)
    	 {
    	
    		 return (this.amount - this.incomingInterest * this.amount);
    	 }
    		
    	 return (this.amount + this.outgoingInterest * this.amount);
    	 
    	}
     
     
     
     
      
}
