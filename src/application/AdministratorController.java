package application;

import java.io.IOException;
import java.sql.Date;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AdministratorController {

	ObservableList<String> speciesList = FXCollections.observableArrayList();
	ObservableList<String> kindList=FXCollections.observableArrayList("User","Pet","Service","Record");
	
	@FXML
	private AnchorPane basepane;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textSpecies;

	@FXML
	private Label labelService;

	@FXML
	private ComboBox<String> chooseSpecies;

	@FXML
	private Label labelName;

	@FXML
	private TextField textName;

	@FXML
	private Label labelPrice;

	@FXML
	private TextField textPrice;

	@FXML
	private Label labelFunction;

	@FXML
	private TextField textFunction;

	@FXML
	private Button buttonAddService;

	@FXML
	private Button buttonBack;

	@FXML
	private Button buttonDeleteSpecies;

	@FXML
	private Button buttonDeleteService;

	@FXML
	private Button buttonSearch;
	

    @FXML
    private Label labelShowDetail;

    @FXML
    private ComboBox<String> chooseKind;

	@FXML
	private Label labelLookUp;
	
    @FXML
    private TextField textLookUpName;
    
    @FXML
    private TextField textLookUpPet;


	@FXML
	private Button buttonUser;

	@FXML
	private Button buttonPet;

	
	@FXML
    private ListView<String> showList;
	
    @FXML
    private Button buttonDeleteBoth;

	String add = null;
	int reminder = 0;
	int update = 0;

	public void initialize() throws ClassNotFoundException, SQLException {

		ResultSet r;
		String sql = "select * from species";
		r = SqlConnect.SqlFind(sql);
		while (r.next()) {
			add = r.getString("speciesName");
			speciesList.add(add);
		}

		chooseSpecies.setItems(speciesList);
		
		chooseKind.setItems(kindList);

	}

	@FXML
	void search(ActionEvent event) throws ClassNotFoundException, SQLException {

		reminder=0;
		
		String sql = "select * from  service " + "where serviceName= '" + textName.getText() + "' and petSpecies ='"
				+ chooseSpecies.getValue() + "'";

		ResultSet result = null;
		result = SqlConnect.SqlFind(sql);

		String n = null;
		String s = null;
		String p = null;
		String f = null;

		while (result.next()) {
			n = result.getString("serviceName");
			s = result.getString("petSpecies");
			p = String.valueOf(result.getInt("servicePrice"));
			f = result.getString("serviceFunction");

			if (n.equals(textName.getText()) && s.equals(chooseSpecies.getValue())) {
			
				textPrice.setText(p);
				textFunction.setText(f);
				reminder = 1;
			}
		

		if (reminder!=0) {
			AlertHelper.showAlert(AlertType.INFORMATION, "Exists", "Do you want to Change?");
		} 
		else {
			AlertHelper.showAlert(AlertType.INFORMATION, "Not Found ", "Please Go ON");
		}

	}
	}

	@FXML
	void addSpecies(ActionEvent event) throws ClassNotFoundException, SQLException {
		String Species = textSpecies.getText();
		int alert = 0;
		if(textSpecies.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "Please fill in");
			alert = 1 ;
		}
		ResultSet result;
		String sql = "select * from Species";
		result = SqlConnect.SqlFind(sql);
		while (result.next()) {
			String temp = result.getString("speciesName");
			if (temp.equals(Species)) {
				alert = 1;
				AlertHelper.showAlert(AlertType.ERROR, "Error", "The Species exsits");
			}
		}
		if (alert != 1) {
			String sql1 = "insert into Species(speciesName) values ('" + Species + "')";
			SqlConnect.SqlUpdate(sql1);
			add = textSpecies.getText();
			speciesList.add(add);
			chooseSpecies.setItems(speciesList);
			AlertHelper.showAlert(AlertType.CONFIRMATION, null, "Success");
		}
	}

	@FXML
	void addService(ActionEvent event) throws ClassNotFoundException {
		String name = textName.getText();
		String temp = textPrice.getText();
		int price = Integer.parseInt(temp);
		String function = textFunction.getText();
		String species = chooseSpecies.getValue();

		int alert = 0;
		if (chooseSpecies.getValue().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "Please choose Species");
			alert = 1;
		}
		if (textName.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "Please fill in Name");
			alert = 1;
		}
		if (textPrice.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "Please fill in Price");
			alert = 1;
		}
		if (textFunction.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "Please fill in Function");
			alert = 1;
		}

		if (alert != 1) {
			if (update != 1) {
				Service s = new Service(name, function, price, species);
				s.addServiceToSql();
				AlertHelper.showAlert(AlertType.INFORMATION, null, "service has been added");
			} else {
				String sql = "update service set servicePrice = '" + price + "',serviceFunction='" + function + "'"
						+ "where serviceName ='" + name + "' and petSpecies ='" + species + "'";
				SqlConnect.SqlUpdate(sql);
				AlertHelper.showAlert(AlertType.INFORMATION, "Success", "Your service has been Changed");
				update = 0;
			}
		}

	}

	@FXML
	void deleteService(ActionEvent event) throws ClassNotFoundException {
		if (reminder == 1) {
			String sql = "delete from service where servicePrice ='" + Integer.parseInt(textPrice.getText()) + "'"
					+ " and serviceFunction='" + textFunction.getText() + "' ";
			SqlConnect.SqlUpdate(sql);
			AlertHelper.showAlert(AlertType.INFORMATION, "Success", "Your service has been deleted");
			reminder = 0;
		} else {
			AlertHelper.showAlert(AlertType.ERROR, "Failed", "Please Search First");
		}

	}

	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane mainPane = FXMLLoader.load(getClass().getResource("/application/LoginForm.fxml"));
		basepane.getChildren().setAll(mainPane);
	}

	@FXML
	void deleteSpecies(ActionEvent event) throws ClassNotFoundException, SQLException {
		String Species = textSpecies.getText();
		ResultSet result;
		String sql = "select * from Species";
		result = SqlConnect.SqlFind(sql);
		int alert = 1;
		while (result.next()) {
			String temp = result.getString("speciesName");
			if (temp.equals(Species)) {
				alert = 0;
			}
		}
		if (alert == 0) {
			String sql1 = "delete from species where speciesName ='" + Species + "'";
			SqlConnect.SqlUpdate(sql1);
			AlertHelper.showAlert(AlertType.INFORMATION, "Success", "The species has been deleted");
			ResultSet r;
			String sql11 = "select * from species";
			r = SqlConnect.SqlFind(sql11);
			while (r.next()) {
				add = r.getString("speciesName");
				speciesList.add(add);
			}

			chooseSpecies.setItems(speciesList);

		} else {
			AlertHelper.showAlert(AlertType.INFORMATION, "Failer",
					"The speices not found \n or you can add then delete");
		}
	}


    @FXML
    void ShowDetail(ActionEvent event) throws ClassNotFoundException, SQLException {

    	String kind=chooseKind.getValue();
    	if(kind.equals("User")) {
    		ObservableList<String> cartList=FXCollections.observableArrayList();
    		String sql="select * from User";
    		ResultSet result;
    		result=SqlConnect.SqlFind(sql);
    		System.out.println(result);
    		int sum=0;
    		while(result.next()) {
    			String userName=result.getString("userName");
    			String password=result.getString("password");
    			
    			String a=userName.concat(" ").concat(password);
    			
    			cartList.add(a);
    			sum++;
    		}
    		String count="\n Total is "+sum;
    		cartList.add(count);
    		showList.setItems(cartList);
    	}
    	if(kind.equals("Pet")) {
    		ObservableList<String> cartList=FXCollections.observableArrayList();
    		String init="petName".concat(" ").concat("petAge").concat(" ").concat("petSex").concat(" ").concat("petSpecies").concat(" ").concat("petDetail");
    		cartList.add(init);
    		String sql="select * from Pet";
    		ResultSet result;
    		result=SqlConnect.SqlFind(sql);
    		int sum=0;
    		while(result.next()) {
    			String petName=result.getString("petName");
    			String petAge=String.valueOf(result.getInt("petAge"));
    			String petSex=result.getString("petSex");
    			String petSpecies=result.getString("petSpecies");
    			String petDetail=result.getString("petDetail");
    			
    			String a=petName+" "+petAge+" "+petSex+" "+petSpecies+" "+petDetail;
    			
    			cartList.add(a);
    			sum++;
    		}
    		String count="\n Total is "+sum;
    		cartList.add(count);
    		showList.setItems(cartList);
    		
    	}
    	if(kind.equals("Service")) {
    		ObservableList<String> cartList=FXCollections.observableArrayList();
    		String init="petSpecies".concat(" ").concat("serviceName").concat(" ").concat("servicePrice").concat(" ").concat("serviceFunction");
    		cartList.add(init);
    		String sql="select * from Service";
    		ResultSet result;
    		result=SqlConnect.SqlFind(sql);
    		int sum=0;
    		while(result.next()) {
    			String petSpecies=result.getString("petSpecies");
    			String serviceName=result.getString("serviceName");
    			String servicePrice=String.valueOf(result.getInt("servicePrice"));
    			String serviceFunction=result.getString("serviceFunction");
    			
    			String a=petSpecies+" "+serviceName+" "+servicePrice+" "+serviceFunction;
    			cartList.add(a);
    			sum++;
    		}
    		String count="\n Total is "+sum;
    		cartList.add(count);
    		showList.setItems(cartList);
    		
    	}
    	if(kind.equals("Record")) {
    		ObservableList<String> cartList=FXCollections.observableArrayList();
    		String init="userName".concat(" ").concat("petName").concat(" ").concat("petSpecies")
    				.concat(" ").concat("serviceName").concat(" ").concat("dateTime");
    		cartList.add(init);
    		String sql="select userName, petName, pet.petSpecies, serviceName,dateTime"
    				+ " from Record  left join service on record.serviceId=service.serviceId  " + 
    				" left join pet on record.petId=pet.petId left join User on pet.hostId = user.userId" ;
    		ResultSet result;
    		result=SqlConnect.SqlFind(sql);
    		int sum=0;
    		while(result.next()) {
    			String userName=result.getString("userName");
    			String petName=result.getString("petName");
    			String petSpecies=result.getString("petSpecies");
    			String serviceName=result.getString("serviceName");
    			String dateTime=result.getString("dateTime");
    			
    				
    			String a=userName+" "+petName+" "+petSpecies+" "+serviceName+" "+dateTime;
    			
    			cartList.add(a);
    			
    			sum++;
    			
    		}
    		String count="\n Total is "+sum;
    		cartList.add(count);
    		showList.setItems(cartList);
    		
    	}
    		
    	}
    	
    

	@FXML
	void petLookUp(ActionEvent event) throws ClassNotFoundException, SQLException {
		String petName=textLookUpPet.getText();
		 String init="Name".concat(" ").concat("Age").concat(" ").concat("Sex").concat(" ")
	        		.concat("Species").concat(" ").concat("userName").concat(" ").concat("password");
		ObservableList<String> cartList=FXCollections.observableArrayList();
		cartList.add(init);
		
		String sql="select * from pet left join User on pet.hostId=User.userId where petName like '%"+petName+"%'  ";
		ResultSet r=SqlConnect.SqlFind(sql);
		while(r.next()) {
			String Name=r.getString("petName");
	        String Age=String.valueOf(r.getInt("petAge"));
	        String Sex=r.getString("petSex");
	        String Species=r.getString("petSpecies");
	        String userName=r.getString("userName");
	        String password=r.getString("password");
	       
	        
	        String a=Name+" "+Age+" "+Sex+" "+Species+" "+userName+" "+password;
	        cartList.add(a);
	        
	        
	        
		}
		
		showList.setItems(cartList);

	}
	
	
	@FXML
	void userLookUp(ActionEvent event) throws ClassNotFoundException, SQLException {
		String userName=textLookUpName.getText();
		 String init="userName".concat(" ").concat("password").concat(" ").concat("petName").concat(" ")
	        		.concat("serviceName").concat(" ").concat("dateTime");
		ObservableList<String> cartList=FXCollections.observableArrayList();
		cartList.add(init);
		
		String sql="select userName,password,petName,serviceName,dateTime  from user left join pet on user.userId=pet.hostId "
				+ "left join record on pet.petId =record.petId "
				+ " left join service on record.serviceId=service.serviceId "
				+ "  where userName like '%"+userName+"%'  ";
		ResultSet r=SqlConnect.SqlFind(sql);
		while(r.next()) {
			String userName1=r.getString("userName");
		    String password=r.getString("password");
			String petName=r.getString("petName");
	       String serviceName=r.getString("serviceName");
	       String dateTime=r.getString("dateTime");
	       
	       String a=userName1+" "+password+" "+petName+" "+serviceName+" "+dateTime;
	      cartList.add(a);
	        
		}
		
		
		showList.setItems(cartList);

	}
	@FXML
    void deleteBoth(ActionEvent event) throws ClassNotFoundException, SQLException {

		if(textLookUpPet.getText().isEmpty()&&textLookUpName.getText().isEmpty()) {
			AlertHelper.showAlert(AlertType.ERROR, "Error", "please enter Something");
		}
		else {
			if(!textLookUpName.getText().isEmpty()) {
				String find="select * from user where userName = '"+textLookUpName.getText()+"'";
				ResultSet r;
				r=SqlConnect.SqlFind(find);
				int find1=0;
				while(r.next()) {
					if(r.getString("userName").equals(textLookUpName.getText())) {
						find1=1;
					}
				}
				if(find1==1) {
					String sql="delete from user where userName ='"+textLookUpName.getText()+"'";
					SqlConnect.SqlUpdate(sql);
					AlertHelper.showAlert(AlertType.CONFIRMATION, "Success","User "
					+textLookUpName.getText()+" has been deleted");
				}
				else {
					AlertHelper.showAlert(AlertType.ERROR, "ERROR", "Not Found");
			}
		}
			if(!textLookUpPet.getText().isEmpty()) {
				String find="select * from Pet where petName = '"+textLookUpPet.getText()+"'";
				ResultSet r;
				r=SqlConnect.SqlFind(find);
				int find1=0;
				while(r.next()) {
					if(r.getString("petName").equals(textLookUpPet.getText())) {
						find1=1;
					}
				}
				if(find1==1) {
					
					String sql="delete from Pet where petName ='"+textLookUpPet.getText()+"'";
					SqlConnect.SqlUpdate(sql);
					AlertHelper.showAlert(AlertType.CONFIRMATION, "Success","Pet"
					+textLookUpPet.getText()+" has been deleted");
				}
				else {
					AlertHelper.showAlert(AlertType.ERROR, "ERROR", "Not Found");
			}
			
			}
		
    }
	}
}

