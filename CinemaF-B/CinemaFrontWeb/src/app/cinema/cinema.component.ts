import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CinemaService} from '../service/cinema.service';

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})
export class CinemaComponent implements OnInit {

  public villes;
  public cinemas;
  public currentVille;
  public currentCinema;
  public currentProjection;
  public salles;
  public selectedTickets: any;
  constructor(public cinemaService: CinemaService) {
  }



  ngOnInit(): void {
    this.cinemaService.getVille().subscribe(res => {
      this.villes = res;
    }, error => {
      console.log(error);
    })
  }

  onGetCinema(v) {
    this.currentVille = v;
    this.cinemaService.getCinema(v).subscribe(res => {
        this.cinemas = res;
      }, error => {
        console.log(error);
      }
    )
  }

  onGetSalle(c) {
    this.currentCinema = c;
    this.cinemaService.getSalle(c).subscribe(res => {
      this.salles = res;
      this.salles._embedded.salles.forEach(salle => {
        this.cinemaService.getProjections(salle).subscribe(res => {
          salle.projections = res;
        }, error => {
          console.log(error);
        })
      })
    }, error => {
      console.log(error);
    })
  }


  GetTicketsPlaces(p: any) {
    this.currentProjection = p;
    // console.log(p);
    this.cinemaService.getPlaces(p).subscribe(res => {
      this.currentProjection.tickets = res;
      this.selectedTickets = [] ;
    },error => {
      console.log(error);
    })
  }

  onSelectTicket(t) {
    if(!t.selected){
      t.selected = true;
      this.selectedTickets.push(t);
    }else {
      t.selected = false ;
      this.selectedTickets.splice(this.selectedTickets.indexOf(t),1);
    }
      console.log(this.selectedTickets);

  }

  getTicketsClass(t) {
    let str = "btn " ;
    if(t.reserve==true){
      str+="btn-danger"
    }else if(t.selected){
      str+="btn-warning"
    }else{
      str+="btn-primary"
    }
    return str ;
  }


  onPayTickets(value: any) {
    console.log(value);
    let tickets =[]
    this.selectedTickets.forEach(t=>{
      tickets.push(t.id);
    });
    value.tickets = tickets;
    this.cinemaService.payerTickets(value).subscribe(res => {
      alert("votre payement est fait avec success");
      this.GetTicketsPlaces(this.currentProjection);
    },error => {
      console.log(error);
    })
  }
}

