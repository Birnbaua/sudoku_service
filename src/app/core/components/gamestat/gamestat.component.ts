import { ImgService } from './../../services/img.service';
import { GameStatDataService } from './../../services/data/gamestat.data.service';
import { Router } from '@angular/router';
import { SudokuDataService } from './../../services/data/sudoku.data.service';
import { SudokuRequestService } from './../../services/request/sudoku.request.service';
import { Difficulty } from './../../interfaces/Difficulty';
import { Sudoku } from './../../interfaces/Sudoku';
import { Component, Input, OnInit } from '@angular/core';
import { GameStat } from '../../interfaces/Gamestat';
import { GameStatsRequestService } from '../../services/request/gamestats.request.service';
@Component({
    selector: '[gamestat]',
    templateUrl: './gamestat.component.html',
    styleUrls: ['./gamestat.component.css']
})
export class Gamestat implements OnInit{
    constructor(
        private sudokuRequestService: SudokuRequestService,
        private sudokuDataService: SudokuDataService,
        private gameStatDataService: GameStatDataService,
        private router: Router,
        private imgServie: ImgService,
        private gameStatRequestService: GameStatsRequestService
    ) {}

    @Input() stat: GameStat | undefined;
    @Input() finished: boolean = false;
    sudoku: Sudoku | undefined;
    name: string = "";
    diff: string = ""; 
    mode: string = "";
    time: string = "";
    preview: string = ""

    ngOnInit(): void {
        this.sudoku = this.stat!.sudoku!
        this.name = this.sudoku.desc!
        let first = this.sudoku.difficulty?.name?.substring(0,1).toUpperCase()
        this.diff = first! + this.sudoku.difficulty?.name?.substring(1)
        let last = this.sudoku.type!.substring(1).toLowerCase()
        this.mode = this.sudoku.type!.substring(0,1) + last
        this.time = this.stat!.duration
        this.preview = this.stat!.preview
    }

    resumeSudoku(){
        this.sudokuRequestService.getSudokuById(this.sudoku?.id!).subscribe(sudoku => this.sudokuDataService.setSudoku(sudoku))
        this.gameStatDataService.setGameStat(this.stat!)
        this.router.navigate(['play'])
    }

    deleteStat(){
        this.gameStatRequestService.deleteGamestat(this.stat!.sudoku ,this.stat!.user).subscribe(x => window.location.reload())
    }

    getImage(arr : any){
        return this.imgServie.byteArrayToImage(arr)
    }

}