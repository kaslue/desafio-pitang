import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../_services/token-storage.service';
import { formatDate } from "@angular/common";
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { timer } from 'rxjs';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  currentUser: any;
  content: any;
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
    this.userService.getUserById(this.token.getUserId()).subscribe(
      data => {
        this.currentUser = data;
        if (this.currentUser.id == this.token.getUserId()) {
          this.token.saveUser(this.currentUser);
        }
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
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

  format(date) {
    return formatDate(date,'dd/MM/yyyy HH:mm:ss','en-US');
  }

  goBack() {
    this.router.navigate(['home']);
  }
}
