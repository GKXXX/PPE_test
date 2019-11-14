import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReservation } from 'app/shared/model/reservation.model';

type EntityResponseType = HttpResponse<IReservation>;
type EntityArrayResponseType = HttpResponse<IReservation[]>;

@Injectable({ providedIn: 'root' })
export class ReservationService {
  public resourceUrl = SERVER_API_URL + 'api/reservations';

  constructor(protected http: HttpClient) {}

  create(reservation: IReservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reservation);
    return this.http
      .post<IReservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reservation: IReservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reservation);
    return this.http
      .put<IReservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReservation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReservation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reservation: IReservation): IReservation {
    const copy: IReservation = Object.assign({}, reservation, {
      dateDebut: reservation.dateDebut != null && reservation.dateDebut.isValid() ? reservation.dateDebut.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reservation: IReservation) => {
        reservation.dateDebut = reservation.dateDebut != null ? moment(reservation.dateDebut) : null;
      });
    }
    return res;
  }
}
