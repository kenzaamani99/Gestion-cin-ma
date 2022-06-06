package ma.emsi.GestionCinemas.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import ma.emsi.GestionCinemas.dao.FilmRepository;
import ma.emsi.GestionCinemas.dao.TicketRepository;
import ma.emsi.GestionCinemas.entities.Film;
import ma.emsi.GestionCinemas.entities.Ticket;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@GetMapping(path="/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name="id")Long id) throws Exception{
    	Film f = filmRepository.findById(id).get();
    	String photoName = f.getPhoto();
    	File file = new File(System.getProperty("user.home")+"/Cinema/Images/"+photoName+".jpg");
    	Path path = Paths.get(file.toURI());
    	return Files.readAllBytes(path);
    }
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm) {
		List<Ticket> listTickets = new ArrayList<>();
		ticketForm.getTickets().forEach(idTicket ->{
			 Ticket ticket = ticketRepository.findById(idTicket).get();
			 ticket.setNomClient(ticketForm.getNomClient());
			 ticket.setReservee(true);
			 ticket.setCodePayement(ticketForm.getCodePayement());
			 ticketRepository.save(ticket);
			 listTickets.add(ticket);
		});
		return listTickets;
	}
    
}
@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> tickets = new ArrayList<>();
}
