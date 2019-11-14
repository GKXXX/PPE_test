import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILocaux, Locaux } from 'app/shared/model/locaux.model';
import { LocauxService } from './locaux.service';
import { IEquipement } from 'app/shared/model/equipement.model';
import { EquipementService } from 'app/entities/equipement/equipement.service';

@Component({
  selector: 'jhi-locaux-update',
  templateUrl: './locaux-update.component.html'
})
export class LocauxUpdateComponent implements OnInit {
  isSaving: boolean;

  equipements: IEquipement[];

  editForm = this.fb.group({
    id: [],
    adresse: [],
    ville: [],
    telephone: [null, [Validators.required]],
    taille: [],
    prixJour: [],
    equipement: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locauxService: LocauxService,
    protected equipementService: EquipementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locaux }) => {
      this.updateForm(locaux);
    });
    this.equipementService
      .query()
      .subscribe(
        (res: HttpResponse<IEquipement[]>) => (this.equipements = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(locaux: ILocaux) {
    this.editForm.patchValue({
      id: locaux.id,
      adresse: locaux.adresse,
      ville: locaux.ville,
      telephone: locaux.telephone,
      taille: locaux.taille,
      prixJour: locaux.prixJour,
      equipement: locaux.equipement
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locaux = this.createFromForm();
    if (locaux.id !== undefined) {
      this.subscribeToSaveResponse(this.locauxService.update(locaux));
    } else {
      this.subscribeToSaveResponse(this.locauxService.create(locaux));
    }
  }

  private createFromForm(): ILocaux {
    return {
      ...new Locaux(),
      id: this.editForm.get(['id']).value,
      adresse: this.editForm.get(['adresse']).value,
      ville: this.editForm.get(['ville']).value,
      telephone: this.editForm.get(['telephone']).value,
      taille: this.editForm.get(['taille']).value,
      prixJour: this.editForm.get(['prixJour']).value,
      equipement: this.editForm.get(['equipement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocaux>>) {
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

  trackEquipementById(index: number, item: IEquipement) {
    return item.id;
  }
}
