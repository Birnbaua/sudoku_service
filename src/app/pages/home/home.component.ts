import { FormControl, FormGroup } from '@angular/forms';
import { TimeService } from './../../core/services/time.service';
import { StaticListsService } from '../../core/services/staticlists.service';
import { DifficultyRequestService } from './../../core/services/request/difficulty.request.service';
import { Difficulty } from './../../core/interfaces/Difficulty';
import { UserDataService } from '../../core/services/data/user.data.service';
import { AuthService } from 'src/app/core/services/request/auth.request.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/User';
import { GameStatsRequestService } from 'src/app/core/services/request/gamestats.request.service';
import { Gamestat } from 'src/app/core/components/gamestat/gamestat.component';
import { GameStatsContent } from 'src/app/core/interfaces/GameStatsContent';
import { GameStat } from 'src/app/core/interfaces/Gamestat';

@Component({
    selector: 'home-page',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
  })
  
  export class HomePageComponent implements OnInit{
    constructor(
      private userDataService: UserDataService,
      private gameStatsRequestService: GameStatsRequestService,
      private difficultyRequestService: DifficultyRequestService,
      private staticlistsService: StaticListsService,
      private timeService: TimeService
    ){}

    user: User | undefined;
    statArray: GameStat[] | undefined;
    title = 'Sudoku';
    difficultyArray: Array<Difficulty> = []
    sudokuTypes: Array<String> = []
    nrFinished: string = ""
    avgDiff: string = ""
    avgTime: string = ""
    currDiff: string = "All"
    currType: string = 'All'
    
    ngOnInit(): void {
      this.userDataService.getUser().subscribe((user) => {
        console.log(user);
        this.gameStatsRequestService.getStatsByUser(user).subscribe((stats) => {
          this.statArray = stats.content;
          this.updateStats()
          console.log(this.statArray);
        });
      });
      this.difficultyRequestService.getAll().subscribe((diffArr) => {
        this.difficultyArray = diffArr;
      });
      this.sudokuTypes = this.staticlistsService.getSudokuTypes();
    }

    updateStats(){
      console.log(this.currDiff)
      console.log(this.currType)
      let stats = this.statArray!.filter(stat => stat.finished)
      if(this.currDiff !='All'){
        stats = stats.filter(stat => stat.sudoku.difficulty?.name == this.currDiff);
      }
      if(this.currType != 'All'){
        stats = stats.filter(stat => stat.sudoku.type == this.currType)
      }
      let time: number = 0;
      let diff: number = 0;
      let nr: number = 0;
      stats.forEach((stat) => {
        nr++;
        time = time + this.timeService.backTransform(stat.duration);
        diff = diff + stat.sudoku.difficulty?.no!;
      });
      if(nr == 0){
        this.nrFinished = "0";
        this.avgTime = "No Data";
        this.avgDiff = "No Data";
      }else{
        this.avgDiff = (diff/nr).toString();
        this.avgTime = this.timeService.transform(time/nr);
        this.nrFinished = nr.toString();
      }
    }

    getDiffName(diff: Difficulty){
      return diff.name!.charAt(0).toUpperCase() + diff.name!.slice(1);
    }

    compareDiff(diff1:Difficulty, diff2:Difficulty){
      return diff1.name == diff2.name;
    }
  }
  