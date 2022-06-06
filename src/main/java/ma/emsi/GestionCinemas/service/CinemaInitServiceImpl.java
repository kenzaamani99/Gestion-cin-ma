package ma.emsi.GestionCinemas.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.emsi.GestionCinemas.dao.CategorieRepository;
import ma.emsi.GestionCinemas.dao.CinemaRepository;
import ma.emsi.GestionCinemas.dao.FilmRepository;
import ma.emsi.GestionCinemas.dao.PlaceRepository;
import ma.emsi.GestionCinemas.dao.ProjectionRepository;
import ma.emsi.GestionCinemas.dao.SalleRepository;
import ma.emsi.GestionCinemas.dao.SeanceRepository;
import ma.emsi.GestionCinemas.dao.TicketRepository;
import ma.emsi.GestionCinemas.dao.VilleRepository;
import ma.emsi.GestionCinemas.entities.Categorie;
import ma.emsi.GestionCinemas.entities.Cinema;
import ma.emsi.GestionCinemas.entities.Film;
import ma.emsi.GestionCinemas.entities.Place;
import ma.emsi.GestionCinemas.entities.Projection;
import ma.emsi.GestionCinemas.entities.Salle;
import ma.emsi.GestionCinemas.entities.Seance;
import ma.emsi.GestionCinemas.entities.Ticket;
import ma.emsi.GestionCinemas.entities.Ville;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
    
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
    @Override
	public void initVilles() {
          Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(v->{
        	  Ville ville = new Ville();
        	  ville.setName(v);
        	  villeRepository.save(ville);
          });;		
	}

	@Override
	
	public void initCinemas() {
          villeRepository.findAll().forEach(v->{
        	  Stream.of("Megarama","IMAX","FOUNOUN","CHAHRAZAD","DAOULIZ")
        	           .forEach(nameCinema->{
        	        	   Cinema cinema = new Cinema();
        	        	   cinema.setName(nameCinema);
        	        	   cinema.setVille(v);
        	        	   cinema.setNombreSalles(3+(int)(Math.random()*7));
        	        	   cinemaRepository.save(cinema);
        	           });
          });		
	}

	@Override
	
	public void initSalles() {
        cinemaRepository.findAll().forEach(cinema->{
        	for (int i = 0; i < cinema.getNombreSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle"+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15+(int)(Math.random()*20));
				salleRepository.save(salle);
				
			}
        });	
	}

	@Override
	public void initPlaces() {
		 salleRepository.findAll().forEach(salle->{
	        	for (int i = 0; i < salle.getNombrePlace(); i++) {
					Place place = new Place();
					place.setNumero(i+1);
					place.setSalle(salle);
					placeRepository.save(place);
					
				}
	        });	
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00")
		.forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});;
		
		
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Action","Fiction","Drama").forEach(cat->{
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilms() {
		double[] durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Game of thrones","Seigneur des anneaux","Spiderman","Ironman","Catwomen")
		.forEach(f->{
			Film film = new Film();
			film.setTitre(f);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(f.replaceAll(" ",""));
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
			
		});;
		
	}

	@Override
	public void initProjections() {
		double[] prix = new double[] {30,50,60,70,90,100};
		villeRepository.findAll().forEach(ville ->{
			ville.getCinemas().forEach(cinema ->{
				cinema.getSalles().forEach(salle ->{
					filmRepository.findAll().forEach(film ->{
						seanceRepository.findAll().forEach(seance ->{
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
		});
		
	}

	@Override
	public void initTickets() {
         projectionRepository.findAll().forEach(p->{
        	 p.getSalle().getPlaces().forEach(place ->{
        		 Ticket ticket = new Ticket();
        		 ticket.setPlace(place);
        		 ticket.setPrix(p.getPrix());
        		 ticket.setProjection(p);
        		 ticket.setReservee(false);
        		 ticketRepository.save(ticket);
        	 });
         });		
	}

}
