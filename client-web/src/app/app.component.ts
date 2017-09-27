import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { SharedVariableService } from './app.service';


@Component({
  selector: 'app-rounter',
  templateUrl: './app.component.html'
})
export class AppComponent {

  constructor(private router:Router, private sharedVariableService:SharedVariableService) {

  }

  isLogin(): boolean {
    let rootScope = this.sharedVariableService.getRootScope();
    return rootScope && rootScope.userInfo;
  }

  logout(): void {
    let rootScope = this.sharedVariableService.getRootScope();
    delete rootScope.userInfo;
    this.sharedVariableService.setRootScope(rootScope);
    this.router.navigate(['/']);
  }
  
}
