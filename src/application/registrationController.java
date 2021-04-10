package application;



import java.io.IOException;

import java.sql.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class registrationController {

	
    @FXML
    private AnchorPane basePane;
    
    @FXML
    private GridPane RegistrationPane;

    @FXML
    private Label labelRegister;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPassword;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPassword;

    @FXML
    private Button backButton;
    
    @FXML
    private Button buttonSubmit;
    
    @FXML
    private Label labelPasswordAgain;
    
    @FXML
    private PasswordField passwordAgain;
   

    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
	    basePane.getChildren().setAll(mainPane);
    }
     
    @FXML
    void userSubmit(ActionEvent event) throws IOException, ClassNotFoundException{
    	String userName = textName.getText();
    	String password = textPassword.getText();
    	int alert=0;
    	if(textName.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error" , "please enter your name");
			alert=1;
    	}
    	
    	if(textPassword.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "please enter your password");
    		alert=1;
    	}
    	
    	if(passwordAgain.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "please enter your password again");
    		alert=1;
    	}
    	 
    	if(!textPassword.getText().equals(passwordAgain.getText())) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "The second Password is not the same"+"\n"+"Please enter again");
    		passwordAgain.setText(null);
    		textPassword.setText(null);
    		alert=1;
    	}
    	
    	
    	if(alert!=1) {
    	
    	User temp=new User(userName,password);
    	
    	temp.addUserToSql();
    	AlertHelper.showAlert(AlertType.INFORMATION, "Success", "You are our member Now");
    	AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
	    basePane.getChildren().setAll(mainPane);
    	}
    	
    }
    

}



