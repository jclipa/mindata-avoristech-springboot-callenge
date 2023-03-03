package es.mindata.avoristech.rest;

import static es.mindata.avoristech.kafka.KafkaConstants.TOPIC_HOTEL_AVAILABILITY_SEARCH;
import static es.mindata.avoristech.rest.SearchRestController.PATH_BASE;
import static es.mindata.avoristech.rest.SearchRestController.PATH_COUNT;
import static es.mindata.avoristech.rest.SearchRestController.PATH_SEARCH;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.mindata.avoristech.domain.SearchEntity;
import es.mindata.avoristech.dto.SearchDTO;
import es.mindata.avoristech.dto.response.SearchCountResDTO;
import es.mindata.avoristech.dto.response.SearchResDTO;
import es.mindata.avoristech.kafka.SearchKafkaService;
import es.mindata.avoristech.repository.ISearchRepository;

@RunWith(MockitoJUnitRunner.class)
public class SearchRestControllerIntegrationTest {

	@Mock
	private ISearchRepository repository;

	@Mock
	private IdGenerator idGenerator;

	@Mock
	private KafkaTemplate<String, String> template;

	private MockMvc mockMvc;

	private SearchKafkaService service = new SearchKafkaService();

	private SearchRestController controller = new SearchRestController();

	private List<SearchEntity> searches;

	private Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

	@BeforeEach
	public void setUp(TestInfo info) {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();

		this.service.setKafkaTemplate(this.template);
		this.service.setRepository(this.repository);

		this.controller.setIdGenerator(this.idGenerator);
		this.controller.setRepository(this.repository);
		this.controller.setService(this.service);

		final var json0 = "{\"searchId\": \"0000xxx\", \"hotelId\": \"hotel1\", \"checkIn\": \"03/03/2023\", \"checkOut\": \"12/03/2023\", \"ages\": [30, 31, 32]}";
		final var json1 = "{\"searchId\": \"0000xxx\", \"hotelId\": \"hotel1\", \"checkIn\": \"03/03/2023\", \"checkOut\": \"12/03/2023\", \"ages\": [30, 31, 32]}";
		final var json2 = "{\"searchId\": \"0000xxx\", \"hotelId\": \"hotel1\", \"checkIn\": \"03/03/2023\", \"checkOut\": \"12/03/2023\", \"ages\": [30, 31, 32]}";
		final var json3 = "{\"searchId\": \"0000xxx\", \"hotelId\": \"hotel1\", \"checkIn\": \"03/03/2023\", \"checkOut\": \"12/03/2023\", \"ages\": [30, 31, 32]}";

		searches = new ArrayList<SearchEntity>();
		searches.add(this.gson.fromJson(json0, SearchEntity.class));
		searches.add(this.gson.fromJson(json1, SearchEntity.class));
		searches.add(this.gson.fromJson(json2, SearchEntity.class));
		searches.add(this.gson.fromJson(json3, SearchEntity.class));
	}

	@Test
	public void search_shouldReturnSearchResultWithGeneratedId() throws Exception {
		final var expResults = new SearchResDTO("0000xxx");
		Mockito.when(this.idGenerator.generateId()).thenReturn("0000xxx");

		final var reqBody = "{\"searchId\": \"0000xxx\", \"hotelId\": \"hotel1\", \"checkIn\": \"03/03/2023\", \"checkOut\": \"12/03/2023\", \"ages\": [30, 31, 32]}";

		final var path = PATH_BASE + "/" + PATH_SEARCH;
		final var mvcResult = this.mockMvc.perform(post(path).content(reqBody).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").exists()).andReturn();

		final var responseBodyS = mvcResult.getResponse().getContentAsString();
		final SearchResDTO responseBody = this.gson.fromJson(responseBodyS, new TypeToken<SearchResDTO>() {
		}.getType());

		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(OK.value());
		assertThat(responseBody).isNotNull();
		assertThat(responseBody).isEqualTo(expResults);

		final var searchJson = this.gson.toJson(reqBody);
		this.template.send(TOPIC_HOTEL_AVAILABILITY_SEARCH, searchJson);
	}

	@Test
	public void search_shouldReturnBadRequest() throws Exception {
		final var path = PATH_BASE + "/" + PATH_SEARCH;
		final var reqBody = new SearchDTO();
		final var mvcResult = this.mockMvc
				.perform(post(path).content(this.gson.toJson(reqBody)).contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist()).andReturn();

		final var responseBodyS = mvcResult.getResponse().getContentAsString();
		final var responseBody = this.gson.fromJson(responseBodyS, new TypeToken<List<SearchResDTO>>() {
		}.getType());

		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
		assertThat(responseBody).isNull();
	}

	@Test
	public void count_shouldReturnSearchCountResult() throws Exception {
		final var searchIdParam = "0000xxx";

		final var expRepByIdResult = this.searches.get(0);
		final var expRepFindResults = this.searches.stream()
				.filter(s -> !searchIdParam.equalsIgnoreCase(s.getSearchId())).collect(toList());
		final var expResult = new SearchCountResDTO(expRepByIdResult, expRepFindResults.size());

		Mockito.when(this.repository.findById(searchIdParam)).thenReturn(Optional.ofNullable(expRepByIdResult));
		Mockito.when(this.repository.findByHotelIdIsAndCheckInIsAndCheckOutIsAndAgesIn(expRepByIdResult.getHotelId(),
				expRepByIdResult.getCheckIn(), expRepByIdResult.getCheckOut(), expRepByIdResult.getAges()))
				.thenReturn(expRepFindResults);

		final var path = PATH_BASE + "/" + PATH_COUNT + "/?searchId=" + searchIdParam;
		final var mvcResult = this.mockMvc.perform(get(path)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON)).andExpect(jsonPath("$").exists()).andReturn();

		final var responseBodyS = mvcResult.getResponse().getContentAsString();
		final SearchCountResDTO responseBody = this.gson.fromJson(responseBodyS, new TypeToken<SearchCountResDTO>() {
		}.getType());

		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(OK.value());
		assertThat(responseBody).isNotNull();
		assertThat(responseBody).isEqualTo(expResult);
		assertThat(responseBody.getSearchId()).isEqualTo(searchIdParam);
		assertThat(responseBody.getCount()).isEqualTo(expRepFindResults.size());
	}

	@Test
	public void count_shouldReturnBadRequest() throws Exception {
		final var path = PATH_BASE + "/" + PATH_COUNT;
		final var mvcResult = this.mockMvc.perform(get(path)).andExpect(status().isBadRequest()).andReturn();

		final var responseBodyS = mvcResult.getResponse().getContentAsString();
		final SearchCountResDTO responseBody = this.gson.fromJson(responseBodyS, new TypeToken<SearchCountResDTO>() {
		}.getType());

		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
		assertThat(responseBody).isNull();
	}

}