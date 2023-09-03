package bank.exceptions;

/**
*<p>Struktur von AccountDoesNotExistException</p>
*<p> AccountDoesnotExistException erbt von der Klasse Exception und definiert die Fehlermeldung, wenn ein Account nicht existiert</p> 
*/

public class AccountDoesNotExistException extends Exception 
{
	/**
	 * gibt eine Fehlermeldung zurück wenn Exception zutrifft
	 * @param message = Fehlermeldung
	 * */

	public AccountDoesNotExistException(String message) 
	{
	super(message);
	}
}
