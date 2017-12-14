import { Component, OnInit } from '@angular/core';
import * as moment from 'moment'
import { TimeTrackerService }          from '../services/timetracker.service';
import { TaskCategory } from '../model/task-category';
import { TaskType } from '../model/task-type';
import { Task } from '../model/task';
import { Ticket } from '../model/ticket';
import { Medium } from '../model/medium';
import { Subtype } from '../model/subtype';
import { environment  as env} from '../../environments/environment';
import { MessageService }          from '../services/message.service';
import { Subscription } from 'rxjs/Subscription';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'time-entry-form',
  templateUrl: './time-entry.component.html',
  styleUrls: [ './time-entry.component.css' ]
})


export class TimeEntryComponent implements OnInit {
  taskTypeList:TaskType[];
  categoryList:TaskCategory[];
  mediumList:Medium[];
  taskList:Task[]; 
  subtypeList:Subtype[]; 
  selectedSubtype:Subtype= new Subtype();
  selectedCategory:TaskCategory = new TaskCategory(); 
  selectedTaskType:TaskType = new TaskType();
  selectedMedium:Medium = new Medium();
  incidentId: string;
  hour: number;
  comments:string;
  taskDate:string;
  formMode:FormMode;
  taskId:number; 
  totaHours:number=0;
  saveSuccess:boolean;
  saveFail:boolean;
  inCompleteForm:boolean;
  deleteFail:boolean;
  showProgressBar:boolean;
  showTaskListProgressBar:boolean=false;
  appContext=env.appContext;
  subscription: Subscription;
  validTicket:boolean=true;
  assignedGroup:string;
   showError:boolean=false;
   showSuccess:boolean=false;
   alertMessage:string="";
   ticketIDPlaceHolder:string="Ticket ID";
   closeResult: string;
   /*autoSuggestionID = ["CRQ000000019805",
                        "CRQ000000019850",
                        "CRQ000000019915",
                        "CRQ000000020002",
                        "CRQ000000020018",
                        "CRQ000000020020",
                        "CRQ000000020021",
                        "CRQ000000020022",
                        "CRQ000000020023",
                        "CRQ000000020024",
                        "CRQ000000020025",
                        "CRQ000000020026",
                        "CRQ000000020032",
                        "CRQ000000020103",
                        "CRQ000000020107",
                        ];
*/
  constructor(private timeTrackerService: TimeTrackerService,
    private messageService: MessageService,
    private modalService: NgbModal) {
     this.subscription = this.messageService.getMessage().subscribe(message => { this.ngOnInit(); });
   }
  environ=env;



/*confirmation model */
open(content, ) {
    this.modalService.open(content).result.then((result) => {


      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

/*confirmation model end */


  deleteTask(task:Task,content:any){
          
          this.modalService.open(content).result.then((result) => {
               console.log("Delete task id: "+task.taskId +" result: "+result);    
                if (result ==='Yes') {
                    this.showTaskListProgressBar=true;
                    this.timeTrackerService.deleteTask(task).subscribe(
                    result => { 
                          if( result.error==null){
                             console.log("Task deleted successfully");
                              this.reloadTaskList();
                              this.showValidAlert("Task deleted successfully");
                           }else{
                               this.showTaskListProgressBar=false;
                               this.showDeleteFailAlert();
                             console.log("Failed to delete the task, status code:"+result.status);
                           }
                     
                    }, err => console.log("Error: " + err),() => {console.log("DONE"); }
                  );  
                }
                 
             

           
          }, (reason) => {
                 ///   this.closeResult = `Dismissed `;
          });
     }


  resetForm(){
       this.incidentId="";
       this.hour=null;
       this.comments="";
       this.taskDate=this.getTodayDate();
       this.formMode=FormMode.NEW;
       if(this.categoryList!=null){
          this.selectedCategory=this.categoryList[0];
       }
      if(this.taskTypeList!=null){
          this.selectedTaskType=this.taskTypeList[0];
       }
       if(this.mediumList!=null){
          this.selectedMedium=this.mediumList[0];
       }
       if(this.subtypeList!=null){
          this.selectedSubtype=this.subtypeList[0];
       }
       
      
      
 
  }

  getUser():any{   
       console.log("current user:"+ env.currentUser);
        return   env.currentUser;
   }

   getDummyTaskCategory():TaskCategory{
    let taskCategory:TaskCategory= new  TaskCategory();
      taskCategory.categoryId=-1;
      taskCategory.categoryName="Select a Ticket Type";
      return taskCategory;
   }
    getDummySubTask():Subtype{
    let subtype:Subtype= new  Subtype();
      subtype.subtypeId=-1;
      subtype.subtypeName="Select a Sub Category";
      return subtype;
   }
    getDummyTaskType():TaskType{
    let taskType:TaskType= new  TaskType();
      taskType.taskTypeId=-1;
      taskType.taskTypeName="Select a  Category";
      return taskType;
   }
   getDummyMedium():Medium{
    let medium:Medium= new  Medium();
      medium.mediumId=-1;
      medium.mediumName="Select a Request Triggered via";
      return medium;
   }

  ngOnInit() {
       console.log("current user.....:#"+this.getUser()+ "#")
      if(   this.getUser()!=env.logoutUser){

         console.log("loading data.....:#"+this.getUser()+ "#")
                     //setting default value
                this.resetForm();
            
               // load category list 
                this.timeTrackerService.getCategoryList().subscribe(
                  result => { 
                    
                   let temp : TaskCategory[]= new Array(this.getDummyTaskCategory()) ;
                
                    this.categoryList = temp.concat(result);

                    
                    this.selectedCategory=this.categoryList[0];
                  },
                   err => console.log("Error: " + err),
                   () => console.log("DONE")
                );  

                //end

                 // load TaskType list 
                this.timeTrackerService.getTaskTypeList().subscribe(
                  result => { 
                     let temp : TaskType[]= new Array(this.getDummyTaskType()) ;

                    this.taskTypeList = temp.concat(result);
                    this.taskTypeList.concat([this.getDummyTaskType()]);
                      this.selectedTaskType=this.taskTypeList[0];
                  },
                   err => console.log("Error: " + err),
                   () => console.log("DONE")
                );  

                //end

                 // load Medium list 
                this.timeTrackerService.getMediumList().subscribe(
                  result => { 
                   let temp : Medium[]= new Array(this.getDummyMedium()) ;
                    this.mediumList = temp.concat(result);
                     this.mediumList.concat([this.getDummyMedium()]);                   
                     this.selectedMedium=this.mediumList[0];
                  },
                   err => console.log("Error: " + err),
                   () => console.log("DONE")
                );  

                //end

               // load Subtype list 
                this.timeTrackerService.getSubtypeList().subscribe(
                  result => { 
                     let temp : Subtype[]= new Array(this.getDummySubTask()) ;
                    this.subtypeList  = temp.concat(result);
                      this.subtypeList.concat([this.getDummySubTask()]);
                     this.selectedSubtype=this.subtypeList[0];
                  },
                   err => console.log("Error: " + err),
                   () => console.log("DONE")
                );  

                //end


                //load all user task by date
                this.reloadTaskList();

      }

     

  }

showErrorAlert(error:string){
        this.showError = true; 
        this.alertMessage= error;       
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }
 showValidAlert(error:string){
        this.showSuccess = true; 
        this.alertMessage= error;       
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }  

   validateTicket(){
        this.validTicket=true;
       if( (this.selectedCategory.ticketVerificationInfo!=undefined   || this.selectedCategory.ticketVerificationInfo!=null ) && this.selectedCategory.ticketVerificationInfo!=""){                 
             if(this.incidentId!=null &&  this.incidentId.trim() !=""){

               this.timeTrackerService.validateTicket(this.selectedCategory.ticketVerificationInfo,this.incidentId ).subscribe(
                  result => { 
                     if(result==null || result.length==0 || result[0]==null){
                        this.validTicket=false;
                         console.log("invalid ticket");
                         this.showErrorAlert("invalid ticket");
                     }else{
                        this.validTicket=true;     
                        this.assignedGroup=  result[1];    
                        console.log("valid ticket: "+ this.assignedGroup);    
                        this.showValidAlert("Valid Ticket");
                     }
                  },
                   err => console.log("Error: " + err),
                   () => console.log("DONE")
                );  
             }

               
        }
   }
   onChangeTaskDate(){
       this.getTaskListByUser(this.taskDate);
   }
   onSelectCatgoryList(categoryId) { 
       this.selectedCategory = null;
        for (var i = 0; i < this.categoryList.length; i++)
        {
          if (this.categoryList[i].categoryId ==categoryId) {
            this.selectedCategory = this.categoryList[i];
             if(this.selectedCategory.ticketVerificationInfo!=null){
                  this.validTicket=false;
             }
             if(this.selectedCategory.ticketPattern!=null && this.selectedCategory.ticketPattern!=""){
                this.ticketIDPlaceHolder="Ticket ID e.g: "+this.selectedCategory.ticketPattern;
             }else{
                this.ticketIDPlaceHolder="Ticket ID";
             }
             
             
             this.incidentId="";
            break;
          }
        }
    
    }

     onSelecttaskTypeList(taskTypeId) { 

       
       this.selectedTaskType = null;
        for (var i = 0; i < this.taskTypeList.length; i++)
        {
          if (this.taskTypeList[i].taskTypeId == taskTypeId) {
            this.selectedTaskType = this.taskTypeList[i];
            break;
          }
        }
    
    }
     onSelectMediumList(mediumId) { 

       
       this.selectedMedium = null;
        for (var i = 0; i < this.mediumList.length; i++)
        {
          if (this.mediumList[i].mediumId == mediumId) {
            this.selectedMedium = this.mediumList[i];
            break;
          }
        }
    
    }

    onSelectSubtypeList(subtypeId) { 

       
       this.selectedSubtype = null;
        for (var i = 0; i < this.subtypeList.length; i++)
        {
          if (this.subtypeList[i].subtypeId == subtypeId) {
            this.selectedSubtype = this.subtypeList[i];
            break;
          }
        }
    
    }


     reloadTaskList(){
       console.log("loading current tasks");
       this.getTaskListByUser(this.taskDate);
     }

     dismissAlert(){   
        this.saveSuccess = false;
        this.saveFail = false;
        this.deleteFail = false;
        this.inCompleteForm=false;
        this.showError=false;
        this.alertMessage="";
        this.showSuccess=false;
     }
  
     showSuccessAlert(){
        this.saveSuccess = true;         
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }
      showFailtAlert(){
        this.saveFail = true;         
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }
      showDeleteFailAlert(){
        this.deleteFail = true;         
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 5000);
     }
      
      showIncompleteFormAlert(){
        this.inCompleteForm = true;         
        setTimeout(function() {
          this.dismissAlert();
       }.bind(this), 3000);
     }

      showProgress(){
        this.showProgressBar = true;  
     }
      hideProgress(){
        this.showProgressBar = false;  
     }
     onClickAdd() { 
       let task:Task=this.getTask();
       if(!this.validTicket){
          this.showErrorAlert("invalid ticket");
          return;
       }
       if(task.medium.mediumId==-1 || task.category.categoryId==-1 || task.subtype.subtypeId==-1 || task.taskType.taskTypeId==-1){
          //alert("Fill the form");
           this.showIncompleteFormAlert();
          return;
       }
     
       if (  task.hours ===null || task.hours===0  ) {
           //alert("Fill the form");
           this.showIncompleteFormAlert();
          return;
       }
       if(task.tiket.incidentId===null || task.tiket.incidentId===""|| task.tiket.incidentId.trim()===""){
        console.log("task.category.categoryName :" +task.category.categoryName + " val"+task.category.categoryName.toLowerCase().indexOf("other"));
           if(task.category.categoryName.toLowerCase().indexOf("other")===-1){
               this.showIncompleteFormAlert();
                  return;
           }

       }
        


      // let jsonString = JSON.stringify(task);
       this.showProgress();
       
      this.timeTrackerService.addTask(task).subscribe(
        result => { 
                    this.hideProgress();
                    if( result.error==null){
                       console.log("Task added successfully");
                       
                         this.showSuccessAlert();
                        this.resetForm();
                        this.reloadTaskList();
                     }else{
                       this.showFailtAlert();
                       console.log("Failed to add task, status code:"+result.status);
                     }         
        },
         err => console.log("Error: " + err),
         () => console.log("DONE")
      );  

     

     // console.log( `click added : ${jsonString}` );
    } 
  
   

      getTaskListByUser(taskdate:string){        
            this.showTaskListProgressBar=true;
            this.timeTrackerService.getTaskList(taskdate).subscribe(
            result => { 

            //   totaHours
            this.totaHours=0;
            result.forEach(task=>{
                  this.totaHours+=task.hours;
            });
              this.taskList = result;
             
              


            },
             err => console.log("Error: " + err),
             () => { console.log("DONE"); this.showTaskListProgressBar=false;}
          );  
      }
     getTask():Task{
            let task:Task = new Task();
            let ticket:Ticket= new Ticket();      
            ticket.incidentId=this.incidentId;
            ticket.assignedGroup= this.assignedGroup;
            task.taskId=this.taskId;
            task.comments=this.comments;
            task.category=this.selectedCategory;
            task.taskDate =this.taskDate;
            task.hours=this.hour;
            task.taskType=this.selectedTaskType;
            task.tiket=ticket;
            task.medium=this.selectedMedium;
            task.subtype=this.selectedSubtype;
            return task;
             
     }
    getTodayDate(): string{
          let today;
           today = moment().format('YYYY-MM-DD');
           console.log(`today ${today}`);
          return today;
    }


}
enum FormMode{
   EDIT, NEW
}