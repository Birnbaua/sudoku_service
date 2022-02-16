import { Sudoku } from './../../interfaces/Sudoku';
import { Injectable } from "@angular/core";
import { Subject, BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SudokuDataService{
    constructor() { }

    public sudokuDataDetails: Sudoku = {};
    public subject = new Subject<Sudoku>();

    private sudokuSource = new BehaviorSubject(this.sudokuDataDetails);

    currentSudoku = this.sudokuSource.asObservable();

    setSudoku(sudoku: Sudoku){
        this.sudokuSource.next(sudoku)
    }

    getSudoku(){
        return this.currentSudoku
    }
}