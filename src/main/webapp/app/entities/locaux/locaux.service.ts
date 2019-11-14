import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocaux } from 'app/shared/model/locaux.model';

type EntityResponseType = HttpResponse<ILocaux>;
type EntityArrayResponseType = HttpResponse<ILocaux[]>;

@Injectable({ providedIn: 'root' })
export class LocauxService {
  public resourceUrl = SERVER_API_URL + 'api/locauxes';

  constructor(protected http: HttpClient) {}

  create(locaux: ILocaux): Observable<EntityResponseType> {
    return this.http.post<ILocaux>(this.resourceUrl, locaux, { observe: 'response' });
  }

  update(locaux: ILocaux): Observable<EntityResponseType> {
    return this.http.put<ILocaux>(this.resourceUrl, locaux, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocaux>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocaux[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
