import { UserDataService } from './../../services/user.data.service';
import { UserRequestService } from './../../services/user.request.service';
import { Component, Output, EventEmitter, OnInit } from '@angular/core';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit{
  constructor(
      private userRequestService: UserRequestService,
      private userDataServkce: UserDataService
    ) {}  

    username : string = ""

  ngOnInit(): void {
    const jwtToken = localStorage.getItem('token')
    if(jwtToken != null){
      this.userRequestService.getUserByToken(jwtToken).subscribe(res => this.userDataServkce.setUser(res))
      this.userDataServkce.getUser().subscribe(user => this.username = user.username)
    }
  }
}
