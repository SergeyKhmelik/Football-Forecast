package entity.db;

import java.sql.Date;

public class Season {

	private int idSeason;
	private String name;
	private Date start;
	private Date end;

	public int getIdSeason() {
		return idSeason;
	}

	public void setIdSeason(int idSeason) {
		this.idSeason = idSeason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Season season = (Season) o;

		if (idSeason != season.idSeason)
			return false;
		if (end != null ? !end.equals(season.end) : season.end != null)
			return false;
		if (name != null ? !name.equals(season.name) : season.name != null)
			return false;
		if (start != null ? !start.equals(season.start) : season.start != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idSeason;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (start != null ? start.hashCode() : 0);
		result = 31 * result + (end != null ? end.hashCode() : 0);
		return result;
	}

}