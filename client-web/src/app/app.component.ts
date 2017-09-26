import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rounter',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(router:Router) {
    router.navigate(['/generate']);
  }
}
