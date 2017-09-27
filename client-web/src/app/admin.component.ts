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
  private page:number = 0;
  private size:number = 10;
  private total:number = 0;
  private paging:any = [];
  private firstPage:boolean = true;
  private lastPage:boolean = false;

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

  cutWording(text): string {
    if(text && text.length > 50) {
      text = text.substr(0,50)+'...';
    }
    return text;
  }

  pageNext(): void {
    this.page++;
    this.fetchUriList();
  }

  pagePrevious(): void {
    this.page++;
    this.fetchUriList();
  }

  pageGoto(page): void {
    this.page = page;
    this.fetchUriList();
  }

  fetchUriList(): void {
    this.openProgress = true;
    let params: URLSearchParams = new URLSearchParams();
    params.set('page', String(this.page));
    params.set('size', String(this.size));
    this.uriService.getUriListObservable(params).subscribe( response => {
      this.openProgress = false;
      if(response.content && response.content.length > 0){
        this.noContent = false;
        this.uriList = response.content
        this.total = response.totalPages;
        for(let i=0;i<this.total;i++){
          this.paging.push(i);
        }
        this.firstPage = response.first;
        this.lastPage = response.last;
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
