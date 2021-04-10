package application;


import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;



public class LoginController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegistation;
    
    @FXML
    private Label labelEcommerce;

   
    
    
    @FXML
    void registrationWindows(ActionEvent event) {
    	try {
    		AnchorPane regPane=FXMLLoader.load(getClass().getResource("/application/RegistationForm.fxml"));
		    mainPane.getChildren().setAll(regPane);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    
    @FXML
    void LoginWindows(ActionEvent event) {
    	try {
    		AnchorPane logPane=FXMLLoader.load(getClass().getResource("/application/LoginForm.fxml"));
    	    mainPane.getChildren().setAll(logPane);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
   
}
