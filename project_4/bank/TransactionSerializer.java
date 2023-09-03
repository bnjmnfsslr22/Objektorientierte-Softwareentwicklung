package bank;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * wandelt die attribute bzw objekte von java in json objekte um
 * @param src Transaction objekt
 * @param typeOfSrc Type
 * @param context JsonDeserializationContext
 * @return obj JsonObject
 */

public class TransactionSerializer implements JsonSerializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        JsonObject instance = new JsonObject();
        instance.addProperty("date", src.getDate());
        instance.addProperty("amount", src.getAmount());
        instance.addProperty("description", src.getDescription());

        if(src instanceof Payment) {
            Payment payment = (Payment) src;
            obj.addProperty("CLASSNAME", "Payment");

            instance.addProperty("incomingInterest", payment.getIncomingInterest());
            instance.addProperty("outgoingInterest", payment.getOutgoingInterest());
            
        } else if (src instanceof IncomingTransfer) {
            IncomingTransfer incomingTransfer = (IncomingTransfer) src;
            obj.addProperty("CLASSNAME", "IncomingTransfer");

            instance.addProperty("sender", incomingTransfer.getSender());
            instance.addProperty("recipient", incomingTransfer.getRecipient());
        } else if (src instanceof OutgoingTransfer) {
            OutgoingTransfer outgoingTransfer = (OutgoingTransfer) src;
            obj.addProperty("CLASSNAME", "IncomingTransfer");

            instance.addProperty("sender", outgoingTransfer.getSender());
            instance.addProperty("recipient", outgoingTransfer.getRecipient());
        }

        obj.add("INSTANCE", instance);
        return obj;

    }
}
