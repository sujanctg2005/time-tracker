import { Component } from '@angular/core';
import { TimeTrackerService }          from './services/timetracker.service';
import { MessageService }          from './services/message.service';
import { User } from './model/user';
import { environment  as env} from '../environments/environment';
import { Subscription } from 'rxjs/Subscription';
 import { Ng2DeviceService } from 'ng2-device-detector';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
     userName:string;
     password:string;
	   loginUser:boolean=false;
     appContext=env.appContext;
     projectTitle:string=env.projectTitle;
     subscription: Subscription;
     deviceInfo =null;
     isIE:boolean=false;
     showError:boolean=false;
     errorMessage:string=""
     showProgressBar:boolean;
     dismissAlert(){   
        this.showError = false;
        this.errorMessage= "";    
     }
  
     showErrorAlert(error:string){
        this.showError = true; 
        this.errorMessage= error;       
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }
   
    progrssBar(state:boolean){
        this.showProgressBar=state;
    }

	 onClickAdd() { 
      console.log("logs");
      let user = new User();
      user.password=this.password;
      user.username=this.userName;
      this.progrssBar(true);
        this.timeTrackerService.login(user).subscribe(
        result => {                    
            if( result.error==null){
                 
            	   //localStorage.setItem('currentUser1', JSON.stringify({ username: this.userName}));
                  console.log("login successfully.............");
                  this.loginUser=true
                  env.currentUser=this.userName;
                  console.log("current user"+env.currentUser);
                  this.messageService.sendMessage('login done');
             }else{              
               this.loginUser=false;
               this.showErrorAlert("login fail");
               console.log("login fail");
               env.currentUser=env.logoutUser;
             }         
        },
         err => console.log("Error: " + err),
         () =>{console.log("DONE");this.progrssBar(false);}
      );  

   
     

	 }
  
   

  logout(){
   //  localStorage.removeItem('currentUser1');
     env.currentUser=env.logoutUser;
     this.loginUser=false;
  }
  constructor(private timeTrackerService: TimeTrackerService,private messageService: MessageService,private deviceService: Ng2DeviceService) {
         this.deviceInfo = this.deviceService.getDeviceInfo();
         console.log(this.deviceInfo.browser);      
          if(this.deviceInfo.browser=="ie"){
              console.log("this is IE");  
              this.isIE=true;
          }else{
              this.isIE=false;
          }
           if ( env.currentUser!==env.logoutUser) {
           	  this.loginUser=true;          
          }else{

          	 this.loginUser=false;
          }

   }
}
