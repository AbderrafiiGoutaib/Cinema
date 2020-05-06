package org.sid.Cinema;

import org.sid.Cinema.Service.ICinemaInitService;
import org.sid.Cinema.entities.Film;
import org.sid.Cinema.entities.Salle;
import org.sid.Cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
	@Autowired
	private ICinemaInitService iCinemaInitService;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class); // pour passer le id d'un film
	iCinemaInitService.initVilles();
	iCinemaInitService.initCinemas();
	iCinemaInitService.initSalles();
	iCinemaInitService.initPlaces();
	iCinemaInitService.initSeances();
    iCinemaInitService.initCategories();
	iCinemaInitService.initFilms();
    iCinemaInitService.initProjections();
	iCinemaInitService.initTickets();
        System.out.println("*******************************enregistrement bien fait*******************************");

	}
}
