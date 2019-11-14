import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEquipement } from 'app/shared/model/equipement.model';

type EntityResponseType = HttpResponse<IEquipement>;
type EntityArrayResponseType = HttpResponse<IEquipement[]>;

@Injectable({ providedIn: 'root' })
export class EquipementService {
  public resourceUrl = SERVER_API_URL + 'api/equipements';

  constructor(protected http: HttpClient) {}

  create(equipement: IEquipement): Observable<EntityResponseType> {
    return this.http.post<IEquipement>(this.resourceUrl, equipement, { observe: 'response' });
  }

  update(equipement: IEquipement): Observable<EntityResponseType> {
    return this.http.put<IEquipement>(this.resourceUrl, equipement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEquipement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEquipement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
