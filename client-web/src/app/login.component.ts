import { Component } from '@angular/core';
import { UserService, ConstantService, SharedVariableService } from './app.service';
import { Router } from '@angular/router';

import swal from 'sweetalert2';


@Component({
  selector: 'app-rounter',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  private username: String;
  private password: String;

  constructor(private userService: UserService
    , private router:Router
    , private constantService:ConstantService
    , private sharedVariableService:SharedVariableService) {  }

  login(): void {
    let params = {
      'username': this.username,
      'password': this.password
    };
    this.userService.loginObservable(params).subscribe( response => {
      if(response.code != this.constantService.http_error_code) {
        this.router.navigate(['/admin']);
        let rootScope = this.sharedVariableService.getRootScope();
        rootScope.userInfo = response;
        this.sharedVariableService.setRootScope(rootScope);
      }else {
        swal(
          'Some have a problem',
          response.message,
          'error'
        )
        this.username = null;
        this.password = null;
      }
    },error => {
      swal(
        'Some have a problem',
        error,
        'error'
      )
      this.username = null;
      this.password = null;
    });

  }
  
}
