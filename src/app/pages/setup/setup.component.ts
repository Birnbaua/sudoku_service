import { ImgService } from './../../core/services/img.service';
import { SudokuDataService } from './../../core/services/data/sudoku.data.service';
import { SudokuRequestService } from '../../core/services/request/sudoku.request.service';
import { Component, OnInit } from '@angular/core';
import { Sudoku } from './../../core/interfaces/Sudoku';

@Component({
  selector: 'setup-page',
  templateUrl: './setup.component.html',
  styleUrls: ['./setup.component.css']
})

export class SetupPageComponent{
  constructor(
      private sudokuRequestService: SudokuRequestService,
      private sudokuDataService: SudokuDataService,
      private imgService: ImgService
    ) {}
  
  sudokuPreviewList : Sudoku[] = []
  difficulty : string = ""
  mode : string = ""

  changeMode(mode : string){
    this.mode = mode
  }

  changeDifficulty(diff : string){
    if(diff == "All"){
      this.difficulty = ""
    } else {
      this.difficulty = diff
    }
  }

  fetchSudokus(){
    console.log('In Function: SetupPageComponent.fetchSudokus()')
    this.sudokuPreviewList = []
    this.sudokuRequestService.getSudokuIdsBySettings(this.difficulty, this.mode).subscribe((sudokuList) => {
      console.log(sudokuList.content[0])
      sudokuList.content.forEach(sudoku => this.sudokuPreviewList.push(sudoku))
    })
  }

  getSudokuById(id: number): void{
    console.log('In Function: SetupPageComponent.getSudokuById()')
    this.sudokuRequestService.getSudokuById(id).subscribe(sudoku => this.sudokuDataService.setSudoku(sudoku));
  }

  getImage(arr: any){
    return this.imgService.byteArrayToImage(arr)
  }
}
