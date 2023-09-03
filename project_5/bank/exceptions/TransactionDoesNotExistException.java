package bank.exceptions;

/**
 * <p>Struktur von TransactionDoesNotExistException </p>
 * <p>TransactionDoesNotExistException erbt von der Klasse Exception und definiert die Fehlermeldung, wenn eine Transaction nicht existiert</p>
 * */
public class TransactionDoesNotExistException extends Exception 
{
	/**
	 * gibt eine Fehlermeldung zurück wenn Exception zutrifft
	 * @param message = Fehlermeldung
	 * */
	public TransactionDoesNotExistException(String message)
	{
		super(message);
	}
}