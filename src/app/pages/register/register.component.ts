import { UserRequestService } from './../../core/services/user.request.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { last } from 'rxjs/operators';
import { ConfirmedValidator } from 'src/app/core/validators/ConfirmedValidator';

@Component({
  selector: 'register-page',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterPageComponent{
    constructor(
        private formBuilder: FormBuilder,
        private userRequestService: UserRequestService,
        private router: Router
    ){}

    invalidForm : boolean = false
    couldNotCreate: boolean = false
    regForm = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required],
        reppassword: ['', Validators.required],
        email: ['', Validators.required],
        firstname: '',
        lastname: ''
      }, {
          validator: ConfirmedValidator('password', 'reppassword')
      });

    onSubmit() : void {
        if(this.regForm.valid){
            this.invalidForm = false
            let username = this.regForm.value.username
            let password = this.regForm.value.password
            let mail = this.regForm.value.email
            let firstname = this.regForm.value.firstname
            let lastname = this.regForm.value.lastname
            console.log(username)
            this.userRequestService.createUser(username, password, mail, firstname, lastname ).subscribe(
                data => this.router.navigate(["login"]),
                error  => this.couldNotCreate = true
                )
        }else{
            this.invalidForm = true
        }
    }
}
