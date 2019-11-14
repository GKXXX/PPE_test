import { Moment } from 'moment';
import { ILocaux } from 'app/shared/model/locaux.model';
import { IEquipe } from 'app/shared/model/equipe.model';
import { ICoach } from 'app/shared/model/coach.model';

export interface IReservation {
  id?: number;
  numeroID?: number;
  equipe?: string;
  coach?: string;
  local?: string;
  duree?: number;
  prixTotal?: number;
  dateDebut?: Moment;
  locaux?: ILocaux;
  equipe?: IEquipe;
  coach?: ICoach;
}

export class Reservation implements IReservation {
  constructor(
    public id?: number,
    public numeroID?: number,
    public equipe?: string,
    public coach?: string,
    public local?: string,
    public duree?: number,
    public prixTotal?: number,
    public dateDebut?: Moment,
    public locaux?: ILocaux,
    public equipe?: IEquipe,
    public coach?: ICoach
  ) {}
}
