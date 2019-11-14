import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICoaching } from 'app/shared/model/coaching.model';

type EntityResponseType = HttpResponse<ICoaching>;
type EntityArrayResponseType = HttpResponse<ICoaching[]>;

@Injectable({ providedIn: 'root' })
export class CoachingService {
  public resourceUrl = SERVER_API_URL + 'api/coachings';

  constructor(protected http: HttpClient) {}

  create(coaching: ICoaching): Observable<EntityResponseType> {
    return this.http.post<ICoaching>(this.resourceUrl, coaching, { observe: 'response' });
  }

  update(coaching: ICoaching): Observable<EntityResponseType> {
    return this.http.put<ICoaching>(this.resourceUrl, coaching, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICoaching>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICoaching[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
