package entity.db;

public class Championship {
	
	private int idChampionship;
	private String name;
	private String description;

	public int getIdChampionship() {
		return idChampionship;
	}

	public void setIdChampionship(int idChampionship) {
		this.idChampionship = idChampionship;
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

		Championship that = (Championship) o;

		if (idChampionship != that.idChampionship)
			return false;
		if (description != null ? !description.equals(that.description)
				: that.description != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idChampionship;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result
				+ (description != null ? description.hashCode() : 0);
		return result;
	}
	
}
