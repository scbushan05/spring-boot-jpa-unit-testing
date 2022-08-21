package in.bushansirgur.springbootjunit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.bushansirgur.springbootjunit.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<Movie> findByGenera(String genera);
}
