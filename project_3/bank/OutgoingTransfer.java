package bank;


/**
 * <p>Struktur von Outgoing Transfer</p>
 * <p>OutgoingTransfer erbt von Transfer und überschreibt die Methode calculate</p>
 * */

public class OutgoingTransfer extends Transfer {

	public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) {
		super(date, amount, description,  sender, recipient);
	}

	@Override
	public double calculate()
	{
		return -amount;
	}
	
}
