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
  deleteFail:boolean;
  showProgressBar:boolean;
  appContext=env.appContext;
  constructor(private timeTrackerService: TimeTrackerService) { }

  resetForm(){
       this.incidentId="";
       this.hour=null;
       this.comments="";
       this.taskDate=this.getTodayDate();
       this.formMode=FormMode.NEW;
 
  }

  ngOnInit() {
       //setting default value
      this.resetForm();
  
     // load category list 
      this.timeTrackerService.getCategoryList().subscribe(
	      result => { 

	      	this.categoryList = result;
            this.selectedCategory=this.categoryList[0];
	      },
	       err => console.log("Error: " + err),
	       () => console.log("DONE")
    	);	

      //end

       // load category list 
      this.timeTrackerService.getTaskTypeList().subscribe(
        result => { 

          this.taskTypeList = result;
            this.selectedTaskType=this.taskTypeList[0];
        },
         err => console.log("Error: " + err),
         () => console.log("DONE")
      );  

      //end

       // load Medium list 
      this.timeTrackerService.getMediumList().subscribe(
        result => { 

          this.mediumList = result;
           this.selectedMedium=this.mediumList[0];
        },
         err => console.log("Error: " + err),
         () => console.log("DONE")
      );  

      //end

     // load Subtype list 
      this.timeTrackerService.getSubtypeList().subscribe(
        result => { 
          this.subtypeList = result;
           this.selectedSubtype=this.subtypeList[0];
        },
         err => console.log("Error: " + err),
         () => console.log("DONE")
      );  

      //end


      //load all user task by date
      this.reloadTaskList();


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

      showProgress(){
        this.showProgressBar = true;  
     }
      hideProgress(){
        this.showProgressBar = false;  
     }
     onClickAdd() { 
       let task:Task=this.getTask();
     
       if (  ( ! task.tiket ===null   || task.tiket.incidentId===null ) ||  (  task.hours ===null || task.hours===0 ) ){
           alert("Fill the form");
          return;
       }
       
      // let jsonString = JSON.stringify(task);
       this.showProgress();
       
      this.timeTrackerService.addTask(task).subscribe(
        result => { 
                    this.hideProgress();
                    if( result.status==null){
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
  
     deleteTask(task:Task){
          console.log("Delete task id: "+task.taskId);        

          this.timeTrackerService.deleteTask(task).subscribe(
          result => { 
                if( result.status==null){
                   console.log("Task delete successfully");
                    this.reloadTaskList();
                 }else{
                     this.showDeleteFailAlert();
                   console.log("Failed to delete the task, status code:"+result.status);
                 }
           
          }, err => console.log("Error: " + err),() => console.log("DONE")
        );  

          
     }

      getTaskListByUser(taskdate:string){        

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
             () => console.log("DONE")
          );  
      }
     getTask():Task{
            let task:Task = new Task();
            let ticket:Ticket= new Ticket();      
            ticket.incidentId=this.incidentId;
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