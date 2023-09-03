package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bank.Payment;

class PaymentTest {

private Payment A;
private Payment C;
private Payment D;
private Payment E;
private Payment F;
private Payment G;
	
	@BeforeEach
	void setUp() {
		 D = new Payment("1.1.2020", 1000,"hallo", 0, 0.3);
		 C = new Payment("2.2.2021", 500, "bezahlung2", 0.1, 0);
		 A = new Payment(C);
		 E = new Payment("date", 10.0, "description", 0.4, 0.5);
		 F = new Payment("date", 10.0, "description", 0.4, 0.5);
		 G = new Payment("date", 11.0, "description", 0.4, 0.5);
	}
	
	@Test
	void luca(){
	assertTrue(E.equals(F));
	assertFalse(E.equals(G));
	}
	@Test
	void testCopyKonstruktor() {
		assertNotEquals(A,D);
		assertEquals(A,C);
		}
	
	
	@Test
	void testEquals() {
		assertEquals(A.equals(C),C.equals(A));
		assertTrue(A.equals(C));
		assertNotEquals(D,C);
		}
	
	@Test
	void testToString(){
		assertEquals("Datum: 1.1.2020  Description: hallo  Amount: 1000.0  Gebühr-Auszahlung-Prozent: 0.3 Gebühr-Einzahlung-Prozent: 0.0", D.toString());
		assertNotEquals("dffsf", C.toString());
	}
	
	@Test
	void testCalculate() {
		assertEquals(450.0, C.calculate());
		
	}

}
