import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'register-page',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterPageComponent{
    constructor(
        private formBuilder: FormBuilder,
        private router : Router
    ){}

    invalidForm : boolean = false
    regForm = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required],
        reppassword: ['', Validators.required],
        email: ['', Validators.required],
        firstname: '',
        lastname: ''
      });

    onSubmit() : void {
        if(this.regForm.valid){
            //TODO
            console.log(this.regForm.value)
            this.invalidForm = false
            this.router.navigate(['/login']);
        }else{
            this.invalidForm = true
        }
    }
}
