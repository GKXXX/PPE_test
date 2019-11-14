import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ILocaux } from 'app/shared/model/locaux.model';
import { LocauxService } from './locaux.service';

@Component({
  selector: 'jhi-locaux',
  templateUrl: './locaux.component.html'
})
export class LocauxComponent implements OnInit, OnDestroy {
  locauxes: ILocaux[];
  eventSubscriber: Subscription;

  constructor(protected locauxService: LocauxService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.locauxService.query().subscribe((res: HttpResponse<ILocaux[]>) => {
      this.locauxes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLocauxes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocaux) {
    return item.id;
  }

  registerChangeInLocauxes() {
    this.eventSubscriber = this.eventManager.subscribe('locauxListModification', () => this.loadAll());
  }
}
