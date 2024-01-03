import { Injectable } from '@angular/core';
import { Observable, observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';

const API_URL = 'http://localhost:8080/api/test/';
const USER_API = 'http://localhost:8080/api/users/';
const CAR_API = 'http://localhost:8080/api/cars/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, 
    private token: TokenStorageService) { }

  private tokenStr: string = this.token.getToken();


  httpOptionsAuth = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json',
    'Authorization': this.tokenStr })
  };

  getPublicContent(): Observable<any> {
    return this.http.get(USER_API);
  }

  getUserById(userId): Observable<any> {
    return this.http.get(USER_API+userId);
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  saveUser(user): Observable<any> {
    return this.http.post(USER_API + 'update/' + user.id, {
      login: user.login,
      email: user.email,
      password: user.password,
      firstName: user.firstName,
      lastName: user.lastName,
      phone: user.phone,
      birthday: user.birthday
    }, httpOptions);
  }

  addCar(car,currentUser): Observable<any> {
    return this.http.post(CAR_API + '', 
    {
      id: currentUser.id,
      car: {
        model: car.model,
        licensePlate: car.licensePlate,
        year: car.year,
        color: car.color,
        country: car.country
      }
    }, httpOptions);
  }

  selectCar(carId,currentUser): Observable<any> {
    return currentUser.cars.find(opt => opt.id == carId);
  }

  editCar(car,currentUser): Observable<any> {
    return this.http.post(CAR_API + 'update/' + car.id, 
    {
      id: currentUser.id,
      car: {
        model: car.model,
        licensePlate: car.licensePlate,
        year: car.year,
        color: car.color,
        country: car.country
      }
    }, httpOptions);
  }

  deleteCar(carId,currentUser): Observable<any> {
    var car: any = {};
    car = this.selectCar(carId,currentUser);
    var url = CAR_API + 'remove/' + carId;
    return this.http.post(url, 
    {
      id: currentUser.id,
      car: {
        model: car.model,
        licensePlate: car.licensePlate,
        year: car.year,
        color: car.color,
        country: car.country
      }
    }, httpOptions);
  }

  deleteUser(userId): Observable<any> {
    var url = USER_API + 'remove/' + userId;
    return this.http.post(url, 
    {}, httpOptions);
  }
}
