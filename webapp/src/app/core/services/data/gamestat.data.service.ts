import { Sudoku } from './../../interfaces/Sudoku';
import { Injectable } from "@angular/core";
import { Subject, BehaviorSubject } from 'rxjs';
import { GameStat } from '../../interfaces/Gamestat';

@Injectable({
    providedIn: 'root'
})
export class GameStatDataService{
    constructor() { }

    public gameStatDataDetails: GameStat = {
        currentResult: '',
        preview: '',
        sudoku: {},
        user: {
            username: ''
        },
        duration: '',
        finished: false
    };

    public subject = new Subject<GameStat>();

    private statSource = new BehaviorSubject(this.gameStatDataDetails);

    currentGameStat = this.statSource.asObservable();

    setGameStat(gamestat: GameStat){
        this.statSource.next(gamestat)
    }

    getGameStat(){
        return this.currentGameStat
    }
}