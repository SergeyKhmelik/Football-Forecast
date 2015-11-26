package entity.db;

public class Team {
	
	private int idTeam;
	private String name;

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Team team = (Team) o;

		return !(idTeam != team.idTeam ||
				(name != null ? !name.equals(team.name) : team.name != null));
	}

	@Override
	public int hashCode() {
		int result = idTeam;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
	
}
