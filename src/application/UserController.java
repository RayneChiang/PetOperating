package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class UserController {
	
	
	ObservableList<String> cartList=FXCollections.observableArrayList(); 
    

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelPet;

    @FXML
    private Label labelService;

    @FXML
    private Label labelPrice;

    @FXML
    private ComboBox<String> choosePet;

    @FXML
    private ComboBox<String> chooseService;
    
    ObservableList<String> petList=FXCollections.observableArrayList();
    ObservableList<String> serviceList=FXCollections.observableArrayList();

    @FXML
    private Label labelList;

    @FXML
    private Label labelFunction;
    
    @FXML
    private Label labelShowFunction;

    @FXML
    private ListView<String> shoppingList;

    @FXML
    private Label labelSum;
    
    @FXML
    private Label labelPrice1;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonWant;

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonBuy;

    int HostId;
    int sum=0;
    
    public void initialize( ) throws ClassNotFoundException, SQLException{
    	HostId=LoginFormController.getHostId();
    	
    	String sql="select* from pet where hostId = '"+HostId+"'";
    	ResultSet result=SqlConnect.SqlFind(sql);
    	while(result.next()) {
    		String temp=result.getString("petName");
    		petList.add(temp);
    	}
    	choosePet.setItems(petList);
    	}
    
    @FXML
    void addPet(ActionEvent event) throws IOException{
    	AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/petAdd.fxml"));
	    basePane.getChildren().setAll(mainPane);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/LoginForm.fxml"));
	    basePane.getChildren().setAll(mainPane);
    }

    
    String s = null;
    @FXML
    void setService(ActionEvent event) throws ClassNotFoundException, SQLException {
    	serviceList.clear();
    	String petName=choosePet.getValue();
    	String sql="select * from pet where petName = '"+petName+"'";
    	ResultSet result=SqlConnect.SqlFind(sql);
    	
    	while(result.next()) {
    		s=result.getString("petSpecies");
    	}
    	
    	String sql1="select * from service where petSpecies ='"+s+"'";
    	ResultSet result1=SqlConnect.SqlFind(sql1);
    	
    	while(result1.next()) {
    		String serviceName=result1.getString("serviceName");
    		serviceList.add(serviceName);
    	}
    	
    	chooseService.setItems(serviceList);

    }
    
    @FXML
    void setPrice(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String service=chooseService.getValue();
    	String sql="select * from service where petSpecies = '"+s+"' and serviceName='"+service+"'";
    	ResultSet result2=SqlConnect.SqlFind(sql);
        int servicePrice=0;
        String f = null;
    	while(result2.next()) {
    		servicePrice=result2.getInt("servicePrice");
    		f=result2.getString("serviceFunction");
    	}
    	labelPrice.setText(String.valueOf(servicePrice));
    	labelShowFunction.setText(f);
    }

    @FXML
    void want(ActionEvent event) throws ClassNotFoundException, SQLException {

    	String cart = chooseService.getValue().concat(" ").concat(labelPrice.getText());
    	cartList.add(cart);
    	shoppingList.setItems(cartList);
    	
    	sum+=Integer.parseInt(labelPrice.getText());
    	labelSum.setText(String.valueOf(sum));
    	
    	String pet=choosePet.getValue();
    	String service=chooseService.getValue();
    	int petId = 0;
    	int serviceId = 0;
    	
    	String sql="select * from pet where hostId = '"+HostId+"' and  petName='"+pet+"'";
    	ResultSet result=SqlConnect.SqlFind(sql);
    	while(result.next()) {
    		petId=result.getInt("petId");
    	}
    	
    	String sql1="select * from service where serviceName='"+service+"' and petSpecies='"+s+"'";
    	ResultSet result1=SqlConnect.SqlFind(sql1);
    	while(result1.next()) {
    		serviceId=result1.getInt("serviceId");
    	}
    	
    	String sql2="insert into Record (petId,serviceId,datetime) values ('"+petId+"','"+serviceId+"',now()) ";
    	SqlConnect.SqlUpdate(sql2);
    	
    	
    	
    	
    }

    @FXML
    void buy(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	String sum=labelSum.getText();
    	
        AlertHelper.showAlert(AlertType.INFORMATION, "Thank You", "Total is "+sum+"\n"+"Thank You for Coming");
    	

    }

}
