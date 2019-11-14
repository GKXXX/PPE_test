import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocaux } from 'app/shared/model/locaux.model';

@Component({
  selector: 'jhi-locaux-detail',
  templateUrl: './locaux-detail.component.html'
})
export class LocauxDetailComponent implements OnInit {
  locaux: ILocaux;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locaux }) => {
      this.locaux = locaux;
    });
  }

  previousState() {
    window.history.back();
  }
}
