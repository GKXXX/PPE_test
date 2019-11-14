import { ICoach } from 'app/shared/model/coach.model';

export interface ICoaching {
  id?: number;
  type?: string;
  jeu?: string;
  prix?: number;
  coach?: ICoach;
}

export class Coaching implements ICoaching {
  constructor(public id?: number, public type?: string, public jeu?: string, public prix?: number, public coach?: ICoach) {}
}
