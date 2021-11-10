import { SudokuService } from './../../services/sudoku.service';
import { Component } from '@angular/core';

@Component({
  selector: 'sudoku-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})

export class BoardComponent {
  constructor(private sudokuService: SudokuService) {}

  sudEntries: string[] = [];
}
