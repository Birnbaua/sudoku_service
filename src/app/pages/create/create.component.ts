import { SudokuRequestService } from './../../core/services/request/sudoku.request.service';
import { SudokuDataService } from './../../core/services/data/sudoku.data.service';
import { GeneratorRequestService } from './../../core/services/request/generator.request.service';
import { Difficulty } from './../../core/interfaces/Difficulty';
import { DifficultyRequestService } from './../../core/services/request/difficulty.request.service';
import { Component, OnInit } from '@angular/core';
import { Sudoku } from 'src/app/core/interfaces/Sudoku';

@Component({
  selector: 'create-page',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
})
export class CreatePageComponent implements OnInit {
  constructor(
    private difficultyRequestService: DifficultyRequestService,
    private generatorRequestService: GeneratorRequestService,
    private sudokuDataService: SudokuDataService,
    private sudokuRequestService: SudokuRequestService
  ) {}

  difficultyArray: Array<Difficulty> = [];
  currDiff: string = 'Random';
  custTitle: string = '';
  loaded: boolean = false;
  saved: boolean = false;
  sudoku: Sudoku | undefined;

  ngOnInit(): void {
    this.difficultyRequestService.getAll().subscribe((diffArr) => {
      this.difficultyArray = diffArr;
    });
  }

  generateSudoku() {
    this.saved = false
    let diff: Difficulty[] = this.difficultyArray.filter(
      (diff) => diff.name == this.currDiff.toLowerCase()
    );
    this.generatorRequestService.getRandomSudoku(diff[0]).subscribe(
        (sudoku) => {
            console.log(sudoku[0])
            this.sudokuDataService.setSudoku(sudoku[0])
            this.sudoku = sudoku[0];
            this.loaded = true
        }
    );
  }

  saveSudoku(){
      this.loaded = false
      this.sudoku!.desc = this.custTitle
      this.sudokuRequestService.postSudoku(this.sudoku!)
        .subscribe(
            (sudoku) => {
                this.sudokuDataService.setSudoku(sudoku)
                this.sudoku = sudoku
                this.saved = true
            },
            (error) => {
                console.log("Error:")
                console.log(error)
            }
        )
  }

  getDiffName(diff: Difficulty) {
    return diff.name!.charAt(0).toUpperCase() + diff.name!.slice(1);
  }
}
