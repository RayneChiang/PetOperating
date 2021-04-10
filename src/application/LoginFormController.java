package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.sql.*;

public class LoginFormController {
	
	static final String JDBC_driver="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost:3306/ecommerce?useUnicode=true&characterEncoding=utf8";
	static final String USER="root";
	static final String password="123456";

    @FXML
    private AnchorPane basePane;

    @FXML
    private GridPane LoginPane;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPassword;

    @FXML
    private TextField textName;

    @FXML
    private PasswordField textPassword;
    
    @FXML
    private Button buttonLogin;
    
    @FXML
    private Button backButton;
    
    static int recallHostId;

    

    @FXML
    void Login(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	String name = textName.getText();
    	String password = textPassword.getText();
    	
    	
    	if(textName.getText().isEmpty()){
    		AlertHelper.showAlert(AlertType.WARNING, "Sorry", "Don't have Name? Register First");
    	}
    	if(textPassword.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.WARNING, "Sorry", "Enter Your PasswordPlease");
    	}
    	int reminder=0;
    	
    	if(textName.getText().equals("ADMIN")&&textPassword.getText().equals("ADMIN")) {
    		AnchorPane adPane= FXMLLoader.load(getClass().getResource("/application/Administrator.fxml"));
			basePane.getChildren().setAll(adPane);
			reminder=3;
    	}
    	else {
    	
    		String select="select * from User";
    		ResultSet result=SqlConnect.SqlFind(select);
    		System.out.println(result);
    		while(result.next()) {
    			if(textName.getText().equals(result.getString("userName"))) {
    				reminder=1;
    				if(textPassword.getText().equals(result.getString("Password"))) {
    					reminder=2;
    					recallHostId=result.getInt("userId");
    					System.out.println(recallHostId);
    					break;
    				}
    			}
    		
    		}
    		
    		}
    	
    	switch(reminder) {
    	case 0:
    		AlertHelper.showAlert(AlertType.WARNING, "Sorry", "Please Register First");
    		break;
    	case 1:
    		AlertHelper.showAlert(AlertType.WARNING, "Sorry", "Forget your password?");
    		break;
    	case 2:
    		AlertHelper.showAlert(AlertType.CONFIRMATION, "Congratulation!","WelCome");
    		AnchorPane adPane= FXMLLoader.load(getClass().getResource("/application/User.fxml"));
			basePane.getChildren().setAll(adPane);
    		break;
    	case 3:
    		AlertHelper.showAlert(AlertType.CONFIRMATION, "Congratulation!","Administrator");
    	}
    		
    
    }
    
    
    
    @FXML 
    void backToregister(ActionEvent e) throws IOException{
    	AnchorPane adPane= FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
    	basePane.getChildren().setAll(adPane);
    }
    
    public static int getHostId(){
    	
		return recallHostId;
    }

}
