package org.sid.Cinema.web;

import lombok.Data;
import org.sid.Cinema.dao.FilmRepository;
import org.sid.Cinema.dao.TicketRepository;
import org.sid.Cinema.entities.Film;
import org.sid.Cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository ;
    @Autowired
    private TicketRepository ticketRepository ;
    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
     public byte[] images(@PathVariable(name = "id") Long id) throws Exception{
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        //System.getProperty("user.home"+"cinema/images")   Exemple C:\Users\GOUTAIBABDERRAFII\Desktop\AbderrafiiGoutaib\JEE\Cinema\CinemaF-B\imagesCinema
        File file = new File("C:\\Users\\GOUTAIBABDERRAFII\\Desktop\\AbderrafiiGoutaib\\JEE\\Cinema\\CinemaF-B\\imagesCinema\\"+photoName);
        //File file = new File(System.getProperty("user.home"+"AbderrafiiGoutaib/JEE/imagesCinema/"+photoName));
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
     }
     @PostMapping("/payerTickets")
     @Transactional
   public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
                List<Ticket> listTickets = new ArrayList<>();
               ticketForm.getTickets().forEach(idTicket->{
                   Ticket ticket = ticketRepository.findById(idTicket).get();
                   ticket.setNomClient(ticketForm.getNomClient());
                   ticket.setReserve(true);
                   ticket.setCodePayement(ticketForm.getCodePayement());
                   ticketRepository.save(ticket);
                   listTickets.add(ticket);
               });
                return listTickets ;
   }
}

@Data
class TicketForm{
    private String nomClient;
    private int CodePayement;
    private List<Long> tickets=new ArrayList<>();
}
