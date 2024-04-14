import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Service} from "./service/service";
import {NgIf} from "@angular/common";
import {ContentComponent} from "./component/content/content.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf, ContentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  public isLoggedIn = false;

  constructor(private service: Service) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.service.checkCredentials();
    let i = window.location.href.indexOf("code");
    if (!this.isLoggedIn && i != -1) {
      this.service.retrieveToken(window.location.href.substring(i + 5));
    }
  }

  login() {
    window.location.href =
      `${this.service.authUrl}/oauth2/authorize?response_type=code&scope=openid%20user.read%20user.write&client_id=${this.service.clientId}&redirect_uri=${this.service.redirectUri}`
  }

  logout() {
    this.service.logout();
  }
}
