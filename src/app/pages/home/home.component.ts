import { UserDataService } from './../../core/services/user.data.service';
import { AuthService } from 'src/app/core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/User';
import { GameStatsRequestService } from 'src/app/core/services/gamestats.request.service';
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
      private authService: AuthService,
      private userDataService: UserDataService,
      private gameStatsRequestService: GameStatsRequestService
    ){}
    user: User | undefined;
    statArray: GameStat[] | undefined;
    title = 'Sudoku';

    
    ngOnInit(): void {
      this.userDataService.getUser().subscribe((user) => {
        console.log(user)
        this.gameStatsRequestService.getStatsByUser(user).subscribe((stats) => {
          this.statArray = stats.content
          console.log(this.statArray)
        })
      })
    }

    
  }
  