import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';

@Component({
  selector: 'jhi-equipe-delete-dialog',
  templateUrl: './equipe-delete-dialog.component.html'
})
export class EquipeDeleteDialogComponent {
  equipe: IEquipe;

  constructor(protected equipeService: EquipeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.equipeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'equipeListModification',
        content: 'Deleted an equipe'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-equipe-delete-popup',
  template: ''
})
export class EquipeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EquipeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.equipe = equipe;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/equipe', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/equipe', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
