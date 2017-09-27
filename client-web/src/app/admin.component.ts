import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConstantService, SharedVariableService } from './app.service';


@Component({
  selector: 'app-rounter',
  templateUrl: './admin.component.html'
})
export class AdminComponent {

  constructor(private router:Router, private sharedVariableService:SharedVariableService) {
    let rootScope = this.sharedVariableService.getRootScope();
    if(!(rootScope && rootScope.userInfo)){
      this.router.navigate(['/login']);
    }
  }
  
}
