import { SudokuWrapper } from './../interfaces/SudokuWrapper';
import { Difficulty } from './../interfaces/Difficulty';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, map, retry } from 'rxjs/operators';
import { Sudoku } from "../interfaces/Sudoku";
import { Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class SudokuService{
    constructor(
        private http: HttpClient
        ) { }

    serviceUrl = 'http://localhost:8080/api/sudoku/'

    getSudokuById(id: number): Observable<Sudoku>{
        console.log('In Function: SudokuService.getSudokuById('+id+')');
        return this.http.get<Sudoku>(this.serviceUrl + id);
    }
}