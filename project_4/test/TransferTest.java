package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.Transfer;

class TransferTest {

	private Transfer A;
	private Transfer C;
	private Transfer D;
	private IncomingTransfer E;
	private OutgoingTransfer F;
	

		
		@BeforeEach
		void setUp() {
			 D = new Transfer("1.1.2020", 1000,"hallo", "weihnachtsmann", "elfe");
			 C = new Transfer("2.2.2021", 500, "bezahlung2", "elfe", "Weihnachstmann");
			 A = new Transfer(D);
			 E = new IncomingTransfer("2.2.2021", 500, "bezahlung2", "elfe", "Weihnachstmann");
			 F = new OutgoingTransfer("1.1.2020", 1000,"hallo", "weihnachtsmann", "elfe");
		}
		
		@Test
		void testCopyKonstruktor() {
			assertEquals(A,D);
			assertNotEquals(A,C);
			}
		
		@Test
		void testEquals() {
			assertEquals(D.equals(A),A.equals(D));
			assertTrue(D.equals(A));
			}
		
		@Test
		void testToString()
		{
			assertEquals("Datum: 1.1.2020  Description: hallo  Amount: 1000.0  Sender: weihnachtsmann  Recipient: elfe", D.toString());
			assertNotEquals("dffsf", C.toString());
		}
		
		@Test
		void testCalculate() {
			assertEquals(500.0, C.calculate());
			//assertEquals(D.calculate(), F.calculate());
			assertEquals(500.0,E.calculate());
			assertEquals(-1000.0, F.calculate());
		}

}
