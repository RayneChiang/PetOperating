package application;

public class Pet {

	private int petId;
	private String petName;
	private int petAge;
	private String petSex;
	private String petSpecies;
	private String petDetail;
	private int HostId;
	
	public Pet(int Id,String Name,int Age,String Sex,String Speicies,String Detail) {
		this.setHostId(Id);
		this.setPetName(Name);
		this.setPetAge(Age);
		this.setPetSex(Sex);
		this.setPetSpecies(Speicies);
		this.setPetDetail(Detail);
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public int getPetAge() {
		return petAge;
	}
	public void setPetAge(int petAge) {
		this.petAge = petAge;
	}
	public String getPetSex() {
		return petSex;
	}
	public void setPetSex(String petSex) {
		this.petSex = petSex;
	}
	public String getPetSpecies() {
		return petSpecies;
	}
	public void setPetSpecies(String petSpecies) {
		this.petSpecies = petSpecies;
	}
	public int getHostId() {
		return HostId;
	}
	public void setHostId(int hostId) {
		HostId = hostId;
	}
	public String getPetDetail() {
		return petDetail;
	}
	public void setPetDetail(String petDetail) {
		this.petDetail = petDetail;
	}
	
	public void addPetToSql() throws ClassNotFoundException {
	    String excute="insert into Pet (hostId,petName, petAge, petSex, petSpecies, petDetail) "
	    		+ "values ('"+HostId+"','"+petName+"','"+petAge+"','"+petSex+"','"+petSpecies+"','"+petDetail+"')";
	    SqlConnect.SqlUpdate(excute);
	}
	
	
}
