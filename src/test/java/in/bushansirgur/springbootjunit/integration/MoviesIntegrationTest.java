package in.bushansirgur.springbootjunit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import in.bushansirgur.springbootjunit.model.Movie;
import in.bushansirgur.springbootjunit.repository.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	private Movie avatarMovie;
	private Movie titanicMovie;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	public void beforeSetup() {
		baseUrl = baseUrl + ":" + port + "/movies";
		
		avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		
		titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
		
		avatarMovie = movieRepository.save(avatarMovie);
		titanicMovie = movieRepository.save(titanicMovie);
	}
	
	@AfterEach
	public void afterSetup() {
		movieRepository.deleteAll();
	}
	
	@Test
	void shouldCreateMovieTest() {
		Movie hangoverMovie = new Movie();
		hangoverMovie.setName("Hangover");
		hangoverMovie.setGenera("Comedy");
		hangoverMovie.setReleaseDate(LocalDate.of(2004, Month.DECEMBER, 31));
		
		Movie newMoive = restTemplate.postForObject(baseUrl, hangoverMovie, Movie.class);
		
		assertNotNull(newMoive);
		assertThat(newMoive.getId()).isNotNull();
	}
	
	@Test
	void shouldFetchMoviesTest() {
	
		List<Movie> list = restTemplate.getForObject(baseUrl, List.class);
		
		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	void shouldFetchOneMovieByIdTest() {
		
		Movie existingMovie = restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(), Movie.class);
		
		assertNotNull(existingMovie);
		assertEquals("Avatar", existingMovie.getName());
	}
	
	@Test
	void shouldDeleteMovieTest() {
		
		restTemplate.delete(baseUrl+"/"+avatarMovie.getId());
		
		int count = movieRepository.findAll().size();
		
		assertEquals(1, count);
	}
	
	@Test
	void shouldUpdateMovieTest() {
		
		avatarMovie.setGenera("Fantacy");
		
		restTemplate.put(baseUrl+"/{id}", avatarMovie, avatarMovie.getId());
		
		Movie existingMovie = restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(), Movie.class);
		
		assertNotNull(existingMovie);
		assertEquals("Fantacy", existingMovie.getGenera());
	}
}















