import { Injectable } from "@angular/core"

@Injectable({
    providedIn: 'root'
})
export class TimeService{

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
}