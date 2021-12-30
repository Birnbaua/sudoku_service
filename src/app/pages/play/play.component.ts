import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-play',
  templateUrl: './play.component.html',
  styleUrls: ['./play.component.css']
})
export class PlayComponent implements OnInit {
  constructor() { }

  time: number = 0;
  display: string = "00:00:00";
  interval: any;
  timerOn: boolean = false;

  ngOnInit(): void {
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

  toggleTimer() {
    if(this.timerOn){
      clearInterval(this.interval);
      this.timerOn = !this.timerOn 
    } else {
      this.startTimer()
    }
  }
}
