import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReservation, Reservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { ILocaux } from 'app/shared/model/locaux.model';
import { LocauxService } from 'app/entities/locaux/locaux.service';
import { IEquipe } from 'app/shared/model/equipe.model';
import { EquipeService } from 'app/entities/equipe/equipe.service';
import { ICoach } from 'app/shared/model/coach.model';
import { CoachService } from 'app/entities/coach/coach.service';

@Component({
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html'
})
export class ReservationUpdateComponent implements OnInit {
  isSaving: boolean;

  locauxes: ILocaux[];

  equipes: IEquipe[];

  coaches: ICoach[];
  dateDebutDp: any;

  editForm = this.fb.group({
    id: [],
    numeroID: [null, [Validators.required]],
    equipe: [],
    coach: [],
    local: [],
    duree: [],
    prixTotal: [],
    dateDebut: [],
    locaux: [],
    equipe: [],
    coach: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected reservationService: ReservationService,
    protected locauxService: LocauxService,
    protected equipeService: EquipeService,
    protected coachService: CoachService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reservation }) => {
      this.updateForm(reservation);
    });
    this.locauxService
      .query()
      .subscribe((res: HttpResponse<ILocaux[]>) => (this.locauxes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.equipeService
      .query()
      .subscribe((res: HttpResponse<IEquipe[]>) => (this.equipes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.coachService
      .query()
      .subscribe((res: HttpResponse<ICoach[]>) => (this.coaches = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reservation: IReservation) {
    this.editForm.patchValue({
      id: reservation.id,
      numeroID: reservation.numeroID,
      equipe: reservation.equipe,
      coach: reservation.coach,
      local: reservation.local,
      duree: reservation.duree,
      prixTotal: reservation.prixTotal,
      dateDebut: reservation.dateDebut,
      locaux: reservation.locaux,
      equipe: reservation.equipe,
      coach: reservation.coach
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reservation = this.createFromForm();
    if (reservation.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationService.update(reservation));
    } else {
      this.subscribeToSaveResponse(this.reservationService.create(reservation));
    }
  }

  private createFromForm(): IReservation {
    return {
      ...new Reservation(),
      id: this.editForm.get(['id']).value,
      numeroID: this.editForm.get(['numeroID']).value,
      equipe: this.editForm.get(['equipe']).value,
      coach: this.editForm.get(['coach']).value,
      local: this.editForm.get(['local']).value,
      duree: this.editForm.get(['duree']).value,
      prixTotal: this.editForm.get(['prixTotal']).value,
      dateDebut: this.editForm.get(['dateDebut']).value,
      locaux: this.editForm.get(['locaux']).value,
      equipe: this.editForm.get(['equipe']).value,
      coach: this.editForm.get(['coach']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackLocauxById(index: number, item: ILocaux) {
    return item.id;
  }

  trackEquipeById(index: number, item: IEquipe) {
    return item.id;
  }

  trackCoachById(index: number, item: ICoach) {
    return item.id;
  }
}
