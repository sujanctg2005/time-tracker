import { Component } from '@angular/core';
import { TimeTrackerService }          from './services/timetracker.service';
import { User } from './model/user';
import { environment  as env} from '../environments/environment';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
     userName:string;
     password:string;
	   loginUser:number=0;
     appContext=env.appContext;
     projectTitle:string=env.projectTitle;
	 onClickAdd() { 
      console.log("logs");
      let user = new User();
      user.password=this.password;
      user.username=this.userName;
        this.timeTrackerService.login(user).subscribe(
        result => { 
                   
            if( result.error==null){
            	 localStorage.setItem('currentUser1', JSON.stringify({ username: this.userName}));
                  console.log("login successfully.............");
                  this.loginUser=1;
                
             }else{
               this.loginUser=0;
               console.log("login fail");
             }         
        },
         err => console.log("Error: " + err),
         () => console.log("DONE")
      );  

   
     

	 }
  logout(){
     localStorage.removeItem('currentUser1');
     this.loginUser=0;
  }
  constructor(private timeTrackerService: TimeTrackerService) {

       
           if (localStorage.getItem('currentUser1')) {
           	  this.loginUser=1;
             let user = JSON.parse(localStorage.getItem('currentUser1'));
             this.userName=user.username;
             let s= JSON.stringify(user);
             console.log(`user ${s}`);

        }else{


        	 this.loginUser=0;
        }

   }
}
