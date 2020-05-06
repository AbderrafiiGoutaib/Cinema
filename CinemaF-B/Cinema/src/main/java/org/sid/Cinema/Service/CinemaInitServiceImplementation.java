package org.sid.Cinema.Service;

import org.sid.Cinema.dao.*;
import org.sid.Cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImplementation implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Override
    public void initVilles() {
    Stream.of("Casablanca","Rabat","Marrakech","Tanger","Agadir").forEach(nameVille->{
        Ville ville = new Ville();
        ville.setName(nameVille);
        villeRepository.save(ville);
    });
    }
    @Autowired
    private CinemaRepository cinemaRepository ;
    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("Megarama","IMAX","ABC").forEach(nameCinema->{
                Cinema cinema = new Cinema();
                cinema.setName(nameCinema);
                cinema.setNombredesalles(3+(int)(Math.random()*7));
                cinema.setVille(v);
                cinemaRepository.save(cinema);
            });
        });
    }
    @Autowired
    private SalleRepository salleRepository ;
    @Override
    public void initSalles() {
    cinemaRepository.findAll().forEach(cinema->{
        for(int i=0 ; i<cinema.getNombredesalles();i++){
            Salle salle = new Salle();
            salle.setName("Salle"+(i+1));
            salle.setCinema(cinema);
            salle.setNombrePlace(15+(int)(Math.random()*20));
            salleRepository.save(salle);
        }
    });
    }
    @Autowired
    private PlaceRepository placeRepository ;
    @Override
    public void initPlaces() {
    salleRepository.findAll().forEach( salle->{
        for(int i = 0 ; i< salle.getNombrePlace();i++){
            Place place = new Place();
            place.setNumeroPlace(i+1);
            place.setSalle(salle);
            placeRepository.save(place);
        }
    });
    }
    @Autowired
    private FilmRepository  filmRepository;
    @Override
    public void initFilms() {
        double[]  dure = new double[] {1,1.5,2,2.5,3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("The god father","Avengers","who i am ","Now you see mee","The troy").forEach(titreFilm->{
            Film film =new Film();
            Random ran =new Random();
            film.setTitre(titreFilm);
            film.setDuree(dure[new Random().nextInt(dure.length)]);
            film.setPhoto(titreFilm.replaceAll(" ","")+".jpg");
            film.setCategorie(categories.get(ran.nextInt(categories.size())));
            filmRepository.save(film);
        });
    }
    @Autowired
    private SeanceRepository seanceRepository;
    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public void initTickets() {
    projectionRepository.findAll().forEach(p->{
        p.getSalle().getPlaces().forEach(place->{
            Ticket ticket = new Ticket();
            ticket.setPlace(place);
            ticket.setPrix(p.getPrix());
            ticket.setProjection(p);
            ticket.setReserve(false);
            ticketRepository.save(ticket);
        });
    });
    }
    @Autowired
    private ProjectionRepository projectionRepository;
    @Override
    public void initProjections() {
        double [] prix = new double[] {30,50,70};
        List<Film> films = filmRepository.findAll();
    villeRepository.findAll().forEach(ville->{
        ville.getCinemas().forEach(cinema->{
            cinema.getSalles().forEach(salle->{
                        int index = new Random().nextInt(films.size());
                        Film film = films.get(index);
                    seanceRepository.findAll().forEach(seance->{
                        Projection projection = new Projection();
                        projection.setDateProjection(new Date());
                        projection.setFilm(film);
                        projection.setPrix(prix[new Random().nextInt(prix.length)]);
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });

            });
        });
    });
    }
    @Autowired
    private CategorieRepository categorieRepository;
    @Override
    public void initCategories() {
        Stream.of("Action","Drama","Fiction","Histoire","Documentaire").forEach(c->{
            Categorie categorie =new Categorie();
            categorie.setName(c);
            categorieRepository.save(categorie);
        });
    }
}
