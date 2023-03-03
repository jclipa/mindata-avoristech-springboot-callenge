package es.mindata.avoristech.dto.response;

public class SearchResDTO {

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
	 * Getters & Setters
	 */

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

}
