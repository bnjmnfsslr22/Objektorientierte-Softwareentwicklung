package bank;

import java.util.Comparator;

public class TransactionComparator implements Comparator <Transaction>{

	@Override
	public int compare(Transaction o1, Transaction o2) {
		
		double o1_value = 0;
		double o2_value = 0;
		if(o1 instanceof Payment) {
			o1_value = ((Payment) o1).calculate();
		}
		if(o1 instanceof OutgoingTransfer) {
			o1_value = ((OutgoingTransfer) o1).calculate();
		}
		if(o1 instanceof IncomingTransfer) {
			o1_value =((IncomingTransfer)o1).calculate();
		}
		if(o2 instanceof Payment) {
			o2_value = ((Payment) o2).calculate();
		}
		if(o2 instanceof OutgoingTransfer) {
			o2_value = ((OutgoingTransfer) o2).calculate();
		}
		if(o2 instanceof IncomingTransfer) {
			o2_value =((IncomingTransfer)o2).calculate();
		}
		if(o1_value-o2_value == 0) {
			return 0;
		} else if(o1_value-o2_value<0) {
			return -1;
		} else  {
			return 1;
		}
	}

}
