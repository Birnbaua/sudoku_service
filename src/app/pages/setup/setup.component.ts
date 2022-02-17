import { StaticListsService } from './../../core/services/staticlists.service';
import { Difficulty } from './../../core/interfaces/Difficulty';
import { DifficultyRequestService } from './../../core/services/request/difficulty.request.service';
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

export class SetupPageComponent implements OnInit{
  constructor(
      private sudokuRequestService: SudokuRequestService,
      private sudokuDataService: SudokuDataService,
      private imgService: ImgService,
      private difficultyRequestService: DifficultyRequestService,
      private staticListsService: StaticListsService
    ) {}

  difficultyArray: Difficulty[] = []
  typeArray: String[] = []
  sudokuPreviewList : Sudoku[] = []
  difficulty : string = ""
  mode : string = ""

  ngOnInit(): void {
    this.difficultyRequestService.getAll().subscribe((diffArr) => {
      this.difficultyArray = diffArr
    })
    this.typeArray = this.staticListsService.getSudokuTypes()
  }
  

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
    let diffArr: Difficulty[] = this.difficultyArray.filter((diff) => diff.name == this.difficulty)
    let diffNumber = 0
    console.log(diffArr)
    if(diffArr.length !== 0){
      diffNumber = diffArr[0].no
    }
    this.sudokuRequestService.getSudokuIdsBySettings(diffNumber, this.mode).subscribe((sudokuList) => {
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

  printFirstUpper(text: string){
    return text.charAt(0).toUpperCase() + text.slice(1);
  }
}
