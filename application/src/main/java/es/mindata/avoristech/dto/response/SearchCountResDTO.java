package es.mindata.avoristech.dto.response;

import java.io.Serializable;
import java.util.Objects;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.dto.SearchDTO;

public class SearchCountResDTO implements Serializable {
	private static final long serialVersionUID = -8747360551038637503L;

	private String searchId;

	private SearchDTO search;

	private int count;

	/*
	 * Constructors
	 */

	public SearchCountResDTO() {
		super();
	}

	public SearchCountResDTO(final SearchEntity searchEntity, final int count) {
		this();
		this.searchId = searchEntity.getSearchId();
		this.search = new SearchDTO(searchEntity);
		this.count = count;
	}

	/*
	 * HashCode, Equals & ToString
	 */

	@Override
	public int hashCode() {
		return Objects.hash(count, search, searchId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchCountResDTO other = (SearchCountResDTO) obj;
		return count == other.count && Objects.equals(search, other.search) && Objects.equals(searchId, other.searchId);
	}

	@Override
	public String toString() {
		return "SearchCountResDTO [searchId=" + searchId + ", search=" + search + ", count=" + count + "]";
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

	public SearchDTO getSearch() {
		return search;
	}

	public void setSearch(SearchDTO search) {
		this.search = search;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
