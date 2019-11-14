import { ILocaux } from 'app/shared/model/locaux.model';

export interface IEquipement {
  id?: number;
  type?: string;
  nom?: string;
  prixJour?: number;
  noms?: ILocaux[];
}

export class Equipement implements IEquipement {
  constructor(public id?: number, public type?: string, public nom?: string, public prixJour?: number, public noms?: ILocaux[]) {}
}
