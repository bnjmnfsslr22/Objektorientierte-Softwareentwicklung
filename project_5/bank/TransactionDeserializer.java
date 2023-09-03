package bank;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * wandelt die Namen von Attributen und Objekten aus dem json Format in java attribute/objekte um
 * @param arg0 JsonElement
 * @param arg1 Type
 * @param arg2 JsonDeserializationContext
 * @return Java Object (Payment|IncomingTransfer|OutgoingTransfer)
 */

public class TransactionDeserializer implements JsonDeserializer<Transaction> {

	@Override
	public Transaction deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException { 
		JsonObject object = arg0.getAsJsonObject();


	    String classname = object.get("CLASSNAME").getAsString();
	    JsonObject instance = object.get("INSTANCE").getAsJsonObject();

	    String date = instance.get("date").getAsString();
	    double amount = instance.get("amount").getAsDouble();
	    String description = instance.get("description").getAsString();

	    if(classname.equals("Payment")) {
	    	return new Payment(date, amount, description,
	    			instance.get("incomingInterest").getAsDouble(),
	                instance.get("outgoingInterest").getAsDouble());
	    } else {
	    	String sender = instance.get("sender").getAsString();
	    	String recipient = instance.get("recipient").getAsString();

	    	if (classname.equals("IncomingTransfer")) {
	    		return new IncomingTransfer(date, amount, description, sender, recipient);
	        } else if (classname.equals("OutgoingTransfer")) {
	        	return new OutgoingTransfer(date, amount, description, sender, recipient);
	        } else {
	            System.out.println("PROBLEM");
	        }
	    	
	    }

	    return null;
	}

}
