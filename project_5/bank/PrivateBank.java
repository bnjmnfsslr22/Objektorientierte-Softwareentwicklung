package bank;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;


/**
 * <p>Struktur von PrivateBank</p>
 *<p>PrivateBank implementiert Bank und definiert die Verwaltung und Verarbeitung von Konten und Transaktionen</p>
 * */
public class PrivateBank implements Bank{
	private static final String path = "C:\\Users\\bfues\\eclipse-workspace\\Praktikum nr.5.2\\jsonFiles";
	/**
 * name ist der Name der privaten Bank
 * incomingInterest ist Gebühr-Einzahlung-Prozent
 * outgoingInterest ist Gebühr-Auszahlung-Prozent
 * accountsToTransactions ist die Verknüpfung zwischen Konten und Transaktionen
 */
	private String name;
	private double incomingInterest;
	private double outgoingInterest;
	private String  directoryName;
	//<String,List <Transaction>> accountsToTransactions;
	Map<String, List <Transaction>> accountsToTransactions = new HashMap<>();

	
	private void readAccounts() throws IOException{
		String[] pathnames;
		File f = new File(path);
		
		pathnames = f.list();
		//List.of(pathnames).forEach(System.out::println);
		for(String filename: pathnames) {
			filename = filename.split("\\.")[0];
			GsonBuilder gsonBuilder = new GsonBuilder(); //erstellen gsonbuilder
	        Path fileName = Path.of(path + "\\"+filename+".json");  //bauen uns unseren path

	        String content = Files.readString(fileName, StandardCharsets.US_ASCII); //Inhalt von Json file rausholen

	        Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType(); //damit wir eine liste von transactions bearbeiten können müssen wir die wieder als type registireren

	        JsonDeserializer<List<Transaction>> deserializer = new ControllerDeserializer(); // Deserializer erstellen
	        gsonBuilder.registerTypeAdapter(listType, deserializer);  //müssen wir wieder registrieren auf unseren gsonbuilder den wir oben gbeaut haben

	        Gson customGson = gsonBuilder.create(); //erstellen aus builder ein gson
	        List<Transaction> yourClassList = customGson.fromJson(content, listType); //damit customgson weiß wie es mit dem content umzugehen hat wird( noch der listtype übergeben(nimmt daten aus json file und verarbeitet anhand listtype typen daten)
	      
	       this.accountsToTransactions.put(filename, yourClassList); //wird in yourclasslist gespeichert
	     
		}
		
	}
	
	
	private void writeAccount(String account)throws IOException{
	        FileWriter writer = new FileWriter(path + "\\" + account + ".json"); //File writer path wo reingeschrieben wird
	        GsonBuilder builder = new GsonBuilder(); //erstellen eines gson builder objekts
	        Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType(); //bauen eines typs für eine array list von transaction, da liste von transaction sonst nicht erkannt wird
	        JsonSerializer<List<Transaction>> serializer = new ControllerSerializer(); //erstellen von serializer aus controller
	        builder.registerTypeAdapter(listType, serializer); //zu dem typen den wir erstellt haben nutze diesen serializer den wir gebaut aben

	        Gson customGson = builder.setPrettyPrinting().disableHtmlEscaping().create(); //erstellen aus builder ein gson, 
	        writer.write(customGson.toJson(accountsToTransactions.get(account), listType)); //wenn listtype bekommt benutzt er serialiezer das weiß erüber register type adapter
	        writer.close(); //wenn file writer aufmacht in java muss man zum schluss wieder closen
		
	}
	
	
	/**
	*Der Name der privaten Bank wird auf den des Paramters gesetzt
    *@param name Name der privaten Bank
    */
	void setName(String name) 
	{
		this.name = name;
	};
	
	/**
	 * Der Einzahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
     * @param incomingInterest Gebühr-Auszahlung-Prozent
     */
	 void setIncomingInterest(double incomingInterest) 
	{
		this.incomingInterest = incomingInterest;
	};
	
	/**
	 * Der Auszahlungs-Prozentsatz der Gebühren wird auf den des Paramaters gesetzt
	 * @param outgoingInterest Gebühr-Einzahlung-Prozent
	 */
	void setOutogingInterest(double outgoingInterest) 
	{
		this.outgoingInterest = outgoingInterest;
	};
	
	/**
	 * Gibt den Namen der privaten Bank zurück
	 * @return name Name der privaten Bank
	 * */
	String getName() 
	{
	return name;
	};
	
	/**
	*Gibt den Prozentsatz der Gebühren einer Einzahlung zurück
    * @return incomingInterest Gebühr-Einzahlung-Prozent
    */
	double getIncomingInterest() 
	{
		return incomingInterest;
	};
	
	/**
	 * Gibt den Prozentsatz der Gebühren einer Auszahlung zurück
	 * @return outgoingInterest Gebühr-Auszahlung-Prozent
	 */
	double getOutogingInterest() 
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
    this.directoryName = "C:\\Users\\bfues\\eclipse-workspace\\Praktikum nr.5.2\\jsonFiles";
    }
	 public void setDirectoryName(String directoryName) {
		 this.directoryName = directoryName;
	 }
	 public String getDirectoryName() {
		 return this.directoryName;
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
		directoryName = privatebank.directoryName;
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
	    return (this.name == obj2.name) &&
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
    	 return("Name: " + name + "  Gebühr-Auszahlung-Prozent: " + outgoingInterest + " Gebühr-Einzahlung-Prozent: " + incomingInterest);
    }
	
	public void createAccount(String account) throws AccountAlreadyExistsException, IOException { 
		
		if (accountsToTransactions.containsKey(account)) {
			throw new AccountAlreadyExistsException("Konto existiert bereits");
		} else {
			accountsToTransactions.put(account,new ArrayList<Transaction>());
			//this.writeAccount(account);
		}
	} 
	
	public void createAccount(String account,List<Transaction>transactions)throws AccountAlreadyExistsException, IOException
	{
		if (accountsToTransactions.containsKey(account)) {
				throw new AccountAlreadyExistsException("Konto existiert bereits");
			}
		else {
				accountsToTransactions.put(account, transactions);
				//this.writeAccount(account);
			}
	}
	
	public void addTransaction(String account, Transaction transaction)throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException
	{
		if(transaction instanceof Payment) 
		{
			((Payment)transaction).setOutgoingInterest(this.outgoingInterest);
			((Payment)transaction).setIncomingInterest(this.incomingInterest);
		}
		if(!accountsToTransactions.containsKey(account)) 
		{
			throw new AccountDoesNotExistException("Konto existiert nicht");
		}
		//System.out.println("compare now");
		if(accountsToTransactions.get(account).contains(transaction)) 
		{
			throw new TransactionAlreadyExistException("Transaction existiert bereits");
		} else {
			//System.out.println("bisherige transactions:");
			
			//accountsToTransactions.get(account).stream().forEach(System.out::println);
			//System.out.println("neue transaction");
			//System.out.println(transaction.toString());
		}
		
		accountsToTransactions.get(account).add(transaction);
		//this.writeAccount(account);
	}
	
	public void removeTransaction(String account, Transaction transaction)  throws TransactionDoesNotExistException, IOException {
		
		if(!accountsToTransactions.get(account).contains(transaction)) {
			throw new TransactionDoesNotExistException("Transaction existiert nicht");
		}
		else {
			accountsToTransactions.get(account).remove(transaction);	
			//this.writeAccount(account);
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
			
			if(transaction instanceof Payment) {
				balance += ((Payment) transaction).calculate();
			}
			if(transaction instanceof OutgoingTransfer) {
				balance+=((OutgoingTransfer)transaction).calculate();
			}
			if(transaction instanceof IncomingTransfer) {
				balance+=((IncomingTransfer)transaction).calculate();
			}
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
		}	
		
		return ergebnis;
	}
	
	public List<Transaction> getTransactionsByType(String account, boolean positive)
	{
		List<Transaction> ergebnis =accountsToTransactions.get(account);
		List<Transaction> newTransactions = new ArrayList<>();
		
		if(positive) {
			for( Transaction s : ergebnis) {
				if(s instanceof IncomingTransfer || (s instanceof Payment && s.getAmount() > 0)) {
					newTransactions.add(s);
				}
			}
			
		} else {
			for(Transaction s : ergebnis) {
				if(s instanceof OutgoingTransfer || (s instanceof Payment && s.getAmount() < 0)) {
					newTransactions.add(s);
				}
					
			}
			
		}
		return newTransactions;		
	}
	public void getDataFromFiles() throws IOException
	{
		//this.readAccounts();
	}
	
	public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
		
		if(!accountsToTransactions.containsKey(account)) {
			throw new AccountDoesNotExistException("Konto existiert nicht");
		}
		else {
			accountsToTransactions.remove(account);
		}
	}
	
	public List<String>getAllAccounts(){
		List<String> allaccounts = new ArrayList<>();
		for (String key : accountsToTransactions.keySet() ) {
		       // System.out.println (key);
		        allaccounts.add(key);
		        }
		return allaccounts;
	}
}

