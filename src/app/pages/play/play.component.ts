import { GameStatDataService } from './../../core/services/gamestat.data.service';
import { Router } from '@angular/router';
import { Sudoku } from './../../core/interfaces/Sudoku';
import { SudokuDataService } from './../../core/services/sudoku.data.service';
import { UserDataService } from './../../core/services/user.data.service';
import { GameStatsRequestService } from './../../core/services/gamestats.request.service';
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
    private router: Router
  ) {   }

  time: number = 0;
  display: string = "00:00:00";
  interval: any;
  timerOn: boolean = false;
  user : User | undefined;
  sudoku : Sudoku | undefined;
  standing : string | undefined;
  current : string = "";

  ngOnInit(): void {
    this.userDataService.getUser().subscribe(user => this.user = user)
    this.sudokuDataService.getSudoku().subscribe(sudoku => this.sudoku = sudoku)
    this.gameStatDataService.getGameStat().subscribe( (stat) => {
      if(stat.currentResult){
        this.display = stat.duration
        this.time = this.backTransform(stat.duration)
        this.current = stat.currentResult
      }
    })
    this.startTimer()
  }

  startTimer(){
    console.log("PlayComponent: started timer")
    this.timerOn = true;
    this.interval = setInterval(() => {
      this.time++;
      this.display=this.transform(this.time)
    }, 1000);
  }

  transform(value: number): string{
    const minutes: number = Math.floor(value / 60);
    const hours: number = Math.floor(value / 3600);
    let time: string = ""
    if(hours < 10 ){
      time = time + 0 
    }
    time = time + hours + ':'
    if(minutes - hours * 60 < 10){
      time = time + 0 
    }
    time = time + (minutes - hours * 60) + ':'
    if(value - minutes * 60 < 10){
      time = time + 0
    }
    time = time  + (value - minutes * 60) 
    return time;
  }

  backTransform(display: string): number{
    let hours: number = +display.split(":")[0]
    let mins: number = +display.split(":")[1]
    let secs: number = +display.split(":")[2]
    return secs + 60*mins + 3600*hours
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
    this.gameStatsRequestService.saveGame(this.user!, this.sudoku!, this.display!, this.standing!)
      .subscribe((res) => {
        console.log(res)
        this.router.navigate(['home'])
      }) 
  }

  endGame(){
  }
}
