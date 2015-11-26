package entity.db;

public class RolePermission {

	private int idRole;
	private int idPermission;

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public int getIdPermission() {
		return idPermission;
	}

	public void setIdPermission(int idPermission) {
		this.idPermission = idPermission;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RolePermission that = (RolePermission) o;

		if (idPermission != that.idPermission)
			return false;
		if (idRole != that.idRole)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idRole;
		result = 31 * result + idPermission;
		return result;
	}
	
}
