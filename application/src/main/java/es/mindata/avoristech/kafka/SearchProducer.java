package es.mindata.avoristech.kafka;

import static es.mindata.avoristech.kafka.KafkaConstants.TOPIC_HOTEL_AVAILABILITY_SEARCH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import es.mindata.avoristech.domain.SearchEntity;

@Component
public class SearchProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private final Gson gson = new Gson();

	public void send(SearchEntity search) {
		final var searchJson = this.gson.toJson(search);
		this.kafkaTemplate.send(TOPIC_HOTEL_AVAILABILITY_SEARCH, searchJson);
	}

}
