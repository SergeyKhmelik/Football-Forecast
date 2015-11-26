package entity.db;

public class User {

	private int idUser;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private int idRole;
	private boolean blocked;

	public User() {
	}

	public User(int id, String login, String password, String name,
			String surname, String email, int idRole) {
		this.idUser = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.idRole = idRole;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		return
				!(this.getIdUser() != user.getIdUser() ||
						(email != null ? !email.equals(user.email) : user.email != null) ||
						(login != null ? !login.equals(user.login) : user.login != null) ||
						(name != null ? !name.equals(user.name) : user.name != null) ||
						(password != null ? !password.equals(user.password) : user.password != null) ||
						(surname != null ? !surname.equals(user.surname) : user.surname != null) ||
						idRole != user.idRole);
	}

	@Override
	public int hashCode() {
		int result = getIdUser();
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + idRole;
		return result;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", login=" + login + ", password="
				+ password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", idRole=" + idRole + "]";
	}

}
