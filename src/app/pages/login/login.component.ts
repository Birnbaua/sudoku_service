import { catchError } from 'rxjs/operators';
import { SudokuRequestService } from '../../core/services/sudoku.request.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'login-page',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginPageComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ){}

  invalidCreds: boolean = false
  invalidForm : boolean = false
  loginForm = this.formBuilder.group({
    name: ['', Validators.required],
    password: ['', Validators.required],
    remember: false
  });

  ngOnInit(): void {
    this.authService.logout()
    this.invalidForm = false
    this.invalidCreds = false
  }

  onSubmit() : void {
    if( this.loginForm.valid){
      this.invalidForm = false
      this.invalidCreds = false
      this.authService.authenticate(this.loginForm.value.name, this.loginForm.value.password).subscribe(
        res => this.authService.setSession(res),
        err => this.invalidCreds = true
      );
    } else {
      this.invalidForm = true
    }
    this.loginForm.reset()
  }
}
