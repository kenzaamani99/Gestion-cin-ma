package ma.emsi.GestionCinemas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ma.emsi.GestionCinemas.service.ICinemaInitService;

@SpringBootApplication
public class GestionCinemasApplication implements CommandLineRunner{
    @Autowired
	private ICinemaInitService cinemaInitService;
	public static void main(String[] args) {
		SpringApplication.run(GestionCinemasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cinemaInitService.initVilles();
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeances();
		cinemaInitService.initCategories();
		cinemaInitService.initFilms();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();
		
	}

}
