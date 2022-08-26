package in.bushansirgur.springbootjunit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import in.bushansirgur.springbootjunit.model.Movie;
import in.bushansirgur.springbootjunit.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
	
	@Mock
	private MovieRepository movieRepository;
	
	@InjectMocks
	private MovieService movieService;
	
	private Movie avatarMovie;
	private Movie titanicMovie;
	
	@BeforeEach
	void init() {
		avatarMovie = new Movie();
		avatarMovie.setId(1L);
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		
		titanicMovie = new Movie();
		titanicMovie.setId(2L);
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
	}
	
	@Test
	void save() {
		
		when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
		
		Movie newMovie = movieService.save(avatarMovie);
		
		assertNotNull(newMovie);
		assertThat(newMovie.getName()).isEqualTo("Avatar");
	}
	
	@Test
	void getMovies() {
		
		List<Movie> list = new ArrayList<>();
		list.add(avatarMovie);
		list.add(titanicMovie);
		
		when(movieRepository.findAll()).thenReturn(list);
		
		List<Movie> movies = movieService.getAllMovies();
		
		assertEquals(2, movies.size());
		assertNotNull(movies);
	}
	
	@Test
	void getMovieById() {
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		Movie existingMovie = movieService.getMovieById(avatarMovie.getId());
		assertNotNull(existingMovie);
		assertThat(existingMovie.getId()).isNotEqualTo(null);
	}
	
	@Test
	void getMovieByIdForException() {
		
		when(movieRepository.findById(2L)).thenReturn(Optional.of(avatarMovie));
		assertThrows(RuntimeException.class, () -> {
			movieService.getMovieById(avatarMovie.getId());
		});
	}
	
	@Test
	void updateMovie() {
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		
		when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
		avatarMovie.setGenera("Fantacy");
		Movie exisitingMovie = movieService.updateMovie(avatarMovie, avatarMovie.getId());
		
		assertNotNull(exisitingMovie);
		assertEquals("Fantacy", avatarMovie.getGenera());
	}
	
	@Test
	void deleteMovie() {
		
		Long movieId = 1L;
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		doNothing().when(movieRepository).delete(any(Movie.class));
		
		movieService.deleteMovie(movieId);
		
		verify(movieRepository, times(1)).delete(avatarMovie);
		
	}
}




















