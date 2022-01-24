import { Duration } from './../interfaces/Durration';
import { GameStatsObject } from './../interfaces/GameStatsObject';
import { Observable } from 'rxjs';
import { Sudoku } from "../interfaces/Sudoku";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from 'src/environments/environment';
import { User } from "../interfaces/User";
import { duration } from 'moment';

@Injectable({
    providedIn: 'root'
})
export class GameStatsRequestService{
    constructor(
        private http: HttpClient
        ) { }

    url = environment.serviceDomain + '/gamestats/'

    saveGame(user : User, sudoku : Sudoku, time : number, currentResult : string){
        let token = localStorage.getItem('token')
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token!
            })
        }
        console.log(httpOptions)
        const duration : Duration = {
            time: time
        }
        const request : GameStatsObject = {
            sudoku: sudoku,
            duration : duration,
            user : user,
            currentResult : currentResult
        }
        let url : string = this.url + "/" + user.username + "/" + sudoku.id
        console.log(url)
        console.log(request)
        return this.http.put<GameStatsObject>(url, request, httpOptions)
    }
}