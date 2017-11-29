
import { TaskCategory } from './task-category';
import { TaskType } from './task-type';
import { User } from './user';
import { Ticket } from './ticket';
import { Medium } from './medium';
import { Subtype } from './subtype';
export class Task{
  taskId: number;
  comments: string;
  taskDate:string;
  hours:number;
  category:TaskCategory;
  taskType:TaskType;
  tiket:Ticket;
  user:User;
  medium:Medium;
  subtype:Subtype;
}