import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEquipement } from 'app/shared/model/equipement.model';

@Component({
  selector: 'jhi-equipement-detail',
  templateUrl: './equipement-detail.component.html'
})
export class EquipementDetailComponent implements OnInit {
  equipement: IEquipement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipement }) => {
      this.equipement = equipement;
    });
  }

  previousState() {
    window.history.back();
  }
}
