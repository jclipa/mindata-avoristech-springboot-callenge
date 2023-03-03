package es.mindata.avoristech.dto.response;

import java.io.Serializable;
import java.util.Objects;

public class SearchResDTO implements Serializable {
	private static final long serialVersionUID = -5005479019078568639L;

	private String searchId;

	/*
	 * Constructors
	 */

	public SearchResDTO() {
		super();
	}

	public SearchResDTO(String searchId) {
		this();
		this.searchId = searchId;
	}

	/*
	 * HashCode, Equals & ToString
	 */

	@Override
	public int hashCode() {
		return Objects.hash(searchId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResDTO other = (SearchResDTO) obj;
		return Objects.equals(searchId, other.searchId);
	}

	@Override
	public String toString() {
		return "SearchResDTO [searchId=" + searchId + "]";
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

}
