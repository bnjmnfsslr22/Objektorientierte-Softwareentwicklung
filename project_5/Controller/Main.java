 package Controller;
	
import java.io.IOException;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	//private PrivateBank bank1;
	
	
	@Override
	public void start(Stage primaryStage) throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException {
			
		//bank1 = new PrivateBank("ing diba", 0.5, 0.4);
		PrivateBank bank1 = new PrivateBank("ing diba", 0.5, 0.4);
		//myListView.getItems().addAll(food);
		
			bank1.createAccount("ess");
			IncomingTransfer K = new IncomingTransfer("02.02.1997", 100, "taschengeld2", "lara" , "sven");
			IncomingTransfer I = new IncomingTransfer("02.02.1997", 200, "taschengeld2", "lara" , "sven");
		    OutgoingTransfer J = new OutgoingTransfer("15.04.1996", 20, "urlaub", "sven", "lara");
				
		    bank1.addTransaction("ess", I);
		    bank1.addTransaction("ess", J);
		    bank1.addTransaction("ess", K);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/Mainview.fxml"));
			Parent root = loader.load();
			MainviewController  controller = loader.getController();
	        controller.showname(bank1);
			
	        /*
			Parent root = null;
			
				root = FXMLLoader.load(getClass().getResource("/View/Mainview.fxml"));
				//root = FXMLLoader.load(getClass().getResource("/View/Accountview.fxml"));
			*/
	        
			Scene Mainview = new Scene(root);
			primaryStage.setScene(Mainview);
			primaryStage.show();
			
	}
	
	/*
	public PrivateBank getBank1(){
		return bank1;
	}*/
	
	public static void main(String[] args) {launch(args);}
	
}
