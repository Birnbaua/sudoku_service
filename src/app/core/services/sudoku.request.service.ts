import { SudokuWrapper } from '../interfaces/SudokuWrapper';
import { Difficulty } from '../interfaces/Difficulty';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, map, retry } from 'rxjs/operators';
import { Sudoku } from "../interfaces/Sudoku";
import { Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SudokuRequestService{
    constructor(
        private http: HttpClient
        ) { }

    url = environment.serviceDomain + '/sudoku/'

    getSudokuById(id: number): Observable<Sudoku>{
        console.log('In Function: SudokuRequestService.getSudokuById('+id+')');
        return this.http.get<Sudoku>(this.url+ id);
    }
}