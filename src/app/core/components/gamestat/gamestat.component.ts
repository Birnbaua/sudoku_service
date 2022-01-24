import { Difficulty } from './../../interfaces/Difficulty';
import { Sudoku } from './../../interfaces/Sudoku';
import { Component, Input, OnInit } from '@angular/core';
import { GameStat } from '../../interfaces/Gamestat';
@Component({
    selector: '[gamestat]',
    templateUrl: './gamestat.component.html',
    styleUrls: ['./gamestat.component.css']
})
export class Gamestat implements OnInit{
    constructor() {}

    @Input() stat: GameStat | undefined;
    @Input() finished: boolean = false;
    sudoku: Sudoku | undefined;
    name: string = "";
    diff: string = ""; 
    mode: string = "";
    time: string = "";

    ngOnInit(): void {
        this.sudoku = this.stat!.sudoku!
        this.name = this.sudoku.desc!
        let first = this.sudoku.difficulty?.name?.substring(0,1).toUpperCase()
        this.diff = first! + this.sudoku.difficulty?.name?.substring(1)
        let last = this.sudoku.type!.substring(1).toLowerCase()
        this.mode = this.sudoku.type!.substring(0,1) + last
        this.time = this.stat!.duration
        console.log(this.stat)
    }

}