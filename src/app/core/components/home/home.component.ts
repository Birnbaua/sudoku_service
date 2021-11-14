import { Component } from '@angular/core';

@Component({
    selector: 'home-page',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })
  
  export class HomePageComponent {
    title = 'Sudoku';
    unsolved = '';
    
    test(newSudoku: string){
      this.unsolved = newSudoku
    }
  }
  