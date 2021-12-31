import { Observable } from 'rxjs';
import { Sudoku } from "../interfaces/Sudoku";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from 'src/environments/environment';
import { User } from "../interfaces/User";

@Injectable({
    providedIn: 'root'
})
export class GameStatsRequestService{
    constructor(
        private http: HttpClient
        ) { }

    url = environment.serviceDomain + '/gamestats/'

    saveGame(user : User, sudoku : Sudoku){
        console.log("GamestatsRequestService: saveGame()")
        console.log(user)
        console.log(sudoku)
        return ""
    }
}