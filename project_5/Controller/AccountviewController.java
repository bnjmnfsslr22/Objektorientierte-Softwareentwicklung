package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.lang.reflect.Field;

public class AccountviewController   {

	@FXML
	private AnchorPane accountView;
	
	@FXML
	private MenuItem loschn;
	
	@FXML
	private Label nameaccount;
	
	@FXML
	private Label accountbalance;
	
	@FXML
	private ListView<String> ListViewAcc;
	
	PrivateBank bank1;
	String momentanerAccount;
	String currentTransaction;
	Map <String, Transaction> dictonary;
	
	
	
	  
	private void exceptionToDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.showAndWait();
		System.exit(0);
	}
	 
	//löschen kontextmenü
	public void loeschentransact(ActionEvent e) throws IOException {
		//System.out.println(currentTransaction);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Löschvorgang");
		alert.setHeaderText("Löschen");
		alert.setContentText("Wollen Sie die Transaktion wirklich löschen?");
		Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
				bank1.removeTransaction(momentanerAccount,dictonary.get(currentTransaction));
				}catch (Exception e1) {
					exceptionToDialog(e1.getMessage());
				}
				refresh();
			} else {
			    
			}
	}
	
	public void showname(String currentacc, PrivateBank bank) {
		//System.out.println("bb");
		bank1 = bank;
		//System.out.println(bank1);
		momentanerAccount = currentacc;
		nameaccount.setText(currentacc);
		refresh();
		
		}
	
	public void positiveAmounts(ActionEvent e) {
		
		List<Transaction> ergebnis = bank1.getTransactionsByType(momentanerAccount, true);
		
		ListViewAcc.getItems().clear();
		for(Transaction s: ergebnis) {
			ListViewAcc.getItems().addAll(s.toString());
		}
		
		
		}
	public void negativeAmounts(ActionEvent e) {
		
		List<Transaction> ergebnis = bank1.getTransactionsByType(momentanerAccount, false);
		
		ListViewAcc.getItems().clear();
		for(Transaction s: ergebnis) {
			ListViewAcc.getItems().addAll(s.toString());
		}
		
		}
	
	private void refresh() {
		accountbalance.setText(""+bank1.getAccountBalance(momentanerAccount));
		
		List<Transaction> ergebnis = bank1.getTransactions(momentanerAccount);
	    ListViewAcc.getItems().clear();
	    dictonary = new HashMap <>();
		for (Transaction s : ergebnis ) {
			String x = s.toString();
			dictonary.put(x, s);
			ListViewAcc.getItems().addAll(x);
		}
	    	
	    ListViewAcc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	    		
	    	@Override
	    	public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
	    		currentTransaction = ListViewAcc.getSelectionModel().getSelectedItem(); //angeklicktes item geht in currentTransaction
	    	}
	    });
	}
	
	public void aufsteigend(ActionEvent e) {
		ListViewAcc.getItems().clear();

		List<Transaction> ergebnis = bank1.getTransactionsSorted(momentanerAccount, true);
		for (Transaction s : ergebnis ) {
			ListViewAcc.getItems().addAll(s.toString());
		}
	}
	
	public void absteigend(ActionEvent e) {
		ListViewAcc.getItems().clear();
		List<Transaction> ergebnis = bank1.getTransactionsSorted(momentanerAccount, false);
		for (Transaction s : ergebnis ) {
			ListViewAcc.getItems().addAll(s.toString());
		}
	}
	
	private boolean checkFieldContents(List<TextField> liste){
		for(TextField textfield: liste) {
			if(textfield.getText().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public void newtransact(ActionEvent e) {
		
		Dialog<Payment> dialog = new Dialog<>();
        dialog.setTitle("Neues Payment");
        dialog.setHeaderText("Bitte geben Sie die Werte ein");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField("date");
        TextField textField2 = new TextField("20.0");
        TextField textField3 = new TextField("description");
        TextField textField4 = new TextField("21.2");
        TextField textField5 = new TextField("5.0");
        dialogPane.setContent(new VBox(8, textField, textField2, textField3, textField4, textField5));
        Platform.runLater(textField::requestFocus);
        
        
    		
    		
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
            	if(!checkFieldContents(List.of(textField, textField2, textField3, textField4, textField5))) {
                	Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("Löschen");
            		alert.setContentText("Alle Felder müssen ausgefüllt sein");
            		alert.showAndWait();
            	}else {
            		  Payment E =  new Payment(textField.getText(), Double.parseDouble(textField2.getText()), textField3.getText(), Double.parseDouble(textField4.getText()), Double.parseDouble(textField5.getText()));
            		  return E;
            	}
               
            }
               return null;
            });
        Optional<Payment> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Payment results) -> {
            try {
				bank1.addTransaction(momentanerAccount, results);
			} catch (Exception e1) {
				exceptionToDialog(e1.getMessage());
			} 
         });
    
        refresh();
       
    }
	
	public void newtransfer(ActionEvent e) {
		
		Dialog<Transfer> dialog = new Dialog<>();
        dialog.setTitle("Neuer Transfer");
        dialog.setHeaderText("Bitte geben Sie die Werte ein");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField("date");
        TextField textField2 = new TextField("20.0");
        TextField textField3 = new TextField("description");
        TextField textField4 = new TextField("sender");
        TextField textField5 = new TextField("recipient");
        dialogPane.setContent(new VBox(8, textField, textField2, textField3, textField4, textField5));
        Platform.runLater(textField::requestFocus);
        
        
    		dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
            	if(!checkFieldContents(List.of(textField, textField2, textField3, textField4, textField5))) {
                	Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("Löschen");
            		alert.setContentText("Alle Felder müssen ausgefüllt sein");
            		alert.showAndWait();
            	}else { //account ist sender
            		if(momentanerAccount.equals(textField4.getText())) {
            			return new OutgoingTransfer(textField.getText(), Double.parseDouble(textField2.getText()), textField3.getText(), textField4.getText(), textField5.getText());
            		} if(momentanerAccount.equals(textField5.getText())) {
            			return new IncomingTransfer(textField.getText(), Double.parseDouble(textField2.getText()), textField3.getText(), textField4.getText(), textField5.getText());
            		}else {
            			System.out.println("Error");
            		}
            		 
            	}
            }
               return null;
            });
        Optional<Transfer> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Transfer results) -> {
            try {
				bank1.addTransaction(momentanerAccount, results);
			} catch (Exception e1) {
				exceptionToDialog(e1.getMessage());
			} 
              
        });
    
        refresh();
       
    }
	
	public void zurueck(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/View/Mainview.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		MainviewController  controller = loader.getController();
        controller.showname(bank1);
        Stage window = (Stage) accountView.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	List<Transaction> ergebnis = bank1.getTransactions(momentanerAccount);
	dictonary = new HashMap <>();
	for (Transaction s : ergebnis ) {
		String x = s.toString();
		dictonary.put(x, s);
		ListViewAcc.getItems().addAll(x);
	}
	//System.out.println("aa");
	ListViewAcc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		
		
		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			currentTransaction = ListViewAcc.getSelectionModel().getSelectedItem(); //angeklicktes item geht in currentTransaction
			
		}
		});
	
	
	*/
	//ListViewAcc.getItems().addAll(bank1.getTransactions(momentanerAccount).toString());
	
	
	/*
	List<Transaction> ergebnis3 = bank1.getTransactions(momentanerAccount);
	for (Transaction s : ergebnis3 ) {
		ListViewAcc.getItems().addAll(s.toString());
	}
	
	List <String> ergebnis2 = ListViewAcc.getItems();
	List<Transaction> ergebnis = bank1.getTransactionsByType(momentanerAccount, false);
	
	
	for(Transaction s: ergebnis) {
	if(ergebnis2.contains(s.toString()))
	{
		ListViewAcc.getItems().remove(s.toString());
	}
	}*/
	//ObservableList <Transaction> ListViewAcc = bank1.getTransactionsByType(momentanerAccount, true);
	//ListViewAcc.getItems().addAll(bank1.getTransactionsByType(momentanerAccount, true).toString());
	//System.out.println(bank1.getTransactionsByType(momentanerAccount, true));
	//ListViewAcc = bank1.getTransactionsByType(momentanerAccount, true).toString());
	
	/*
	List<Transaction> ergebnis = bank1.getTransactionsByType(momentanerAccount, false);
	for (Transaction s : ergebnis ) {
		ListViewAcc.getItems().addAll(s.toString());
	}*/
	
	
	
	//ListViewAcc.getItems().addAll(bank1.getTransactionsByType(momentanerAccount, false).toString());
	
	/*
	@Override 
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//myListView.getItems().addAll(food);
		System.out.println(bank1);
	 	IncomingTransfer I = new IncomingTransfer("02.02.1997", 200, "taschengeld2", "lara" , "sven");
        OutgoingTransfer J = new OutgoingTransfer("15.04.1996", 20, "urlaub", "sven", "lara");
		
        
		try {
			bank1.addTransaction(momentanerAccount, I);
		} catch (TransactionAlreadyExistException | AccountDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			bank1.addTransaction(momentanerAccount, J);
		} catch (TransactionAlreadyExistException | AccountDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListViewAcc.getItems().addAll(bank1.getTransactions(momentanerAccount).toString());
		ListViewAcc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		
	
	@Override
	public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		momentanerAccount = ListViewAcc.getSelectionModel().getSelectedItem(); //angeklicktes item geht in currentaccount
		
	}
	});
	}*/
	
	
	
}
