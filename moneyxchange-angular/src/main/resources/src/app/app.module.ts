import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule }   from '@angular/router';
import { OAuthModule } from 'angular-oauth2-oidc';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AppComponent } from './app.component';
import { FooComponent } from './foo/foo.component';
import { HomeComponent } from './home/home.component';

import { StompService } from './app.service';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooComponent
  ],
  imports: [
    BrowserModule,
    NgbModule.forRoot(),
    FormsModule,
    HttpModule,
    HttpClientModule,
    OAuthModule.forRoot(),    
    RouterModule.forRoot([
     { path: '', component: HomeComponent }])
  ],
  providers: [StompService],
  bootstrap: [AppComponent]
})
export class AppModule { }
