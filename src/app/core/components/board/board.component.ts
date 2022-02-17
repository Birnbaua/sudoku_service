import { Difficulty } from './../../interfaces/Difficulty';
import { GameStatDataService } from './../../services/data/gamestat.data.service';
import { SudokuDataService } from './../../services/data/sudoku.data.service';
import { Sudoku } from './../../interfaces/Sudoku';
import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { mergeMap } from 'rxjs/operators';

@Component({
  selector: 'sudoku-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css'],
})
export class BoardComponent implements OnInit {
  constructor(
    private sudokuDataService: SudokuDataService,
    private gameStatDataService: GameStatDataService
  ) {}

  @Input() preview = false;
  @Input() paused = false;
  @Input() currentInp = '';
  @Output() sudokuOutput = new EventEmitter<string>();
  sudoku: Sudoku = {};
  startGrid: string[] = [];
  currentSud: string[] = [];

  diagonals: number[] = [0,8,10,16,20,24,30,32,40,40,50,48,60,56,70,64,72,80]

  ngOnInit() {
    this.sudokuDataService.getSudoku().subscribe((sudoku: Sudoku) => {
      this.sudoku = sudoku;
      if (Object.keys(sudoku).length != 0) {
        this.startGrid = sudoku.unsolved?.split('')!;
      } else {
        this.startGrid = Array.from('0'.repeat(81));
      }
      if(this.currentInp){
        this.currentSud = Object.assign([], this.currentInp.split(""));
      }else{
        this.currentSud = Object.assign([], this.startGrid);
      }
      console.log(this.currentSud)
      this.sudokuOutput.emit(this.currentSud.join(''));
    });
  }

  sudokuChange(event: any) {
    let id = event.target.id;
    let val = event.target.value;
    this.currentSud[id] = val;
    this.sudokuOutput.emit(this.currentSud.join(''));
  }

  isDiag(pos: number): boolean{
    if(this.sudoku.type != "DIAGONAL"){
      return false
    }
    if(this.diagonals.lastIndexOf(pos) !=  -1){
      return true
    }
    return false
  }

  printFirstUpper(text: string){
    return text.charAt(0).toUpperCase() + text.slice(1);
  }

}
