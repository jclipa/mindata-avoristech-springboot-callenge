package es.mindata.avoristech.dto.response;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.dto.SearchDTO;

public class SearchCountResDTO {

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
