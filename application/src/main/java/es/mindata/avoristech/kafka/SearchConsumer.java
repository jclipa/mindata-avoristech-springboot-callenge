package es.mindata.avoristech.kafka;

import static es.mindata.avoristech.kafka.KafkaConstants.GROUP_SEARCHES;
import static es.mindata.avoristech.kafka.KafkaConstants.TOPIC_HOTEL_AVAILABILITY_SEARCH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.repository.ISearchRepository;

@Component
public class SearchConsumer {

	@Autowired
	private ISearchRepository searchRepository;

	private final Gson gson = new Gson();

	@KafkaListener(groupId = GROUP_SEARCHES, topics = TOPIC_HOTEL_AVAILABILITY_SEARCH)
	public void listenNewSearches(@Payload String search) {
		final var searchEntity = gson.fromJson(search, SearchEntity.class);
		this.searchRepository.save(searchEntity);
	}

}
