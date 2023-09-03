import bank.PrivateBank;
import bank.PrivateBankAlt;
import bank.exceptions.AccountAlreadyExistsException;
import bank.Transfer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.Transaction;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

public class Main {
    public static void main(String[] args) throws AccountAlreadyExistsException, AccountDoesNotExistException, IOException
    {
    	Transfer A = new Transfer("22.12.2021", 55,"test", "mann1", "mann2");
    	Transfer B =  new Transfer(A);
    	IncomingTransfer C = new IncomingTransfer("22.12.2021", 65,"test", "mann1", "mann2");
    	Transfer E = new Transfer("22.12.2020",20, "test2", "Lukas", "mann2");
    	Transfer G = new Transfer("01.01.1997",15,"test3","mensch4","Lukas");
        Payment D = new Payment("1.1.2020", 700,"hallo", 0.1 , 0 );
        Payment F = new Payment ("2.02.2020",-500, "taschengeld", 0, 0.1);
        IncomingTransfer I = new IncomingTransfer("02.02.1997", 200, "taschengeld2", "lara" , "sven");
        OutgoingTransfer J = new OutgoingTransfer("15.04.1996", 20, "urlaub", "sven", "lara");
       
   
    PrivateBankAlt bankalt = new PrivateBankAlt("alte bank", 0.2, 0.3);
    PrivateBank bank1 = new PrivateBank("ing diba", 0.5, 0.4);
    PrivateBank konstruktor_outgoing = new PrivateBank("commerz", 0.2, 1.4);
    PrivateBank copy_incoming = new PrivateBank(bank1);
    PrivateBank copy_outgoing = new PrivateBank(konstruktor_outgoing);
    
    
    System.out.println(" (PrivateBankAlt) Account Lukas sendet 20 und empfängt 15 mit Transaktion Transfer");
    try {
    bankalt.createAccount("Lukas");
    bankalt.addTransaction("Lukas", E);
    bankalt.addTransaction("Lukas", G);
    }catch(AccountAlreadyExistsException e) {
    	System.out.println(e);
    } catch (TransactionAlreadyExistException e) {
		System.out.println(e);
	} catch (AccountDoesNotExistException e) {
		System.out.println(e);
	}
    System.out.println(bankalt.getTransactions("Lukas"));
    System.out.println("Acount Balance Erwartungswert: -5.0 (da 15-20 = -5) \nErgebniswert:");
    System.out.println(bankalt.getAccountBalance("Lukas"));
    
    System.out.println(" \n(PrivateBank) Account Sven sendet 20 mit OutgoingTransfer und empfängt 200 mit Transaktion IncomingTransfer");
    try {
    bank1.createAccount("Sven");
    bank1.addTransaction("Sven", I);
    bank1.addTransaction("Sven", J);
    }catch(AccountAlreadyExistsException e) {
    	System.out.println(e);
    } catch (TransactionAlreadyExistException e) {
		System.out.println(e);
	} catch (AccountDoesNotExistException e) {
		System.out.println(e);
	}
    System.out.println(bank1.getTransactions("Sven"));
    System.out.println("Acount Balance Erwartungswert: 180 (da 200-20 = 180) \nErgebniswert:");
    System.out.println(bank1.getAccountBalance("Sven"));
     
    System.out.println("\nObjekt1 (PrivateBank)"); 
    System.out.println("Erwartete Werte:  \nName: ing diba  Gebühr-Einzahlung-Prozent: 0.5 Gebühr-Auszahlung-Prozent: 0.4 \nErgebniswerte:");
    System.out.println( bank1.toString());
    System.out.println("\nObjekt2  (PrivateBank)"); 
    System.out.println("Erwartete Werte: \nName: commerz Gebühr-Einzahlung-Prozent: 0.2 Gebühr-Auszahlung-Prozent: 1.4 \nErgebniswerte:");
    System.out.println( konstruktor_outgoing.toString());
    System.out.println("\nObjekt1  Copy Konstruktor (PrivateBank)"); 
    System.out.println("Erwartete Werte: \nName: ing diba, Gebühr-Einzahlung-Prozent: 0.5 Gebühr-Auszahlung-Prozent: 0.4 \nErgebniswerte: ");
    System.out.println(copy_incoming.toString());
    System.out.println("\nObjekt2  Copy Konstruktor (PrivateBank)");
    System.out.println("Erwartete Werte: \nName: commerz, Gebühr-Einzahlung-Prozent: 0.2 Gebühr-Auszahlung-Prozent: 1.4 \nErgebniswerte:");
    System.out.println(copy_outgoing.toString());
    System.out.println("\nVergleich von Objekt1 und Objekt2 (test von equals)");
    System.out.println("Ewarteter Wert: \nfalse \nErgebniswert: ");
    System.out.println(bank1.equals(konstruktor_outgoing));
    
    System.out.println("\nVergleich von Objekt2 mit sich selbst (test von equals)");
    System.out.println("Erwarteter Wert: \ntrue \nErgebniswert: ");
    System.out.println(konstruktor_outgoing.equals(konstruktor_outgoing));
    
    try {
    	konstruktor_outgoing.createAccount("account2", Arrays.asList(new Transfer[] {A, B, C}));
    }catch(AccountAlreadyExistsException e) {
    	System.out.println(e);
    }
    System.out.println("\nErstellter Account account2 mit 3 Transaktionen vom Typ Transfer(Test von getTransactions und createAccount)");
    System.out.println("Erwartete Werte: \n[Datum: 22.12.2021  Description: test  Amount: 55.0  Sender: mann1  Recipient: mann2, Datum: 22.12.2021  Description: test  Amount: 55.0  Sender: mann1  Recipient: mann2, Datum: 22.12.2021  Description: test  Amount: 65.0  Sender: mann1  Recipient: mann2] ");
    System.out.println("Ergebniswerte:");
    System.out.println(konstruktor_outgoing.getTransactions("account2"));
    System.out.println("\nTransactions werden absteigend nach amount sortiert (Test getTransactionsSorted)");
    System.out.println(konstruktor_outgoing.getTransactionsSorted("account2", false));
   
    System.out.println("\nHinzufügen einer Payment Einzahlung Transaktion zu einem leeren Account acount1(Test von addTransaction und getTransactions)");
    try {
		bank1.createAccount("account1");
	} catch (AccountAlreadyExistsException e) {
		System.out.println(e);
	}
    try {
    	bank1.addTransaction("account1", D);
    	} 
    catch(AccountDoesNotExistException e) {
    	System.out.println(e);
    }
    catch(TransactionAlreadyExistException e) {
    	System.out.println(e);
    }
    System.out.println("Erwarteter Wert: \nDatum: 1.1.2020  Description: hallo  Amount: 350.0  Gebühr-Auszahlung-Prozent: 0.4 Gebühr-Einzahlung-Prozent: 0.5 \nErgebniswert:");
    System.out.println(bank1.getTransactions("account1"));
    try {
    	bank1.addTransaction("account1", F);
    }catch(AccountDoesNotExistException e) {
    	System.out.println(e);
    }
    catch(TransactionAlreadyExistException e) {
    	System.out.println(e);
    }
    System.out.println("\nNur die negativen Transaktionen bei acc1 (Test getTransactionsByType)");
    System.out.println(bank1.getTransactionsByType("account1", false));
    System.out.println();
    System.out.println("Nur die positiven Transaktionen bei acc1 (Test getTransactionsByType)");
    System.out.println(bank1.getTransactionsByType("account1", true));
    System.out.println("\nDer Kontostand von acc1 (nur payment einzahlungen)(Test getAccountBalance) \nErwarteter Wert: \n-350\nErgebniswert:");
    System.out.println(bank1.getAccountBalance("account1"));
    
    
    
    System.out.println("\nEntfernen der hinzugefügten Payment Transaktion D (Test removeTransaction)");
    try {
    	bank1.removeTransaction("account1", D);
    }catch(TransactionDoesNotExistException e) {
    	System.out.println(e);
    }
    System.out.println ("Überprüfen ob Transaction D noch vorhanden (Test containsTransaction) \nErwarteter Wert: \nfalse\n Ergebniswert:");
    System.out.println(bank1.containsTransaction("account1", D));
  
    
    System.out.println("Hall");
    bank1.getAllAccounts();
    System.out.println("Hall2");
    bank1.deleteAccount("account1");
    bank1.getAllAccounts();
    System.out.println(bank1.getTransactions("account1"));
    System.out.println(bank1.getTransactions("Sven"));
   
  
    
    /*
    try {
		konstruktor_incoming.createAccount("account1");
	} catch (AccountAlreadyExistsException e) {
		System.out.println(e);
	}
    
    try {
		konstruktor_incoming.createAccount("account1");
	} catch (AccountAlreadyExistsException e) {
		System.out.println(e);
	}
    
    
    try {
    	konstruktor_outgoing.createAccount("account2", Arrays.asList(new Transfer[] {A, B, C}));
    }catch(AccountAlreadyExistsException e) {
    	System.out.println(e);
    }
    
    konstruktor_outgoing.getTransactions("account2");
    
    try {
    	konstruktor_incoming.removeTransaction("account1", A);
    }catch(TransactionDoesNotExistException e) {
    	System.out.println(e);
    }
    
    try {
    	konstruktor_incoming.addTransaction("account1", A);
    	konstruktor_incoming.addTransaction("account1", B);
    	konstruktor_incoming.addTransaction("account1", C);
    	konstruktor_incoming.addTransaction("account1", D);
    	} 
    catch(AccountDoesNotExistException e) {
    	System.out.println(e);
    }
    catch(TransactionAlreadyExistException e) {
    	System.out.println(e);
    }
   
   // konstruktor_incoming.containsTransaction("account1", B);
    //konstruktor_incoming.getAccountBalance("");
    konstruktor_incoming.getTransactions("account1");
    konstruktor_incoming.getTransactionsSorted("account1", true);
    konstruktor_incoming.getTransactionsSorted("account1", false);
    konstruktor_incoming.getTransactionsByType("account1", true);
    konstruktor_incoming.getTransactionsByType("account1", false);
    
    
    System.out.println("\nVergleich von konstruktor_outgoing und konstruktor_incoming");
    System.out.println("Ewartete Rückgabe false");
    konstruktor_incoming.equals(konstruktor_outgoing);
    System.out.println("\nVergleich von konstruktor_outgoing mit sich selbst");
    System.out.println("Erwartete rückgabe true");
    konstruktor_outgoing.equals(konstruktor_outgoing);
    
    try {
		nuroutgoing.createAccount("account2");
	} catch (AccountAlreadyExistsException e) {
		System.out.println(e);
	}
    
    //nuroutgoing.getAccountBalance("account2");
  */ 	
    }
}
