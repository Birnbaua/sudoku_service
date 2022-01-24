import { catchError } from 'rxjs/operators';
import { Duration } from './../interfaces/Durration';
import { GameStatsRequest } from '../interfaces/GameStatsRequest';
import { Observable } from 'rxjs';
import { Sudoku } from "../interfaces/Sudoku";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from 'src/environments/environment';
import { User } from "../interfaces/User";
import { duration } from 'moment';
import { Gamestat } from '../components/gamestat/gamestat.component';
import { GameStatsContent } from '../interfaces/GameStatsContent';

@Injectable({
    providedIn: 'root'
})
export class GameStatsRequestService{
    constructor(
        private http: HttpClient
        ) { }

    url = environment.serviceDomain + '/gamestats/'

    saveGame(user : User, sudoku : Sudoku, duration : string, currentResult : string){
        let token = localStorage.getItem('token')
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token!
            })
        }
        console.log(httpOptions)
        const request : GameStatsRequest = {
            duration : duration,
            currentResult : currentResult
        }
        let url : string = this.url + user.username + "/" + sudoku.id
        console.log(url)
        console.log(request)
        return this.http.put<GameStatsRequest>(url, request, httpOptions)
    }

    getStatsByUser(user: User){
        let token = localStorage.getItem('token')
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token!
            })
        }
        let url : string = this.url + user.username
        return this.http.get<GameStatsContent>(url, httpOptions)
    }
}