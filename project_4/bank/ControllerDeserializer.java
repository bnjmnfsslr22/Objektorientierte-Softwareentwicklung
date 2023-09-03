package bank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * erzeugt aus Json Element eine Liste von Transaction objekten
 * @return transactions java liste
 * @param arg0 Json Element
 * @param arg1 Type 
 * @param arg2 JsonDeserializationContext
 */


public class ControllerDeserializer implements JsonDeserializer<List<Transaction>>{

	@Override
	public List<Transaction> deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) //bekommen json element woraus wir unsre liste erstellen
			throws JsonParseException {

		List<Transaction> transactions = new ArrayList<>(); //leere liste von transactions erstellen
		JsonArray jarray = arg0.getAsJsonArray(); //dass was wir übergeben bekommen wird als jsonarray verstanden
		
		for (JsonElement object : jarray ) { //für jedes objekt von diesem array wird es als jsonobject instanziert
			JsonObject jobject = (JsonObject) object; //typecasting
			
			JsonDeserializer<Transaction> deserializer = new TransactionDeserializer(); //deserializer wird erwstellt
			GsonBuilder gsonBuilder = new GsonBuilder(); //gsonbuilder gebaut
			gsonBuilder.registerTypeAdapter(Transaction.class, deserializer); //registriern zu der transaction klasse den deserializer
			Gson customGson = gsonBuilder.create(); //erstellen den builder
			Transaction customObject = customGson.fromJson(jobject,Transaction.class); //dass jobject aus der schleife wird als transaction object gesehen und deserialisert über den deserializer der übergeben wurde aus zeile 30
			transactions.add(customObject); //jedes jason element was wir in unsreer liste haben wird in transactionlist hinzugefügt
			
		}
		return transactions;
	}
	

}
