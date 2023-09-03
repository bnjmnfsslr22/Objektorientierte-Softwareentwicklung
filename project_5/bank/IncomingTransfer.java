package bank;

/**
 * <p>Struktur von Incoming Transfer</p>
 * <p>IncomingTransfer erbt von Transfer und überschreibt die Methode calculate</p>
 * */
public class IncomingTransfer extends Transfer {

	public IncomingTransfer(String date, double amount, String description, String sender, String recipient) {
		super(date, amount, description, sender, recipient);
	}
	
	@Override
	public double calculate()
	{
		return amount;
	}
	
	
}
