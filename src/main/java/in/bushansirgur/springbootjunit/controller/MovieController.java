package in.bushansirgur.springbootjunit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.bushansirgur.springbootjunit.model.Movie;
import in.bushansirgur.springbootjunit.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Movie create(@RequestBody Movie movie) {
		return movieService.save(movie);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Movie> read() {
		return movieService.getAllMovies();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Movie read(@PathVariable Long id) {
		return movieService.getMovieById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		movieService.deleteMovie(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public Movie update(@PathVariable Long id, @RequestBody Movie movie) {
		return movieService.updateMovie(movie, id);
	}
}
























