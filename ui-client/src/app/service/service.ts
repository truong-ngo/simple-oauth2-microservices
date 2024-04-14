import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class Service {

  constructor(private http: HttpClient, private cookie: CookieService) {}

  public clientId = "ui-client";
  public clientSecret = "secret";
  public redirectUri = "http://localhost:4200";
  public authUrl = "http://127.0.0.1:8080";

  retrieveToken(code: string) {
    let params = new URLSearchParams();
    params.append("grant_type", "authorization_code");
    params.append("client_id", this.clientId);
    params.append("client_secret", this.clientSecret);
    params.append("redirect_uri", this.redirectUri);
    params.append("code", code);

    let headers = new HttpHeaders({"Content-type": "application/x-www-form-urlencoded; charset=utf-8"})

    this.http.post(`${this.authUrl}/oauth2/token`, params.toString(), {headers: headers})
      .subscribe(
        data => this.saveToken(data),
          error => {
            alert("Invalid credential");
          });

  }

  saveToken(token: any) {
    let expireDate = new Date().getTime() + (1000 * token["expires_in"]);
    this.cookie.set("id_token", token["id_token"], {expires: expireDate});
    window.location.href = this.redirectUri;
  }

  getResource(url: string) : Observable<any> {
    let headers = new HttpHeaders({
      'Content-type': 'application/json; charset=utf-8',
      'Authorization': 'Bearer '+ this.cookie.get('id_token')});
    return this.http.get(url, {headers: headers});
  }

  checkCredentials() {
    return this.cookie.check('id_token');
  }

  logout() {
    if (confirm("Do you want to logout")) {
      this.cookie.delete('id_token');
      window.open(`${this.authUrl}/auth/logout?client_id=${this.clientId}`, "_self");
    }
  }
}
