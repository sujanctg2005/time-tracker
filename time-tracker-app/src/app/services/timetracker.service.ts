import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';


import { TaskCategory } from '../model/task-category';
import { TaskType } from '../model/task-type';
import { Payload } from '../model/payload';
import { Void } from '../model/void';
import { Task } from '../model/task';
import { User } from '../model/user';
import { Medium } from '../model/medium';
import { Error } from '../model/error';
import { Subtype } from '../model/subtype';
import { environment  as env} from '../../environments/environment';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
 let loginUser: User = new User();
@Injectable()
export class TimeTrackerService {
  
   private  apiHOST=env.apiURL;

  private getCategoryListUrl = `${this.apiHOST}/category/getCategoryList`;
  private getSubtypeListUrl = `${this.apiHOST}/subtype/getSubtypeList`;

  private getMediumListUrl = `${this.apiHOST}/medium/getMediumList`;  
  private getTaskTypeListUrl = `${this.apiHOST}/taskType/getTaskTypeList`; 
  private createTaskUrl = `${this.apiHOST}/timeEntry/createTask`; 
  private allTasksByUserUrl = `${this.apiHOST}/timeEntry/allTasksByUser`; 
  private allTasksByUserV1Url = `${this.apiHOST}/timeEntry/allTasksByUserV1`; 
  private deleteTaskUrl = `${this.apiHOST}/timeEntry/deleteTask`; 
  private  loginUrl = `${this.apiHOST}/auth/login`; 
  //http://localhost:8080/ticket/validateTicket/TICKET,INCIDENT_ID,ASSIGNED_GROUP/CRQ000000019915
  constructor(
    private http: HttpClient) { 

     console.log("apiHOST :"+this.apiHOST);
  }


/** GET login user */
   getUser():any{   
       console.log("current user:"+ env.currentUser);
        return   env.currentUser;
   }

   login<Data>(user: User): Observable<Payload<Void>> {     

    const url = `${this.loginUrl}/${user.username}/${user.password}`;
    return this.http.get<Payload<Void>>(url)
      .pipe(       
        tap(h => {
           
          const outcome = h ? `fetched` : `did not find`;
          this.log(`login: ${loginUser.username} `);
        }),
        catchError(this.handleError<Payload<Void>>(`getCategoryList`))
      );
  }

  /** GET getMediumList from the server */
  getSubtypeList<Data>(): Observable<Subtype[]> {
    const url = `${this.getSubtypeListUrl}`;
    return this.http.get<Payload<Subtype[]>>(url)
      .pipe(
        map(payload => payload.data),
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} `);
        }),
        catchError(this.handleError<Subtype[]>(`getSubtypeList`))
      );
  }

  /** GET getMediumList from the server */
  getMediumList<Data>(): Observable<Medium[]> {
    const url = `${this.getMediumListUrl}`;
    return this.http.get<Payload<Medium[]>>(url)
      .pipe(
        map(payload => payload.data),
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} `);
        }),
        catchError(this.handleError<Medium[]>(`getMediumList`))
      );
  }

  /** GET getCategoryList from the server */
  getCategoryList<Data>(): Observable<TaskCategory[]> {
    const url = `${this.getCategoryListUrl}`;
    return this.http.get<Payload<TaskCategory[]>>(url)
      .pipe(
        map(payload => payload.data),
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} `);
        }),
        catchError(this.handleError<TaskCategory[]>(`getCategoryList`))
      );
  }


/** GET task list by user from the server */
  getTaskList<Data>(taskDate:string): Observable<Task[]> {
     loginUser.username =this.getUser();        
    //allTasksByUserV1Url  allTasksByUserUrl
    const url = `${this.allTasksByUserV1Url}/${taskDate}/${loginUser.username}`;
    return this.http.get<Payload<Task[]>>(url)
      .pipe(
        map(payload => payload.data),
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} `);
        }),
        catchError(this.handleError<Task[]>(`getTaskList`))
      );
  }

  /** POST: add a new task to the server */
  
  addTask (task: Task): Observable<Payload<Void>> {
    task.user=loginUser;
    console.log("task.user" +task.user.username);
    return this.http.post<Payload<Void>>(this.createTaskUrl, task, httpOptions).pipe(
      tap((payload: Payload<Void>) => this.log(`added task w/ id=${payload}`)),
      catchError(this.handleError<Payload<Void>>('addTask'))
    );
  }

    /** DELETE: task the  from the server */
 deleteTask (task: Task| number): Observable<Payload<Void>> {
    const id = typeof task === 'number' ? task : task.taskId;
    const url = `${this.deleteTaskUrl}/${id}`;

    return this.http.delete<Payload<Void>>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted task id=${id}`)),
      catchError(this.handleError<Payload<Void>>('deleteTask'))
    );
  }


  /** GET getTaskTypeList from the server */
  getTaskTypeList<Data>(): Observable<TaskType[]> {
    const url = `${this.getTaskTypeListUrl}`;
    return this.http.get<Payload<TaskType[]>>(url)
      .pipe(
        map(payload => payload.data), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} `);
        }),
        catchError(this.handleError<TaskType[]>(`getTaskTypeList `))
      );
  }

  

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
      console.log("error.status:" +error.status);
       
       if(error.status!=0){
          result=error.error; // rror.error is reponse payload
        }else{
          let httpError = new Error();
          let payload:any= new Payload<Void>()
          httpError.errorCode=error.status;
          payload.error=httpError;
          result=payload;
        }
       

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a taskService message with the MessageService */
  private log(message: string) {
   console.log(`${message}`);
  }
}