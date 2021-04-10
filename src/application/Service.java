package application;

public class Service {

	private String petSpecies;
	private String serviceName;
	private String serviceFunction;
	private int servicePrice;
	
	public Service(String Name,String Function,int Price,String Species) {
		this.setPetSpecies(Species);
		this.setServiceName(Name);
		this.setServiceFunction(Function);
		this.setServicePrice(Price);
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceFunction() {
		return serviceFunction;
	}
	public void setServiceFunction(String serviceFunction) {
		this.serviceFunction = serviceFunction;
	}
	public int getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(int servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getPetSpecies() {
		return petSpecies;
	}
	public void setPetSpecies(String petSpecies) {
		this.petSpecies=petSpecies;
	}
	
	
	
	public void addServiceToSql() throws ClassNotFoundException {
	    String excute="insert into Service(serviceName,servicePrice,serviceFunction,petSpecies) "
	    		+ "values ('"+serviceName+"','"+servicePrice+"','"+serviceFunction+"','"+petSpecies+"')";
	    SqlConnect.SqlUpdate(excute);
	}
}
