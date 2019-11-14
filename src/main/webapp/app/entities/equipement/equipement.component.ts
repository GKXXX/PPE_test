import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipement } from 'app/shared/model/equipement.model';
import { EquipementService } from './equipement.service';

@Component({
  selector: 'jhi-equipement',
  templateUrl: './equipement.component.html'
})
export class EquipementComponent implements OnInit, OnDestroy {
  equipements: IEquipement[];
  eventSubscriber: Subscription;

  constructor(protected equipementService: EquipementService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.equipementService.query().subscribe((res: HttpResponse<IEquipement[]>) => {
      this.equipements = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEquipements();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEquipement) {
    return item.id;
  }

  registerChangeInEquipements() {
    this.eventSubscriber = this.eventManager.subscribe('equipementListModification', () => this.loadAll());
  }
}
