import { IReservation } from 'app/shared/model/reservation.model';

export interface IEquipe {
  id?: number;
  nom?: string;
  jeu?: string;
  taille?: number;
  telephone?: number;
  telephones?: IReservation[];
}

export class Equipe implements IEquipe {
  constructor(
    public id?: number,
    public nom?: string,
    public jeu?: string,
    public taille?: number,
    public telephone?: number,
    public telephones?: IReservation[]
  ) {}
}
