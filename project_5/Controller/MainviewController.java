package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Controller.Main;
import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;

public class MainviewController /*implements Initializable*/{ 
	//private PrivateBank bank1;
	//private Controller.Main main = new Main();
	PrivateBank bank1; 
	
	@FXML
	private AnchorPane mainView;
	
	@FXML
	private ListView<String> myListView;
	
	@FXML
	private Button buttonacc;
	
	@FXML
	private MenuItem choose;
	
	@FXML
	private MenuItem delete;
	
	
	String currentAccount;
	
	
	
	private void refresh() {
		myListView.getItems().clear();
		myListView.getItems().addAll(bank1.getAllAccounts());
		myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		
	
		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			currentAccount = myListView.getSelectionModel().getSelectedItem(); //angeklicktes item geht in currentaccount
		
	}
	});
	}
	
	
	private void exceptionToDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.showAndWait();
		System.exit(0);
	}
	
	public void newAcc(ActionEvent e) {
		//System.out.println("up");
		//buttonacc.setText("Ok nutton");
		Dialog <String>dialog = new TextInputDialog();
		dialog.setTitle("Neuer Account");
		dialog.setHeaderText("Accountname?");
		 
		Optional<String> result = dialog.showAndWait();
		String entered = "none.";
		 
		if (result.isPresent()) {
		 
		    entered = result.get();
		    try {
				bank1.createAccount(entered);
			} catch (Exception e1) {
				exceptionToDialog(e1.getMessage());
			} 
		    IncomingTransfer K = new IncomingTransfer("02.02.1997", 100, "taschengeld2", "lara" , "sven");
			IncomingTransfer I = new IncomingTransfer("02.02.1997", 200, "taschengeld2", "lara" , "sven");
	        OutgoingTransfer J = new OutgoingTransfer("15.04.1996", 20, "urlaub", "sven", "lara");
			
	        try {
	        	bank1.addTransaction(entered, J);
				bank1.addTransaction(entered, K);
				bank1.addTransaction(entered, I);
			} catch (Exception e1) {
				exceptionToDialog(e1.getMessage());
			} 
			
		    myListView.getItems().addAll(entered);
		}
		 refresh();
	}
	public void showname(PrivateBank bank) {
		bank1 = bank;
		
		refresh();
	}
	
	public void auswaehlen (ActionEvent e) throws IOException{
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Accountview.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        AccountviewController  controller = loader.getController();
        controller.showname(currentAccount, bank1);
        
        
        //This line gets the Stage information
        Stage window = (Stage) mainView.getScene().getWindow();
        
        window.setScene(scene);
        window.show();
		
		
	}
	
	public void loeschen(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Löschvorgang");
		alert.setHeaderText("Löschen");
		alert.setContentText("Wollen Sie den Account wirklich löschen?");
		Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			   try {
				bank1.deleteAccount(currentAccount);
			} catch (Exception e1) {
				exceptionToDialog(e1.getMessage());
			}
			   myListView.getItems().remove(currentAccount);
			} else {
			    
			}
			refresh();
	}
	
	PrivateBank getBank1(){
		return bank1;
	}
	
	
	/*
	 //scene wechsel der funktioniert
	Parent root = FXMLLoader.load(getClass().getResource("/View/Accountview.fxml"));
	Stage window = (Stage) mainView.getScene().getWindow();
	Scene scene = new Scene(root);
	window.setScene(scene);
	window.show();*/
	
	/*
	FXMLLoader loader = new FXMLLoader();
   loader.setLocation(getClass().getResource("/View/Accountview.fxml"));
   Parent root = loader.load();
   Scene Accountview = new Scene(root);
   Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	window.setScene(Accountview);
	window.show();*/
	
	/*
	Parent Root = null;
	try {
		Root = FXMLLoader.load(getClass().getResource("/View/Accountview.fxml"));
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
	Scene Accountview = new Scene(Root);
	Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	window.setScene(Accountview);
	window.show();*/
	//System.out.println("up");
	
}
