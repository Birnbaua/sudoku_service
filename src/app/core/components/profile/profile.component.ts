import { UserService } from './../../services/user.service';
import { Component, Output, EventEmitter, OnInit } from '@angular/core';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit{
  constructor(private UserService: UserService) {}  

  ngOnInit(): void {
    const jwtToken = localStorage.getItem('token')
    console.log(jwtToken)
  }
}
