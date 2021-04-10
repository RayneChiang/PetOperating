package application;

public class User {

	private String userName;
	private String password;
	private int userId;
	
	public User(String Name, String Password) {
		this.setUserName(Name);
		this.setPassword(Password);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addUserToSql() throws ClassNotFoundException {
    String excute="insert into User (userName,password) values ('"+userName+"','"+password+"')";
    SqlConnect.SqlUpdate(excute);
	}
}

