import { AuthService } from 'src/app/core/services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'home-page',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })
  
  export class HomePageComponent{
    constructor(
      private authService: AuthService
    ){}

    title = 'Sudoku';
  }
  