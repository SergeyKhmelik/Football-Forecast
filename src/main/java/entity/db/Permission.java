package entity.db;

import java.io.Serializable;

public class Permission implements Serializable{

	private static final long serialVersionUID = 109333068392612393L;
	private int idPermission;
	private String name;
	private String description;

	public int getIdPermission() {
		return idPermission;
	}

	public void setIdPermission(int idRole) {
		this.idPermission = idRole;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Permission permition = (Permission) o;

		if (getIdPermission() != permition.getIdPermission())
			return false;
		if (description != null ? !description.equals(permition.description)
				: permition.description != null)
			return false;
		if (name != null ? !name.equals(permition.name)
				: permition.name != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = getIdPermission();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result
				+ (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Permission [name=" + name + ", description=" + description
				+ "]\n";
	}

}
