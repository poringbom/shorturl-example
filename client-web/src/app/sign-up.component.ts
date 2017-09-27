import { Component } from '@angular/core';
import { UserService, ConstantService } from './app.service';
import { Router } from '@angular/router';

import swal from 'sweetalert2';


@Component({
  selector: 'app-rounter',
  templateUrl: './sign-up.component.html'
})
export class SignUpComponent {

  private username: String;
  private password: String;
  
  constructor(private userService: UserService, private router:Router, private constantService:ConstantService) {  }

  signup(): void {
    let params = {
      'username': this.username,
      'password': this.password
    };
    this.userService.signUpObservable(params).subscribe( response => {
      if(response.code != this.constantService.http_error_code) {
        swal(
          'Sign up',
          'Create user success',
          'success'
        );
        this.router.navigate(['/login']);
      }else {
        swal(
          'Some have a problem',
          response.message,
          'error'
        );
        this.username = null;
        this.password = null;
      }
    },error => {
      swal(
        'Some have a problem',
        error,
        'error'
      );
      this.username = null;
      this.password = null;
    });

  }
  
}
