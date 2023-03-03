package es.mindata.avoristech.kafka;

import static es.mindata.avoristech.kafka.KafkaConstants.GROUP_SEARCHES;
import static es.mindata.avoristech.kafka.KafkaConstants.TOPIC_HOTEL_AVAILABILITY_SEARCH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.repository.ISearchRepository;

@Component
public class SearchKafkaService {

	@Autowired
	private ISearchRepository repository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private final Gson gson = new Gson();

	/*
	 * Producer
	 */

	public void send(SearchEntity search) {
		final var searchJson = this.gson.toJson(search);
		this.kafkaTemplate.send(TOPIC_HOTEL_AVAILABILITY_SEARCH, searchJson);
	}

	/*
	 * Consumer
	 */

	@KafkaListener(groupId = GROUP_SEARCHES, topics = TOPIC_HOTEL_AVAILABILITY_SEARCH)
	public void listenNewSearches(@Payload String search) {
		final var searchEntity = this.gson.fromJson(search, SearchEntity.class);
		this.repository.save(searchEntity);
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

	public KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

}
