import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { formatDate } from "@angular/common";
import { ActivatedRoute, Route, Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  displayedColumns: string[] = ['login','firstName','lastName', 'email', 'birthday','phone','dhLastUpdate','actions'];
  currentUsers: any;
  content: string;
  errorMessage : string;

  constructor(private userService: UserService,
    private router: Router,
    private storage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe(
      data => {
        this.currentUsers = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  format(date) {
    return formatDate(date,'dd/MM/yyyy HH:mm:ss','en-US');
  }

  onAdd() {
    this.router.navigate(['register']);
  }

  onEdit(userId) {
    this.storage.saveUserId(userId);
    this.router.navigate(['home/edit-user']);
  }

  onDelete(userId) {
    var currentUser: any;
    currentUser = this.storage.getUser();
    if (userId == currentUser.id) {
      alert("The user you trying to delete is logged in, please log out before doing it!");
    } else {
      if (confirm("Are you sure you want to delete this user?") == true) {
        this.userService.deleteUser(userId).subscribe(
          data => {
            console.log(data);
            location.reload();
          },
          err => { 
            this.errorMessage = err.error.message;
          }
        );
      }
    }
  }
}
