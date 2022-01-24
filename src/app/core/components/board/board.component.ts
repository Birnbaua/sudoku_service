import { SudokuDataService } from './../../services/sudoku.data.service';
import { Sudoku } from './../../interfaces/Sudoku';
import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';

@Component({
  selector: 'sudoku-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})

export class BoardComponent implements OnInit{
  constructor(private sudokuDataService: SudokuDataService) {}

  @Input() preview = false
  @Input() paused = false
  @Output() sudokuOutput = new EventEmitter<string>()
  sudoku: Sudoku = {}
  startGrid: string[] = []
  currentSud: string[] = []

  ngOnInit(){
    this.sudokuDataService.getSudoku().subscribe((sudoku : Sudoku) => {
      this.sudoku = sudoku
    if(Object.keys(sudoku).length != 0){
      this.startGrid = sudoku.unsolved?.split("")! // '!' makes Angular not check for null/undefined. We have to ensure this property can not be undefined.
    } else {
      this.startGrid = Array.from('0'.repeat(81))
    }
    this.currentSud = Object.assign([],this.startGrid)
    this.sudokuOutput.emit(this.currentSud.join(""))
    })
  }

  sudokuChange(event : any){
    let id = event.target.id
    let val = event.target.value
    this.currentSud[id] = val
    this.sudokuOutput.emit(this.currentSud.join(""))
  }
}
