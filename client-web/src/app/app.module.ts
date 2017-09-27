import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { GenerateComponent } from './generate.compoment';
import { SignUpComponent } from './sign-up.component';
import { LoginComponent } from './login.component';
import { AdminComponent } from './admin.component';

import { UserService, ConstantService, SharedVariableService, UriService } from './app.service';

const appRoutes: Routes = [
  { path: '', component: GenerateComponent },
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
    RouterModule.forRoot(appRoutes),
    FormsModule,
    HttpModule
  ],
  providers: [
    UserService, 
    ConstantService,
    SharedVariableService,
    UriService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
