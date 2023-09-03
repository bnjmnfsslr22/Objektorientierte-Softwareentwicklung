package bank;

/**
 * <p>Struktur von CalculateBill</p>
 * <p>Methode zum berechnen des Betrags</p>
 * 
 * 
 * @author Benjamin F��ler
 * @version 15.11.2021 
 */


public interface CalculateBill {
/**
 * Berechnet den Gesamtbetrag mit verrechnetem Prozentsatz bei Ein- und Auszahlung und gibt bei Transfererierung des Betrags den Betrag unver�ndert zur�ck
 * @return Betrag 
 */
	
	public double calculate();
	
}