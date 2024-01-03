import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../_services/token-storage.service';
import { formatDate } from "@angular/common";
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';


@Component({
  selector: 'app-new-car-form',
  templateUrl: './new-car-form.component.html',
  styleUrls: ['./new-car-form.component.css']
})
export class NewCarFormComponent implements OnInit {
  form: any = {};
  currentUser: any;
  errorMessage = '';
  isSuccessful = false;
  constructor(
    private token: TokenStorageService,
    private router: Router,
    private userService: UserService,
    private tokenStorage: TokenStorageService,
  ) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
  }
  format(date) {
    return formatDate(date,'dd/MM/yyyy HH:mm:ss','en-US');
  }

  back() {
    this.router.navigate(['profile']);
  }

  onSubmit(): void {
    this.userService.addCar(this.form,this.currentUser).subscribe(
      data => {
        this.tokenStorage.saveUser(data);
        console.log(data);
        this.isSuccessful = true;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSuccessful = false;
      }
    );
  }
}
