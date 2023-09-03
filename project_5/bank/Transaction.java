package bank;

/**
 * <p>Struktur von Transaction</p>
 * <p>Transaction definiert die Transaktionen einer Bank</p>
 * <p>Die Methoden toString und equals wurden implementiert</p>
 *
 *
 * @author Benjamin Füßler
 * @version 15.11.2021 
 */

public abstract class Transaction  {
	
	/**
	 * date ist Zeitpunkt der Transaktion
	 * description ist Beschreibung der Transaktion
	 * amount ist Betrag der Transaktion
	 * */
	    protected String date; 
	    protected String description; 
	    protected double amount; 
	    
	   
	    public Transaction(){
	    	}
	    
	    public Transaction(String date, double amount, String description)
	    {
	       this.date = date;
	       this.amount = amount;
	       this.description = description;
	    }
	    
	    /**
	     * Gibt das Datum der Transaktion zurück
	     * @return Datum
	     */
	    public String getDate()
	    {
	    return date;
	    }
	    
	    /**
	     * Gibt die Beschreibung der Transaktion zurück
	     * @return Beschreibung
	     */
	   public String getDescription()
	    {
	    return description;
	    }
	    
	   /**
	    * Gibt den Betrag der Transaktion zurück
	    * @return Betrag
	    */
	    public double getAmount()
	    {
	    return amount;
	    }
	    
	   /**
	    * Das Datum wird geändert durch den Parameter
	    * @param date Datum
	    */
	    public void setDate(String date)
	    {
	        this.date =  date;
	    }
	    
	    /**
	     * Die Beschreibung wird geändert durch den Paramter
	     * @param description
	     */
	    public void setDescription(String description)
	    {
	        this.description =  description;
	    }
	    
	    /**
	     * Der Betrag wird geändert durch den Parameter
	     * @param amount
	     */
	   public void setAmount(double amount)
	    {
	        this.amount = amount;
	    }
	    
	   /**
	    * Standardmethode
	    * Das aktuelle Objekt wird in einen String umgewandelt
	    * @return  String: Datum = date
	    *                  Beschreibung = description
	    *                  Betrag = amount
	    */
	    @Override
	    public String toString()
	    {
	    	return("Datum: " + date + " Beschreibung: " + description + "  Betrag: " + amount );
	    }
	   
	    Transaction obj1;
	    Object obj =  obj1;
	    
	    /**
	     * Standardmethode
	     * @param obj Benutzer liefert das Objekt, dessen Inhalte mit dem aktuellen Objekt verglichen werden sollen
	     * @return true gleich oder false ungleich
	     */
	    @Override
	    public boolean equals(Object obj)
	    {
	    	if(obj instanceof Transaction) {
	    	 Transaction obj2 = (Transaction)obj;
	    	return (this.date.equals(obj2.date))&&
	    			(this.description.equals(obj2.description)) &&
	    			(this.amount == obj2.amount);
	    	}else {
	    		System.out.println("obj is of type " + obj.getClass() + ", && has values " + obj.toString());
	    		return false;
	    	}
	    
	    }
}
