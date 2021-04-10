package application;


import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class petAddController {
	
    @FXML
    private ComboBox<String> chooseSex;

    @FXML
    private ComboBox<String> chooseSpecies;
	
	ObservableList<String> sexList =FXCollections.observableArrayList("F","M");
	
    ObservableList<String> speciesList=FXCollections.observableArrayList();

 
	    @FXML
	    private AnchorPane basePane;

	    @FXML
	    private Label labelName;

	    @FXML
	    private Label labelAge;

	    @FXML
	    private Label labelSex;

	    @FXML
	    private Label labelSpecies;

	    @FXML
	    private Label labelDetail;

	    @FXML
	    private TextField textName1;

	    @FXML
	    private TextField textAge;

	    @FXML
	    private TextArea textDetail;

	    @FXML
	    private Button buttonSubmit;
	    
	    @FXML
	    private Button buttonBack;

	    @FXML
	    private Button buttonSearch;
	    
	    @FXML
	    private Button buttonDelete;
	
    
    int HostId;
    int reminder=0;
    int update=0;
 
    public void initialize( ) throws ClassNotFoundException, SQLException{
    	HostId=LoginFormController.getHostId();
    	System.out.println(HostId);
    	
    	ResultSet r;
    	String sql="select * from species";
    	r=SqlConnect.SqlFind(sql);
    	while(r.next()) {
    		String temp=r.getString("speciesName");
    		speciesList.add(temp);
    	}
    	 	
    	chooseSex.setItems(sexList);
    	chooseSpecies.setItems(speciesList);
    	
    }
    @FXML
    void compare(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String sql="select * from pet where hostId ='"+HostId+"' and petName ='"+textName1.getText()+"'";
        ResultSet result = null;
    	result=SqlConnect.SqlFind(sql);
    	 String a=null;
    	 String Age = null;
    	 String Sex = null;
    	 String Species = null;
    	 String Detail = null;

    	
    	while (result.next()) {
    		a=result.getString("petName");
    		Age=String.valueOf(result.getInt("petAge"));
    		Sex=result.getString("petSex");
    		Species=result.getString("petSpecies");
    		Detail=result.getString("petDetail");
    		
    		if(a.equals(textName1.getText())) {
    	    	reminder=1;
    		}
    	}
    	
    	
    	
    	if(reminder==1) {
    	AlertHelper.showAlert(AlertType.INFORMATION, "Exists", "Do you want to Change?");
    	update=1;
		textAge.setText(Age);
		chooseSex.setValue(Sex);
		chooseSpecies.setValue(Species);
		textDetail.setText(Detail);
    	}
    	else {
    		AlertHelper.showAlert(AlertType.INFORMATION, "Not Found", "Please Go On and Register");
    	}
    	

    }
    
   
    @FXML
    void Confirm(ActionEvent event) throws ClassNotFoundException, IOException {

    	String Name=textName1.getText();
    	String tempAge=textAge.getText();
    	int Age=Integer.parseInt(tempAge);
    	String Sex=chooseSex.getValue();
    	String Species=chooseSpecies.getValue();
    	String Detail=textDetail.getText();
    	int alert=0;
    	if(textName1.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "Please Fill in Name");
    		alert=1;
    	}
    	if(textAge.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "Please Fill in Age");
    		alert=1;
    	}
    	if(chooseSex.getValue().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "Please Fill in Sex");
    		alert=1;
    	}
    	if(chooseSpecies.getValue().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "Please Fill in Species");
    		alert=1;
    	}
    	if(textDetail.getText().isEmpty()) {
    		AlertHelper.showAlert(AlertType.ERROR, "Error", "Please Fill in Detail");
    		alert=1;
    	}
    	
    	if(alert!=1) {
    		if(update!=1) {
    	Pet p =new Pet(HostId,Name,Age,Sex,Species,Detail);
    	p.addPetToSql();
    	AlertHelper.showAlert(AlertType.INFORMATION, "Success","Your pet information has been added");
    	}
    		else {
    			String sql="update pet set petAge ='"+Age+"',petSex='"+Sex+"',petSpecies='"+Species+"',petDetail='"+Detail+"'"
    					+ " where petName ='"+Name+"' and hostId ='"+HostId+"'";
    			SqlConnect.SqlUpdate(sql);
    			AlertHelper.showAlert(AlertType.INFORMATION, "Success", "Your pet information has been changed ");
    			update=0;
    		}
        	AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/User.fxml"));
    	    basePane.getChildren().setAll(mainPane);	
    	}
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
    	AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/User.fxml"));
	    basePane.getChildren().setAll(mainPane);
    }
    
    @FXML
    void delete(ActionEvent event) throws ClassNotFoundException, IOException {

    	if(reminder==1) {
    		String sql="delete from pet where petName='"+textName1.getText()+"' and hostId='"+HostId+"'";
    		SqlConnect.SqlUpdate(sql);
    		AlertHelper.showAlert(AlertType.CONFIRMATION, "Success","Your pet information has been deleted");
    		AnchorPane mainPane=FXMLLoader.load(getClass().getResource("/application/User.fxml"));
    	    basePane.getChildren().setAll(mainPane);	
    	    reminder=0;
    	}
    	else {
    		AlertHelper.showAlert(AlertType.CONFIRMATION, "Failed", "Please search first");
    	}
    }


    

    
}