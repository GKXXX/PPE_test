import { ICoaching } from 'app/shared/model/coaching.model';
import { IReservation } from 'app/shared/model/reservation.model';

export interface ICoach {
  id?: number;
  nom?: string;
  jeu?: string;
  prixJour?: number;
  telephone?: number;
  dispo?: boolean;
  telephones?: ICoaching[];
  telephones?: IReservation[];
}

export class Coach implements ICoach {
  constructor(
    public id?: number,
    public nom?: string,
    public jeu?: string,
    public prixJour?: number,
    public telephone?: number,
    public dispo?: boolean,
    public telephones?: ICoaching[],
    public telephones?: IReservation[]
  ) {
    this.dispo = this.dispo || false;
  }
}
