package test;


import org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import bank.Transaction;
import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;


class PrivateBankTest {
	
	private PrivateBank bank1;
	private PrivateBank konstruktor_outgoing;
	private Transfer transfer1;
	private Transfer transfer2;
	private IncomingTransfer transfer3;
	private OutgoingTransfer transfer4;
	private Payment payment1;
	
	@BeforeEach
	void setUp() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException{
		Path src =  Paths.get("C:/Users/bfues/eclipse-workspace/Praktikum 4/src/jsonFilesSample/PersistenceSample.json");
		Path dst = Paths.get("C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles\\PersistenceSample.json");
		Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
	 bank1 = new PrivateBank("ing diba", 0.2, 0.4);
	 konstruktor_outgoing = new PrivateBank("commerz", 0, 1.4);
	 transfer1  = new Transfer("15.01.2020", 200, "Überweisung", "mann3", "mann1");
	 transfer2 = new Transfer("15.01.2021", 100, "Überweisung", "mann3", "mann1");
	 transfer3  = new IncomingTransfer("15.01.2020", 200, "Überweisung", "mann3", "mann1");
	 transfer4 = new OutgoingTransfer("15.01.2021", 100, "Überweisung", "mann3", "mann1");
	 payment1 = new Payment("01.02.2000",50,"abhebung", 0.2, 0.3);
	 bank1.createAccount("account1");
	 //bank1.addTransaction("account1", transfer2);
	 bank1.addTransaction("account1", transfer1);
	 bank1.addTransaction("account1", payment1);
	 //bank1.writeAccount("account1");
	 }
	
	
	
	@AfterEach
	void tearDown(){
		File path = new File("C:\\Users\\bfues\\eclipse-workspace\\Praktikum 4\\src\\jsonFiles\\");
		String[] files = path.list();
		for(String filename: files) {
			File file = new File(path + filename);
			file.delete();
		}
	
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"account2", "account500","acount600"})
	void testCreateAcount(String arg){
		assertThrows(AccountAlreadyExistsException.class, ()->bank1.createAccount("account1"));
		assertDoesNotThrow(()->bank1.createAccount(arg));
	}
	
	
	@Test
	void removetransaction(){
		try {
			bank1.removeTransaction("account1",payment1);
			}catch (Exception e) {
				 e.printStackTrace();
				}
		assertThrows(TransactionDoesNotExistException.class, ()-> bank1.removeTransaction("account1", payment1));	
	}
	
	@Test
	void testAddTransaction() {
		assertThrows(TransactionAlreadyExistException.class, ()->bank1.addTransaction("account1", transfer1 ));
		assertThrows(AccountDoesNotExistException.class, ()-> bank1.addTransaction("account700", transfer1));
	
	try {
		bank1.addTransaction("account1", transfer2);
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	@Test
	void testContainsTransaction() {
		assertTrue(bank1.containsTransaction("account1", transfer1));
		//assertFalse(bank1.containsTransaction("account2", transfer2));
	}
	
	@Test
	void testGetAccountBalance() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException {
		bank1 = new PrivateBank("ing diba", 0.2, 0.4);
		bank1.createAccount("account1");
		bank1.addTransaction("account1", transfer4);
		assertEquals(-100, bank1.getAccountBalance("account1"));
	}
	
	@Test
	void testGetTransactions(){
		List<Transaction>transactions = new ArrayList<>();
		transactions.add(transfer1);
		transactions.add(payment1);
		 assertEquals(transactions, bank1.getTransactions("account1"));
	}
	
	@Test
	void testGetTransactionsByType() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException {
		List<Transaction> transactions = new ArrayList<>();
		bank1 = new PrivateBank("ing diba", 0.2, 0.4);
		bank1.createAccount("account1");
		bank1.addTransaction("account1", transfer4);
		transactions.add(transfer4);
		assertEquals(transactions,bank1.getTransactionsByType("account1", false));
	}
	@Test
	void testGetTransactionsSorted() throws TransactionAlreadyExistException, AccountDoesNotExistException, AccountAlreadyExistsException, IOException {
		List<Transaction> transactions = new ArrayList<>();
		bank1 = new PrivateBank("ing diba", 0.2, 0.4);
		bank1.createAccount("account1");
		bank1.addTransaction("account1", transfer4);
		bank1.addTransaction("account1", transfer3);
		transactions.add(transfer3);
		transactions.add(transfer4);
		assertEquals(transactions, bank1.getTransactionsSorted("account1", false));
	}
	/*
	@Test
	void testCorrectRead() throws IOException {
		PrivateBank bank2 = new PrivateBank("ing diba", 0.2, 0.4);
		bank2.getDataFromFiles();
		assertThrows(AccountAlreadyExistsException.class, ()->bank2.createAccount("PersistenceSample"));
		Payment p1 = new Payment("10.01.1988",1500, "Deposit; 1500", 0.025, 0.05);
		IncomingTransfer i1 = new IncomingTransfer("01.01.2021", 150, "Incoming Transfer; Eva->Adam; 150", "Account Eva", "Account Adam");
		OutgoingTransfer o1 = new OutgoingTransfer("03.01.2021", 50, "Outgoing Transfer; Adam->Eva; 50", "Account Adam", "Account Eva");
		List <Transaction> transactions = new ArrayList<>();
		transactions.add(p1);
		transactions.add(i1);
		transactions.add(o1);
		List <Transaction> transactions2 = bank2.getTransactions("PersistenceSample");
		
		for(int i = 0; i < transactions.size();i++) {
			//assertEquals(transactions.get(i),transactions2.get(i));
			if(transactions.get(i) instanceof Payment) {
				Payment expected = (Payment) transactions.get(i);
				Payment actual = (Payment) transactions2.get(i);
				
				
				assertEquals(expected.getDate(), actual.getDate());
				assertEquals(expected.getAmount(), actual.getAmount());
				assertEquals(expected.getDescription(), actual.getDescription());
				assertEquals(expected.getIncomingInterest(), actual.getIncomingInterest());
				assertEquals(expected.getOutgoingInterest(), actual.getOutgoingInterest());
			}else if(transactions.get(i) instanceof IncomingTransfer) {
				IncomingTransfer expected = (IncomingTransfer) transactions.get(i);
				IncomingTransfer actual = (IncomingTransfer) transactions2.get(i);
				
				
				assertEquals(expected.getDate(), actual.getDate());
				assertEquals(expected.getAmount(), actual.getAmount());
				assertEquals(expected.getDescription(), actual.getDescription());
				assertEquals(expected.getSender(), actual.getSender());
				assertEquals(expected.getRecipient(), actual.getRecipient());
			}else if(transactions.get(i) instanceof OutgoingTransfer){
				OutgoingTransfer expected = (OutgoingTransfer) transactions.get(i);
				OutgoingTransfer actual = (OutgoingTransfer) transactions2.get(i);
			
			
			assertEquals(expected.getDate(), actual.getDate());
			assertEquals(expected.getAmount(), actual.getAmount());
			assertEquals(expected.getDescription(), actual.getDescription());
			assertEquals(expected.getSender(), actual.getSender());
			assertEquals(expected.getRecipient(), actual.getRecipient());

				
				
			}else {
				assertEquals(transactions.get(i),transactions2.get(i));
			}
		}
		
		
		
		//assertEquals(transactions, bank2.getTransactions("PersistenceSample"));
	}*/
}
	
	







/*
public static void main(String[] args) throws IOException, AccountAlreadyExistsException {
IncomingTransfer E = new IncomingTransfer("22.12.2020",20, "test2", "mann1", "mann2");
 PrivateBank konstruktor_incoming = new PrivateBank("ing diba", 0.5, 0);
 konstruktor_incoming.readAccounts();
 konstruktor_incoming.getTransactions("PersistenceSample").forEach(System.out::println);
 List<Transaction> transactions = new ArrayList<>();
 transactions.add(E);
 konstruktor_incoming.createAccount("Benjamin", transactions);
 konstruktor_incoming.writeAccount("Benjamin");}}
 }}*/
 
 
 
 