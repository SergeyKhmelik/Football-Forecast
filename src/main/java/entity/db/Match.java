package entity.db;

import java.sql.Date;

public class Match {

	private int idMatch;
	private int idHomeTeam;
	private int idGuestTeam;
	private int idChampionship;
	private int idSeason;
	private Date date;
	private int homeGoals;
	private int guestGoals;

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public int getIdHomeTeam() {
		return idHomeTeam;
	}

	public void setIdHomeTeam(int idHomeTeam) {
		this.idHomeTeam = idHomeTeam;
	}

	public int getIdGuestTeam() {
		return idGuestTeam;
	}

	public void setIdGuestTeam(int idGuestTeam) {
		this.idGuestTeam = idGuestTeam;
	}

	public int getIdChampionship() {
		return idChampionship;
	}

	public void setIdChampionship(int idChampionship) {
		this.idChampionship = idChampionship;
	}

	public int getIdSeason() {
		return idSeason;
	}

	public void setIdSeason(int idSeason) {
		this.idSeason = idSeason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	public int getGuestGoals() {
		return guestGoals;
	}

	public void setGuestGoals(int guestGoals) {
		this.guestGoals = guestGoals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Match match = (Match) o;

		if (guestGoals != match.guestGoals)
			return false;
		if (homeGoals != match.homeGoals)
			return false;
		if (idChampionship != match.idChampionship)
			return false;
		if (idGuestTeam != match.idGuestTeam)
			return false;
		if (idHomeTeam != match.idHomeTeam)
			return false;
		if (idMatch != match.idMatch)
			return false;
		if (idSeason != match.idSeason)
			return false;
		if (date != null ? !date.equals(match.date) : match.date != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idMatch;
		result = 31 * result + idHomeTeam;
		result = 31 * result + idGuestTeam;
		result = 31 * result + idChampionship;
		result = 31 * result + idSeason;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + homeGoals;
		result = 31 * result + guestGoals;
		return result;
	}

	@Override
	public String toString() {
		return "Match{" +
				"idMatch=" + idMatch +
				", idHomeTeam=" + idHomeTeam +
				", idGuestTeam=" + idGuestTeam +
				", idChampionship=" + idChampionship +
				", idSeason=" + idSeason +
				", date=" + date +
				", homeGoals=" + homeGoals +
				", guestGoals=" + guestGoals +
				'}';
	}
}
