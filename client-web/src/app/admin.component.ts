import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UriService, SharedVariableService, ConstantService } from './app.service';
import { URLSearchParams } from '@angular/http';

import swal from 'sweetalert2';

@Component({
  selector: 'app-rounter',
  templateUrl: './admin.component.html'
})
export class AdminComponent {

  private openProgress:boolean = false;
  private uriList:any = [];
  private noContent:boolean = true;

  constructor(private router:Router
    , private sharedVariableService:SharedVariableService
    , private uriService:UriService
    , private constantService:ConstantService) {
    let rootScope = this.sharedVariableService.getRootScope();
    if(!(rootScope && rootScope.userInfo)){
      this.router.navigate(['/login']);
    }else{
      this.fetchUriList();
    }
  }

  fetchUriList(): void {
    this.openProgress = true;
    let params: URLSearchParams = new URLSearchParams();
    //TODO paging
    params.set('page', '0');
    params.set('size', '999999');
    this.uriService.getUriListObservable(params).subscribe( response => {
      this.openProgress = false;
      if(response.content && response.content.length > 0){
        this.noContent = false;
        this.uriList = response.content
      }
    }, error => {
      this.openProgress = false;
      swal(
        'Some have a problem',
        error,
        'error'
      );
    });
  }
  
}
