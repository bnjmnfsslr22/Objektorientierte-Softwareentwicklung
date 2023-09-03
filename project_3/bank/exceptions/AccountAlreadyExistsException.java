package bank.exceptions;

/**
 * <p>Struktur von AccountAlreadyExistsException </p>
 *<p> AccountAlreadyExistsException erbt von der Klasse Exception und definiert die Fehlermeldung, wenn ein Account bereits existiert</p> 
 * */

public class AccountAlreadyExistsException extends Exception 
{
	/**
	 * gibt eine Fehlermeldung zurück wenn Exception zutrifft
	 * @param message = Fehlermeldung
	 * */
	public AccountAlreadyExistsException(String message) 
	{
		super(message);
	}

}
