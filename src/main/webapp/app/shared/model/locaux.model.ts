import { IReservation } from 'app/shared/model/reservation.model';
import { IEquipement } from 'app/shared/model/equipement.model';

export interface ILocaux {
  id?: number;
  adresse?: string;
  ville?: string;
  telephone?: number;
  taille?: number;
  prixJour?: number;
  telephones?: IReservation[];
  equipement?: IEquipement;
}

export class Locaux implements ILocaux {
  constructor(
    public id?: number,
    public adresse?: string,
    public ville?: string,
    public telephone?: number,
    public taille?: number,
    public prixJour?: number,
    public telephones?: IReservation[],
    public equipement?: IEquipement
  ) {}
}
