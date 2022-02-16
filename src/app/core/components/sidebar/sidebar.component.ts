import { SudokuRequestService } from '../../services/request/sudoku.request.service';
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent {
  constructor(private sudokuService: SudokuRequestService) {}  
  title = 'Sudoku'
}
