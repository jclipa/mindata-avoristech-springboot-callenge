package es.mindata.avoristech.rest;

import static es.mindata.avoristech.rest.SearchRestController.PATH_BASE;
import static org.apache.commons.collections4.CollectionUtils.size;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.dto.SearchDTO;
import es.mindata.avoristech.dto.response.SearchCountResDTO;
import es.mindata.avoristech.dto.response.SearchResDTO;
import es.mindata.avoristech.kafka.SearchProducer;
import es.mindata.avoristech.repository.ISearchRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(PATH_BASE)
public class SearchRestController {

	public static final String PATH_BASE = "/";
	public static final String PATH_SEARCH = "/search";
	public static final String PATH_COUNT = "/count";

	@Autowired
	private ISearchRepository repository;

	@Autowired
	private SearchProducer producer;

	@PostMapping(value = PATH_SEARCH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchResDTO> search(@Valid @RequestBody final SearchDTO searchDto) {
		final String generatedSearchId = randomAlphanumeric(7);
		this.producer.send(new SearchEntity(generatedSearchId, searchDto));
		return ResponseEntity.ok(new SearchResDTO(generatedSearchId));
	}

	@GetMapping(value = PATH_COUNT, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchCountResDTO> count(@RequestParam(required = true) final String searchId) {
		ResponseEntity<SearchCountResDTO> result = ResponseEntity.notFound().build();

		final Optional<SearchEntity> searchFindOpt = this.repository.findById(searchId);

		if (searchFindOpt.isPresent()) {
			final SearchEntity foundSearch = searchFindOpt.get();
			final List<SearchEntity> foundSimilars = this.repository.findByHotelIdIsAndCheckInIsAndCheckOutIsAndAgesIn(
					foundSearch.getHotelId(), foundSearch.getCheckIn(), foundSearch.getCheckOut(),
					foundSearch.getAges());
			result = ResponseEntity.ok(new SearchCountResDTO(foundSearch, size(foundSimilars)));
		}

		return result;
	}

	/*
	 * Getters & Setters
	 */

	public ISearchRepository getRepository() {
		return repository;
	}

	public void setRepository(ISearchRepository repository) {
		this.repository = repository;
	}

}
