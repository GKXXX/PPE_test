import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocaux } from 'app/shared/model/locaux.model';
import { LocauxService } from './locaux.service';

@Component({
  selector: 'jhi-locaux-delete-dialog',
  templateUrl: './locaux-delete-dialog.component.html'
})
export class LocauxDeleteDialogComponent {
  locaux: ILocaux;

  constructor(protected locauxService: LocauxService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locauxService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'locauxListModification',
        content: 'Deleted an locaux'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-locaux-delete-popup',
  template: ''
})
export class LocauxDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locaux }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocauxDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locaux = locaux;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/locaux', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/locaux', { outlets: { popup: null } }]);
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
