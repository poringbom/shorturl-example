import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { GenerateComponent } from './generate.compoment';
import { SignUpComponent } from './sign-up.component';
import { LoginComponent } from './login.component';
import { AdminComponent } from './admin.component';

const appRoutes: Routes = [
  { path: 'generate', component: GenerateComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    GenerateComponent,
    SignUpComponent,
    LoginComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes)
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
