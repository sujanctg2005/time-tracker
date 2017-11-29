
import { Error } from './error';

export class Payload<T> {
  data: T;
  error: Error;
  status:number;
}