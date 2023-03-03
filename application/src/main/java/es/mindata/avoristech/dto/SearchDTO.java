package es.mindata.avoristech.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.mindata.avoristech.domain.SearchEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SearchDTO implements Serializable {
	private static final long serialVersionUID = 7021190517824322421L;

	@NotNull
	private String hotelId;

	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date checkIn;

	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date checkOut;

	@NotNull
	@Size(min = 1)
	private Set<Integer> ages;

	/*
	 * Constructors
	 */

	public SearchDTO() {
		super();
	}

	public SearchDTO(final SearchEntity searchEntity) {
		this();
		this.hotelId = searchEntity.getHotelId();
		this.checkIn = searchEntity.getCheckIn();
		this.checkOut = searchEntity.getCheckOut();
		this.ages = searchEntity.getAges();
	}

	/*
	 * HashCode, Equals & ToString
	 */

	@Override
	public int hashCode() {
		return Objects.hash(ages, checkIn, checkOut, hotelId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchDTO other = (SearchDTO) obj;
		return Objects.equals(ages, other.ages) && Objects.equals(checkIn, other.checkIn)
				&& Objects.equals(checkOut, other.checkOut) && Objects.equals(hotelId, other.hotelId);
	}

	@Override
	public String toString() {
		return "SearchDTO [hotelId=" + hotelId + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", ages=" + ages
				+ "]";
	}

	/*
	 * Getters & Setters
	 */

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Set<Integer> getAges() {
		return ages;
	}

	public void setAges(Set<Integer> ages) {
		this.ages = ages;
	}

}
