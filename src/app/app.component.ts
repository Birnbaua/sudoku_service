import { SidebarComponent } from './core/components/sidebar/sidebar.component';
import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Sudoku';
  unsolved = '';
  
  test(newSudoku: string){
    this.unsolved = newSudoku
  }
}
