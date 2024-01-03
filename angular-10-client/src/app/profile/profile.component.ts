import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { formatDate } from "@angular/common";
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { timer } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  displayedColumns: string[] = ['model','licensePlate','year','color','country','actions'];
  currentUser: any;
  userId;
  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  lineNumber = 0;

  constructor(private token: TokenStorageService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
  }

  onSubmit(): void {
    var btn = document.getElementById("btSave");
    btn.setAttribute("disabled","");
    
    this.userService.saveUser(this.currentUser).subscribe(
      data => {
        this.token.saveUser(data);
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        timer(5000).subscribe(() => {
          btn.removeAttribute("disabled");
          location.reload();
        });
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        timer(5000).subscribe(() => {
          btn.removeAttribute("disabled");
          location.reload();
        });
      }
    );
  }

  onAdd() {
    this.router.navigate(['new-car'], {relativeTo: this.route});
  }

  onEdit(carId) {
    this.token.saveCarId(carId);
    this.router.navigate(['edit-car'], {relativeTo: this.route});
  }

  format(date) {
    return formatDate(date,'dd/MM/yyyy HH:mm:ss','en-US');
  }

  onDelete(carId) {
    if (confirm("Are you sure you want to delete this car?") == true) {
        this.userService.deleteCar(carId,this.currentUser).subscribe(
        data => {
          this.token.saveUser(data);
          console.log(data);
          this.isSuccessful = true;
          location.reload();
        },
        err => {
          this.errorMessage = err.error.message;
          this.isSuccessful = false;
        }
      );
    }
  }
}
