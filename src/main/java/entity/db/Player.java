package entity.db;

public class Player {

	private int idPlayer;
	private int idTeam;
	private String firstname;
	private String lastname;
	private int number;
	private Position position;
	private double rank;

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = Position.valueOf(position);
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Player player = (Player) o;

		if (idPlayer != player.idPlayer)
			return false;
		if (idTeam != player.idTeam)
			return false;
		if (number != player.number)
			return false;
		if (Double.compare(player.rank, rank) != 0)
			return false;
		if (firstname != null ? !firstname.equals(player.firstname)
				: player.firstname != null)
			return false;
		if (lastname != null ? !lastname.equals(player.lastname)
				: player.lastname != null)
			return false;
		if (position != null ? !position.equals(player.position)
				: player.position != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = idPlayer;
		result = 31 * result + idTeam;
		result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
		result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
		result = 31 * result + number;
		result = 31 * result + (position != null ? position.hashCode() : 0);
		temp = rank != +0.0d ? Double.doubleToLongBits(rank) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

}
