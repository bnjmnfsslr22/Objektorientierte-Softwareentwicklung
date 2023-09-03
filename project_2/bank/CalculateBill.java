package bank;

/**
 * <p>Struktur von CalculateBill</p>
 * <p>Methode zum berechnen des Betrags</p>
 * 
 * 
 * @author Benjamin Füßler
 * @version 15.11.2021 
 */


public interface CalculateBill {
/**
 * Berechnet den Gesamtbetrag mit verrechnetem Prozentsatz bei Ein- und Auszahlung und gibt bei Transfererierung des Betrags den Betrag unverändert zurück
 * @return Betrag 
 */
	
	public double calculate();
	
}