package es.mindata.avoristech.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import es.mindata.avoristech.dto.SearchDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "search")
public class SearchEntity implements Serializable {
	private static final long serialVersionUID = -4557710782340218327L;

	@Id
	@NotNull
	@Column(name = "search_id", nullable = false, insertable = false, updatable = false)
	private String searchId = null;

	@NotNull
	@Column(name = "hotel_id", nullable = false)
	private String hotelId = null;

	@NotNull
	@Column(name = "check_in", nullable = false)
	private Date checkIn = null;

	@NotNull
	@Column(name = "check_out", nullable = false)
	private Date checkOut = null;

	@Size(min = 1)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "search_ages", joinColumns = @JoinColumn(name = "search_id"))
	@Column(table = "search_ages", name = "age")
	private Set<Integer> ages = null;

	/*
	 * Constructors
	 */

	public SearchEntity() {
		super();
	}

	public SearchEntity(final String searchId, final SearchDTO searchReqDto) {
		this();
		this.searchId = searchId;
		this.hotelId = searchReqDto.getHotelId();
		this.checkIn = new Date(searchReqDto.getCheckIn().getTime());
		this.checkOut = new Date(searchReqDto.getCheckOut().getTime());
		this.ages = searchReqDto.getAges();
	}

	/*
	 * HashCode, Equals & ToString
	 */

	@Override
	public int hashCode() {
		return Objects.hash(ages, checkIn, checkOut, hotelId, searchId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchEntity other = (SearchEntity) obj;
		return Objects.equals(ages, other.ages) && Objects.equals(checkIn, other.checkIn)
				&& Objects.equals(checkOut, other.checkOut) && Objects.equals(hotelId, other.hotelId)
				&& Objects.equals(searchId, other.searchId);
	}

	@Override
	public String toString() {
		return "SearchEntity [searchId=" + searchId + ", hotelId=" + hotelId + ", checkIn=" + checkIn + ", checkOut="
				+ checkOut + ", ages=" + ages + "]";
	}

	/*
	 * Getters & Setters
	 */

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

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
