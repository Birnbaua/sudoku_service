import { SudokuDataService } from './../../services/sudoku.data.service';
import { Sudoku } from './../../interfaces/Sudoku';
import { SudokuRequestService } from '../../services/sudoku.request.service';
import { Component, Input, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'sudoku-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})

export class BoardComponent implements OnInit {
  constructor(private sudokuDataService: SudokuDataService) {}

  @Input() preview = false
  sudoku: Sudoku = {}
  sudokuGrid: string[] = []
  

  ngOnInit(){
    this.sudokuDataService.getSudoku().subscribe(sudoku => this.updateSudoku(sudoku))
  }

  updateSudoku(sudoku : Sudoku){
    this.sudoku = sudoku
    if(Object.keys(sudoku).length != 0){
      this.sudokuGrid = sudoku.unsolved?.split("")! // '!' makes Angular not check for null/undefined. We have to ensure this property can not be undefined.
    } else {
      this.sudokuGrid = Array.from('0'.repeat(81))
    }
  }
}
