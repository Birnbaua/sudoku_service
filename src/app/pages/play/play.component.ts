import { TimeService } from './../../core/services/time.service';
import { SudokuRequestService } from './../../core/services/request/sudoku.request.service';
import { GameStatDataService } from './../../core/services/data/gamestat.data.service';
import { Router } from '@angular/router';
import { Sudoku } from './../../core/interfaces/Sudoku';
import { SudokuDataService } from './../../core/services/data/sudoku.data.service';
import { UserDataService } from '../../core/services/data/user.data.service';
import { GameStatsRequestService } from './../../core/services/request/gamestats.request.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/User';

@Component({
  selector: 'app-play',
  templateUrl: './play.component.html',
  styleUrls: ['./play.component.css']
})
export class PlayComponent implements OnInit {
  constructor(
    private gameStatsRequestService: GameStatsRequestService,
    private userDataService: UserDataService,
    private sudokuDataService: SudokuDataService,
    private gameStatDataService: GameStatDataService,
    private sudokuRequestService: SudokuRequestService,
    private timeService: TimeService
  ) {   }

  time: number = 0;
  display: string = "00:00:00";
  interval: any;
  timerOn: boolean = false;
  user : User | undefined;
  sudoku : Sudoku | undefined;
  standing : string | undefined;
  current : string = "";
  validate : boolean = false;
  isSaved : boolean = false ;
  isWrong : boolean = false;
  playable : boolean = true;

  ngOnInit(): void {
    this.userDataService.getUser().subscribe(user => this.user = user)
    this.sudokuDataService.getSudoku().subscribe(sudoku => this.sudoku = sudoku)
    this.gameStatDataService.getGameStat().subscribe( (stat) => {
      if(stat.currentResult){
        this.display = stat.duration
        this.time = this.timeService.backTransform(stat.duration)
        this.current = stat.currentResult
      }
      if(stat.finished){
        this.playable = false
      }
    })
    if(this.playable){
      this.startTimer()
    }
  }

  startTimer(){
    console.log("PlayComponent: started timer")
    this.timerOn = true;
    this.interval = setInterval(() => {
      this.time++;
      this.display=this.timeService.transform(this.time)
    }, 1000);
  }

  pauseGame(){
    this.toggleTimer()
  }

  toggleTimer() {
    if(this.timerOn){
      clearInterval(this.interval);
      this.timerOn = !this.timerOn 
    } else {
      this.startTimer()
    }
  }

  receiveStanding(newStanding: string) {
    this.standing = newStanding
  }

  saveGame(){
    this.gameStatsRequestService.saveGame(this.user!, this.sudoku!, this.display!, this.standing!, 0)
      .subscribe(() => {
        this.setAlert(false,false,true)
      }) 
  }

  validateGame(){
    this.sudokuRequestService.validateSudoku(this.sudoku?.id!, this.current, this.sudoku?.type!).subscribe((ret) => {
      if (ret){
        if (this.timerOn){
          this.toggleTimer()
        }
        this.setAlert(true,false,false)
        this.gameStatsRequestService.saveGame(this.user!, this.sudoku!, this.display!, this.standing!, 1).subscribe(() => {
          this.playable = false
        })
      } else {
        this.setAlert(false,true,false)
      }
    })
  }

  setAlert(isValid: boolean, isWrong: boolean, isSaved: boolean){
    this.validate = isValid
    this.isWrong = isWrong
    this.isSaved = isSaved
    setTimeout(() => {
      this.validate = false
      this.isWrong = false
      this.isSaved = false
    }, 5000)
  }
}
