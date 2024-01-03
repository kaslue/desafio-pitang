import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const CAR_ID = 'car-id';
const USER_ID = 'user-id';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }

  public saveCarId(id): void {
    window.sessionStorage.removeItem(CAR_ID);
    window.sessionStorage.setItem(CAR_ID, id);
  }

  public getCarId(): String {
    var id = window.sessionStorage.getItem(CAR_ID);
    return id;
  }

  public saveUserId(id): void {
    window.sessionStorage.removeItem(USER_ID);
    window.sessionStorage.setItem(USER_ID, id);
  }

  public removeUserId(id): void {
    window.sessionStorage.removeItem(USER_ID);
  }

  public getUserId(): String {
    return window.sessionStorage.getItem(USER_ID);
  }
}
