package bank;

import java.lang.reflect.Type;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * erzeugt aus liste von transactions ein jarray (json array)
 * @return jarray json array
 * @param src List of Transaction
 * @param typeOfSrc Type 
 * @param context JsonDeserializationContext
 */

public class ControllerSerializer implements JsonSerializer<List<Transaction>> {
    @Override
    public JsonElement serialize(List<Transaction> src, Type typeOfSrc, JsonSerializationContext context) {  //src ist liste von tranactions(bekommt also liste von transactions)

        JsonArray jarray = new JsonArray(); 
        for(Transaction t:src) { 
            GsonBuilder builder = new GsonBuilder(); //erstellen builder
            builder.registerTypeAdapter(Transaction.class, new TransactionSerializer()); 
            Gson customGson = builder.disableHtmlEscaping().create(); 
            jarray.add(customGson.toJsonTree(t, Transaction.class)); 
           
        }
        return jarray; 
    }
}	
