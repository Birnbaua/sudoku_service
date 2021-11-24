import { SudokuDataService } from './../../core/services/sudoku.data.service';
import { SudokuRequestService } from '../../core/services/sudoku.request.service';
import { Component, OnInit } from '@angular/core';
import { Sudoku } from './../../core/interfaces/Sudoku';
import { Difficulty } from 'src/app/core/interfaces/Difficulty';
import { Observable } from 'rxjs';

@Component({
  selector: 'setup-page',
  templateUrl: './setup.component.html',
  styleUrls: ['./setup.component.css']
})

export class SetupPageComponent{
  constructor(
      private sudokuRequestService: SudokuRequestService,
      private sudokuDataService: SudokuDataService
    ) {}
  
  getSudokuById(id: number): void{
    console.log('In Function: SetupPageComponent.getSudokuById()')
    this.sudokuRequestService.getSudokuById(id).subscribe(sudoku => this.sudokuDataService.setSudoku(sudoku));
  }
}
