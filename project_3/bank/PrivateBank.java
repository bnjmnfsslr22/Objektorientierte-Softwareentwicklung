package bank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;


/**
 * <p>Struktur von PrivateBank</p>
 *<p>PrivateBank implementiert Bank und definiert die Verwaltung und Verarbeitung von Konten und Transaktionen</p>
 * */
public class PrivateBank implements Bank{

	/**
 * name ist der Name der privaten Bank
 * incomingInterest ist Gebühr-Einzahlung-Prozent
 * outgoingInterest ist Gebühr-Auszahlung-Prozent
 * accountsToTransactions ist die Verknüpfung zwischen Konten und Transaktionen
 */
	private String name;
	private double incomingInterest;
	private double outgoingInterest;
	//<String,List <Transaction>> accountsToTransactions;
	private Map<String, List <Transaction>> accountsToTransactions = new HashMap<>();

	
	/**
	*Der Name der privaten Bank wird auf den des Paramters gesetzt
    *@param name Name der privaten Bank
    */
	public void setName(String name) 
	{
		this.name = name;
	};
	
	/**
	 * Der Einzahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
     * @param incomingInterest Gebühr-Auszahlung-Prozent
     */
	public void setIncomingInterest(double incomingInterest) 
	{
		this.incomingInterest = incomingInterest;
	};
	
	/**
	 * Der Auszahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
	 * @param outgoingInterest Gebühr-Einzahlung-Prozent
	 */
	public void setOutogingInterest(double outgoingInterest) 
	{
		this.outgoingInterest = outgoingInterest;
	};
	
	/**
	 * Gibt den Namen der privaten Bank zurück
	 * @return name Name der privaten Bank
	 * */
	public String getName() 
	{
	return name;
	};
	
	/**
	*Gibt den Prozentsatz der Gebühren einer Einzahlung zurück
    * @return incomingInterest Gebühr-Einzahlung-Prozent
    */
	public double getIncomingInterest() 
	{
		return incomingInterest;
	};
	
	/**
	 * Gibt den Prozentsatz der Gebühren einer Auszahlung zurück
	 * @return outgoingInterest Gebühr-Auszahlung-Prozent
	 */
	public double getOutogingInterest() 
	{ 
		return outgoingInterest;
	};
	
	/**
	 * Erstellt ein Objekt vom Typ PrivateBank
	 * @param name Name der privaten Bank
	 * @param incomingInterest Gebühr-Einzahlung-Prozent
	 * @param outgoingInterest Gebühr-Auszahlung-Prozent
	 */
	public PrivateBank (String name, double incomingInterest, double outgoingInterest) 
	{
	this.name = name;
	this.incomingInterest = incomingInterest;
    this.outgoingInterest = outgoingInterest;
    }
	
	 /**
	 * kopiert ein bereits vorhandenes Objekt vom Typ PrivateBank
	 * @param privatebank
	 */
	 public PrivateBank(PrivateBank privatebank)
	{
		name = privatebank.name;
		incomingInterest = privatebank.incomingInterest;
		outgoingInterest = privatebank.outgoingInterest;
	}
	 
	 /**
	  * Standardmethode
	  * @param obj Benutzer liefert das Objekt, dessen Inhalte mit dem aktuellen Objekt verglichen werden sollen
	  * @return true gleich oder false ungleich
	   */
	 @Override
    public boolean equals(Object obj)
    {
	   if(obj instanceof PrivateBank) {
	    PrivateBank obj2 = (PrivateBank)obj;
	    return (this.name.equals(obj2.name)) &&
    			(this.outgoingInterest == obj2.outgoingInterest) &&
    			(this.incomingInterest== obj2.incomingInterest);
	   }
	   else {
		   return false;
	   }
    }
	 /**
	     * Standardmethode
	     * Das aktuelle Objekt wird in einen String umgewandelt
	     * @return  String: Name = name
		 *                  Gebühr-Einzahlung-Prozent = incomingInterest
		 *                  Gebühr-Auszahlung-Prozent = outgoingInterest
	     */
	@Override
    public String toString()
    {
    	 return("Name: " + name + " Gebühr-Einzahlung-Prozent: " + incomingInterest  + " Gebühr-Auszahlung-Prozent: " + outgoingInterest);
    }
	
	public void createAccount(String account) throws AccountAlreadyExistsException { 
		if (accountsToTransactions.containsKey(account)) {
			throw new AccountAlreadyExistsException("Konto existiert bereits");
		} else {
			accountsToTransactions.put(account,new ArrayList<Transaction>());
		}
	} 
	
	public void createAccount(String account,List<Transaction>transactions)throws AccountAlreadyExistsException
	{
		if (accountsToTransactions.containsKey(account)) {
				throw new AccountAlreadyExistsException("Konto existiert bereits");
			}
		else {
				accountsToTransactions.put(account, transactions);
			}
	}
	
	public void addTransaction(String account, Transaction transaction)throws TransactionAlreadyExistException, AccountDoesNotExistException
	{

		if(!accountsToTransactions.containsKey(account)) 
		{
			throw new AccountDoesNotExistException("Konto existiert nicht");
		}
		if(accountsToTransactions.get(account).contains(transaction)) 
		{
			throw new TransactionAlreadyExistException("Transaction existiert bereits");
		}
		if(transaction instanceof Payment) 
		{
			((Payment)transaction).setOutgoingInterest(this.outgoingInterest);
			((Payment)transaction).setIncomingInterest(this.incomingInterest);
		}
		accountsToTransactions.get(account).add(transaction);
	}
	
	public void removeTransaction(String account, Transaction transaction)  throws TransactionDoesNotExistException {
		
		if(!accountsToTransactions.get(account).contains(transaction)) {
			throw new TransactionDoesNotExistException("Transaction existiert nicht");
		}
		else {
			accountsToTransactions.get(account).remove(transaction);	
		}
			
	}
		
	public boolean containsTransaction(String account, Transaction transaction) {
		return accountsToTransactions.get(account).contains(transaction);
	}
	
	
	public double getAccountBalance(String account) 
	{
		List<Transaction> transactions = accountsToTransactions.get(account);
		
		double balance = 0;
	
		for(Transaction transaction : transactions) {
			balance += transaction.calculate();
			
		} 
		return balance;
	}
	
	public List<Transaction> getTransactions(String account)
	{
		return accountsToTransactions.get(account);
	}
	
	public List<Transaction> getTransactionsSorted(String account, boolean asc)
	{
		List<Transaction> ergebnis = accountsToTransactions.get(account);
	    ergebnis.sort(new TransactionComparator());
		if(!asc)
		{
			Collections.reverse(ergebnis);
			//Collections.sort(ergebnis, new TransactionComparator());
		}	
		
		return ergebnis;
	}
	
	public List<Transaction> getTransactionsByType(String account, boolean positive)
	{
		List<Transaction> ergebnis = accountsToTransactions.get(account);
		List<Transaction> newTransactions = new ArrayList<>();
		
		if(positive) {
			for( Transaction s : ergebnis) {
				if(s instanceof IncomingTransfer || (s instanceof Payment && s.getAmount()>0)) {
					newTransactions.add(s);
				}
			}
			
		} else {
			for(Transaction s : ergebnis) {
				if(s instanceof OutgoingTransfer || (s instanceof Payment && s.getAmount()<0)) {
					newTransactions.add(s);
				}
					
			}
			
		}
		return newTransactions;		
	}
	
	public List<String>getAllAccounts(){
		List<String> allaccounts = new ArrayList<>();
		for (String key : accountsToTransactions.keySet() ) {
		        System.out.println (key);
		        allaccounts.add(key);
		        }
		return allaccounts;
		}
	
	public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
		
		if(!accountsToTransactions.containsKey(account)) {
			throw new AccountDoesNotExistException("Konto existiert nicht");
		}
		else {
			accountsToTransactions.remove(account);
		}
	}
	/*
	public double getAccountBalance(String account) 
	{
		List<Transaction> transactions = accountsToTransactions.get(account);
		double balance = 0;
		for(Transaction transaction : transactions) {
			if(transaction.)
		}
		
	}*/
	
	
	/*
			if(transaction instanceof Payment) {
				balance -= ((Payment) transaction).calculate();
			}
			if(transaction instanceof OutgoingTransfer) {
				balance+=((OutgoingTransfer)transaction).calculate();
			}
			if(transaction instanceof IncomingTransfer) {
				balance-=((IncomingTransfer)transaction).calculate();
			}*/
	
	
	
	
	
	
	
	
	
	
	
	/*
	public List<Transaction> getTransactionsByType(String account, boolean positive)
	{
		List<Transaction> ergebnis =accountsToTransactions.get(account);
		List<Transaction> newTransactions = new ArrayList<>();
		
		if(positive) {
			for( Transaction s : ergebnis) {
				if(s instanceof IncomingTransfer) {
					newTransactions.add(s);
				}
			}
			
		} else {
			for(Transaction s : ergebnis) {
				if(s instanceof OutgoingTransfer || s instanceof Payment) {
					newTransactions.add(s);
				}
					
			}
			
		}
		return newTransactions;		
	} */
	
}
