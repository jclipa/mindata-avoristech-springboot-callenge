package es.mindata.avoristech.dto;

import java.util.Date;
import java.util.Set;

import es.mindata.avoristech.domain.SearchEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SearchDTO {

	@NotNull
	private String hotelId;

	@NotNull
	private Date checkIn;

	@NotNull
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
