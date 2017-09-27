import { Component } from '@angular/core';
import { UriService, ConstantService } from './app.service';
import { Router } from '@angular/router';

import swal from 'sweetalert2';

@Component({
  selector: 'app-rounter',
  templateUrl: './generate.compoment.html'
})
export class GenerateComponent {

  private uri:string;
  private shortUri:string;
  private openProgress:boolean = false;

  url_parttern = /^((https?|ftp|smtp):\/\/)?(www.)?[a-z0-9]+(\.[a-z]{2,}){1,3}(#?\/?[a-zA-Z0-9#]+)*\/?(\?[a-zA-Z0-9-_]+=[a-zA-Z0-9-%]+&?)?$/;

  constructor(private uriService:UriService, private constantService:ConstantService) { 
    
  }

  copy(): void {
    let textArea = document.createElement("textarea");
    textArea.value = this.shortUri;
    textArea.style.position = 'fixed';
    textArea.style.top = "0";
    textArea.style.left = "0";
    textArea.style.width = '2em';
    textArea.style.height = '2em';
    textArea.style.padding = "0";
    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';
    textArea.style.background = 'transparent';
    document.body.appendChild(textArea);
    textArea.select();
    try {
      var successful = document.execCommand('copy');
      var msg = successful ? 'successful' : 'unsuccessful';
      console.log('Copying text command was ' + msg);
    } catch (err) {
      console.log('Oops, unable to copy');
    }
    document.body.removeChild(textArea);
  }

  dismiss(): void {
    this.shortUri = null;
  }

  invaliURL(): boolean {
    return !this.url_parttern.test(this.uri);
  }

  generate(): void {
    this.openProgress = true;
    this.uriService.generateUriObservable({'uri': this.uri}).subscribe( response => {
      this.openProgress = false;
      if(response.code != this.constantService.http_error_code) {
        this.shortUri = response.shortUri;
      }else{
        swal(
          'Some have a problem',
          response.message,
          'error'
        );
      }
    },error => {
      this.openProgress = false;
      swal(
        'Some have a problem',
        error,
        'error'
      );
      this.uri = null;
    });

  }

}
