import { SudokuWrapper } from '../../interfaces/SudokuWrapper';
import { Difficulty } from '../../interfaces/Difficulty';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, map, retry } from 'rxjs/operators';
import { Sudoku } from "../../interfaces/Sudoku";
import { Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SudokuRequestService{
    constructor(
        private http: HttpClient
        ) { }

    difficultyMap = new Map([
        ["Easy", '1'],
        ["Medium", '2'],
        ["Hard", '3']
    ])

    url = environment.serviceDomain + '/sudoku/'

    getSudokuById(id: number): Observable<Sudoku>{
        console.log('In Function: SudokuRequestService.getSudokuById('+id+')');
        return this.http.get<Sudoku>(this.url+ id);
    }

    getSudokuIdsBySettings(diff : string | undefined, mode : string){
        let url : string = this.url
        let first = true
        if(diff != ''){
            diff = this.difficultyMap.get(diff!)
            url = url + "?difficulty="+ diff
            first = false
        }
        if(mode != ''){
            if(first){
                url = url + "?type="+ mode.toUpperCase()
            }else{
                url = url + "&type="+ mode.toUpperCase()
            }
            
        }
        console.log(url)
        return this.http.get<SudokuWrapper>(url);
    }

    postSudoku(sud : Sudoku){
        let token = localStorage.getItem('token')
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token!
            })
        }
        console.log(sud)
        return this.http.post<Sudoku>(this.url, sud, httpOptions)
    }

    validateSudoku(id: number, current: string, type: string){
        let token = localStorage.getItem('token')
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token!
            })
        }
        let url : string = this.url
        url = url + id + "/" + "validate" + "/?solved=" + current + "&type=" + type.toUpperCase()
        return this.http.get<any>(url,httpOptions)
    }
    
}