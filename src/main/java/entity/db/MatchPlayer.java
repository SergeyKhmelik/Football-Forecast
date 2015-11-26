package entity.db;

public class MatchPlayer {

	private int idMatch;
	private int idPlayer;

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MatchPlayer that = (MatchPlayer) o;

		if (idMatch != that.idMatch)
			return false;
		if (idPlayer != that.idPlayer)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idMatch;
		result = 31 * result + idPlayer;
		return result;
	}

	@Override
	public String toString() {
		return "MatchPlayer{" +
				"idMatch=" + idMatch +
				", idPlayer=" + idPlayer +
				'}';
	}

}
