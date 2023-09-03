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

import bank.json.Deserialize;
import bank.json.Serialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
	private static final String path = "C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles";
	/**
 * name ist der Name der privaten Bank
 * incomingInterest ist Gebühr-Einzahlung-Prozent
 * outgoingInterest ist Gebühr-Auszahlung-Prozent
 * directoryName ist der Speicherort des Kontos
 * accountsToTransactions ist die Verknüpfung zwischen Konten und Transaktionen
 */
	private String name;
	private double incomingInterest;
	private double outgoingInterest;
	private String  directoryName;
	//<String,List <Transaction>> accountsToTransactions;
	Map<String, List <Transaction>> accountsToTransactions = new HashMap<>();

	
	/**
	 * Holt den Inhalt aus dem json file und wendet darauf den Deserializer an
	 * @throws IOException
	 */
	
	private void readAccounts() throws IOException{
		String[] pathnames;
		File f = new File(path);
		
		pathnames = f.list();
		//List.of(pathnames).forEach(System.out::println);
		for(String filename: pathnames) {
			filename = filename.split("\\.")[0]; //benjamin.json -> {benjamin,json} daraus das 0te  (benjamin)
			
			GsonBuilder gsonBuilder = new GsonBuilder(); //erstellen gsonbuilder
	        Path fileName = Path.of(path + "\\"+filename+".json");  //bauen uns unseren Path da path ncht den jsonnamen enthält

	        String content = Files.readString(fileName, StandardCharsets.US_ASCII); //Inhalt von Json file rausholen mit loop 

	        Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType(); //damit wir eine liste von transactions bearbeiten können müssen wir die wieder als type registireren

	        JsonDeserializer<List<Transaction>> deserializer = new ControllerDeserializer(); // Deserializer erstellen
	        gsonBuilder.registerTypeAdapter(listType, deserializer);  //müssen wir wieder registrieren auf unseren gsonbuilder den wir oben gbeaut haben

	        Gson customGson = gsonBuilder.create(); //erstellen aus builder ein gson
	        List<Transaction> yourClassList = customGson.fromJson(content, listType); //damit customgson weiß wie es mit dem content umzugehen hat wird( noch der listtype übergeben(nimmt daten aus json file und verarbeitet anhand listtype typen daten)
	      
	       this.accountsToTransactions.put(filename, yourClassList); //wird in yourclasslist gespeichert
	     
		}
		
	}
	
	/**
	 * ruft den Serializer auf und schreibt mit dem Filewriter den inhalt in ein json File
	 * @param account name(key) des accounts
	 * @throws IOException
	 */
	
	
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
	
/*
	
	private void writeAccount(String account) throws IOException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(IncomingTransfer.class, new Serialize())
                .registerTypeAdapter(Payment.class, new Serialize())
                .registerTypeAdapter(OutgoingTransfer.class, new Serialize())
                .setPrettyPrinting()
                .create();

        List<Transaction> ListofTransactions = getTransactions(account);
        String komplett = gson.toJson(ListofTransactions);
        FileWriter fw = new FileWriter("C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles" + this.directoryName + "\\" + account + ".json");
        fw.write(komplett); // serelized String im Datei einfügen
        fw.close(); // Datei schliessen
}*/

/**
 * Reads all Accounts from the PrivateBank -> Directory and saves them in
 * accountsToTransactions
 * @throws IOException
 */
	
	/*
private void readAccounts() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(IncomingTransfer.class, new Deserialize())
                .registerTypeAdapter(OutgoingTransfer.class, new Deserialize())
                .registerTypeAdapter(Payment.class, new Deserialize())
                .setPrettyPrinting()
                .create();

        File file = new File("C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles" + this.directoryName);
        File[] fileArray = file.listFiles();// Array of Files
        for (File f : fileArray) { // für jeden File ::
                String content = Files.readString(f.toPath()); // wir speichern den Inhalt in einem String "content"
                String filename = f.getName();
                int ende = filename.length() - 5; // damit wir die .json ignorieren
                String key = filename.substring(0, ende); //der richtige Name(Name von account)
                List<Transaction> transaktionen = new ArrayList<Transaction>(); // die einzufügenden Transaktionen

                JsonArray t_neu = gson.fromJson(content, JsonArray.class);
                for (JsonElement JE : t_neu)
                {
                        JsonObject JO = JE.getAsJsonObject();
                        String classname = JO.get("CLASSNAME").getAsString();
                        if(classname.equals("IncomingTransfer"))
                        {
                                IncomingTransfer it = gson.fromJson(JO, IncomingTransfer.class);
                                transaktionen.add(it); // fügen das Element die Transaktionen ein
                        }
                        else if(classname.equals("OutgoingTransfer"))
                        {
                                OutgoingTransfer ot = gson.fromJson(JO, OutgoingTransfer.class);
                                transaktionen.add(ot);
                        }
                        else if (classname.equals("Payment"))
                        {
                                Payment p = gson.fromJson(JO, Payment.class);
                                transaktionen.add(p);
                        }
                }
                this.accountsToTransactions.put(key, transaktionen);
        }
}
	*/
	
	
	
	
	
	
	
	
	
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
    String path = "C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles\\";
    
    
    
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
		//this.directoryName = "C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles\\";
		
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
			this.writeAccount(account);
			
		}
	} 
	
	public void createAccount(String account,List<Transaction>transactions)throws AccountAlreadyExistsException, IOException
	{
		if (accountsToTransactions.containsKey(account)) {
				throw new AccountAlreadyExistsException("Konto existiert bereits");
			}
		else {
				accountsToTransactions.put(account, transactions);
				this.writeAccount(account);
				}
	}
	
	public void addTransaction(String account, Transaction transaction)throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException
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
		
			//this.writeAccount(account);
		try {
            writeAccount(account);
    }
    catch (IOException e)
    {
            System.out.println(e);
    }
		
	}
	
	public void removeTransaction(String account, Transaction transaction)  throws TransactionDoesNotExistException, IOException {
		
		if(!accountsToTransactions.get(account).contains(transaction)) {
			throw new TransactionDoesNotExistException("Transaction existiert nicht");
		}
		else {
			accountsToTransactions.get(account).remove(transaction);	
			this.writeAccount(account);
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
				balance -= ((Payment) transaction).calculate();
			}
			if(transaction instanceof OutgoingTransfer) {
				balance+=((OutgoingTransfer)transaction).calculate();
			}
			if(transaction instanceof IncomingTransfer) {
				balance-=((IncomingTransfer)transaction).calculate();
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
		List<Transaction> ergebnis = accountsToTransactions.get(account);
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
	}
	public void getDataFromFiles() throws IOException
	{
		this.readAccounts();
	}
	
	
	
	
	public static void main(String[] args) throws IOException, AccountAlreadyExistsException, TransactionAlreadyExistException{
		
		IncomingTransfer E = new IncomingTransfer("22.12.2020",20, "test2", "mann1", "mann2");
		IncomingTransfer F = new IncomingTransfer("21.12.2020",20, "test2", "mann1", "mann2");
		 PrivateBank konstruktor_incoming = new PrivateBank("ing diba", 0.5, 0);
		 //konstruktor_incoming.readAccounts();
		 //konstruktor_incoming.getTransactions("PersistenceSample").forEach(System.out::println);
		 List<Transaction> transactions = new ArrayList<>();
		 transactions.add(E);
		 transactions.add(F);
		 konstruktor_incoming.createAccount("Benjamin", transactions);
		 konstruktor_incoming.writeAccount("Benjamin");
		 }
	
	
	
	
	
	
	
	
	
	/*
	PrivateBank pBank2 = new PrivateBank("private_bank_2", 0.5, 0.5, "BBBank");


    pBank2.createAccount("Hassen");
    IncomingTransfer it1 = new IncomingTransfer( "04.12.21", 100, "description_1","Hassen", "Dali");
    OutgoingTransfer ot1 = new OutgoingTransfer( "05.12.21", 100, "description_2","Dali", "Hassen");
    Payment p1 = new Payment( "03.12.21", -100,"payment_1",0.1, 0.1);
    Payment p2 = new Payment( "02.12.21", 100, "payment_2",0.1, 0.1);
    pBank2.addTransaction("Hassen", it1);
    pBank2.addTransaction("Hassen", ot1);
*/
	}
			


