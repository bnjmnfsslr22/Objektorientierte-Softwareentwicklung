package bank.exceptions;


/**
 * <p>Struktur von TransactionAlreadyExistException</p>
 * <p>TransactionAlreadyExistException erbt von der Klasse Exception und definiert die Fehlermeldung, wenn eine Transaction bereits existiert</p> 
 * */

public class TransactionAlreadyExistException extends Exception 
{
	/**
	 * gibt eine Fehlermeldung zurück wenn Exception zutrifft
	 * @param message = Fehlermeldung
	 * */
	public TransactionAlreadyExistException(String message) 
	{
		super(message);
	}
}