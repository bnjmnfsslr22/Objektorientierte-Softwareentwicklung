package bank;

/**
 * <p>Struktur von Transfer</p>
 * <p>Transfer erbt von der Klasse Transaction und definiert die Transferierung von Betr�gen einer Bank</p>
 * <p>Die Methoden toString und equals wurden implementiert</p>
 * 
 * 
 * @author Benjamin F��ler
 * @version 15.11.2021 
 */


public class Transfer extends Transaction {

   /**
    * sender ist der Sender des Transfers
    * recipient ist der Empf�nger des Transfers
    * */
    private String sender; 
    private String recipient; 

    
    public Transfer(String date, double amount, String description)
    {
    	super(date, amount, description);
    }
    
    public Transfer(String date, double amount, String description, String sender, String recipient)
    {
        super(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }
    
    public Transfer(Transfer transfer)
    {
    	
        date = transfer.date;
        amount = transfer.amount;
        description = transfer.description;
        sender = transfer.sender;
        recipient = transfer.recipient;
    }  
    
    /**
     * Gibt den Sender zur�ck
     * @return Sender
     */
    public String getSender()
    {
       return sender;
    }
    
    /**
     * Gibt den Empf�nger zur�ck
     * @return Empf�nger
     */
    public String getRecipient()
    {
        return recipient;
    }
   
    /**
     * Der Sender wird ge�ndert auf den Parameter
     * @param sender Sender
     */
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    
   /**
    * Der Empf�nger wird ge�ndert auf den Parameter
    * @param recipient Empf�nger
    */
    public void setRecipient(String recipient)
    {
        this.recipient =  recipient;
    }
    
    /**
     * Standardmethode
     * Das aktuelle Objekt wird in einen String umgewandelt
     * @return  String: Datum = date
	 *                  Beschreibung = description
	 *                  Betrag = amount
	 *                  Sender = sender
	 *                  Empf�nger = recipient
     */
    @Override
    public String toString()
    {
    return("Datum: " + date + "  Description: " + description + "  Amount: " + this.calculate()
               + "  Sender: " + sender + "  Recipient: " + recipient);
    }
    
    /**
     * Standardmethode
     * @param obj Benutzer liefert das Objekt, dessen Inhalte mit dem aktuellen Objekt verglichen werden sollen
	 * @return true gleich oder false ungleich
     */
   @Override
    public boolean equals(Object obj)
    {
	   if(obj instanceof Transfer) {
	    Transfer obj2 = (Transfer)obj;
	    
	    	return (super.equals(obj2)) &&
    			(this.sender == obj2.sender) &&
    			(this.recipient== obj2.recipient);
	   }else
	   {
		   return false;
	   }
    }

    /**
     * Gibt unver�nderten Betrag zur�ck
     * @return amount Betrag
     */
   public double calculate()
   {
	   return amount;
   }
    
}
