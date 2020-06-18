package org.sid.Cinema.web;

import org.sid.Cinema.dao.CinemaRepository;
import org.sid.Cinema.dao.FilmRepository;
import org.sid.Cinema.dao.ProjectionRepository;
import org.sid.Cinema.dao.VilleRepository;
import org.sid.Cinema.entities.Cinema;
import org.sid.Cinema.entities.Film;
import org.sid.Cinema.entities.Projection;
import org.sid.Cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CinemaController {
    @Autowired
    private CinemaRepository cinemaRepository;
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/cinema")
    public String cinemas(Model model,
                          @RequestParam(name="page",defaultValue = "0") int p ,
                          @RequestParam(name="size",defaultValue = "5") int s,
                          @RequestParam(name="keyword",defaultValue = "") String kw ){
        Page<Cinema> ListCinema =cinemaRepository.findByNameContains(kw,PageRequest.of(p,s));
        model.addAttribute("listeCinema", ListCinema);
        model.addAttribute("pages", new int [ListCinema.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("size", s);
        model.addAttribute("keyword",kw);

        return "Cinema" ;
    }
    @RequestMapping(value ="/delete",method = RequestMethod.GET)
    public String delete(Long id, String keyword , int page , int size) {

        cinemaRepository.deleteById(id );

        return "redirect:/cinema?page="+page+"&keyword="+keyword+"&size="+size;

    }
    @Autowired
    private FilmRepository filmRepository ;

    @GetMapping("/film")
    public String film(Model model,
                       @RequestParam(name="page",defaultValue = "0") int p ,
                       @RequestParam(name="size",defaultValue = "5") int s,
                       @RequestParam(name="keyword",defaultValue = "") String kw ){
        Page<Film> ListFilm =filmRepository.findByTitreContains(kw, PageRequest.of(p,s));
        model.addAttribute("listeFilm", ListFilm);
        model.addAttribute("pages", new int [ListFilm.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("size", s);
        model.addAttribute("keyword",kw);

        return "Film" ;
    }

    @PostMapping(path = "/ajoutercinema")
    public String ajoutercinema(Cinema cinema,  Model model){

        cinemaRepository.save(cinema);

        model.addAttribute("cinema",cinema);
        model.addAttribute("mode","new");

        return "Confirmation";
    }
    @Autowired
    private VilleRepository villeRepository;
    @GetMapping("/formcinema")
    public String formCinema(Model model){
        model.addAttribute("cinema",new Cinema());
        List<Ville> ville = villeRepository.findAll();
        model.addAttribute("ville",ville);
        return "Ajoutercinemas";
    }
    @GetMapping(path = "/editercinema")
    public String EditerPatient(Model model, Long id  ) {

        Cinema cinema = cinemaRepository.findById(id).get();
        List<Ville> ville = villeRepository.findAll();
        model.addAttribute("ville",ville);

        model.addAttribute("cinema",cinema);
        model.addAttribute("mode","edit");

        return "Ajoutercinemas";

    }

    @Autowired
    private ProjectionRepository projectionRepository ;
    @GetMapping("projection")
    public String projection( Model model,
                              @RequestParam(name = "idf",required = false)Long idf,
                              @RequestParam(name="page",defaultValue = "0") int p ,
                              @RequestParam(name="size",defaultValue = "5") int s,
                             @RequestParam(name="keyword",defaultValue = "") String kw
                              ){
       Page<Projection> projection = projectionRepository.getProjectionByFilmId(idf,kw,PageRequest.of(p,s));
       model.addAttribute("projection",projection);
        model.addAttribute("pages", new int [projection.getTotalPages()].length);
        model.addAttribute("currentPage",p);
        model.addAttribute("size", s);
        model.addAttribute("idf", idf);
       model.addAttribute("keyword", kw);
        return "Projection";
    }



}
