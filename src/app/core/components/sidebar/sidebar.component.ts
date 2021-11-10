import { Observable } from 'rxjs';
import { SudokuService } from './../../services/sudoku.service';
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent {
  constructor(private sudokuService: SudokuService) {}

  title = 'Sudoku';
  sudokuId = 1;
  unsolved = '';

  getNextSudoku(): void{
    console.log('In Function: SidebarComponent.getNextSudoku()')
    let jsonArr: Observable<Object>;
    this.sudokuService.getSudokuById(this.sudokuId).subscribe(sudoku => this.unsolved = sudoku.unsolved);
    if (this.sudokuId == 1){
      this.sudokuId = 2;
    } else {
      this.sudokuId = 1;
    }
    console.log(this.unsolved)
  }
}
