import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestTestModule } from '../../../test.module';
import { LocauxDeleteDialogComponent } from 'app/entities/locaux/locaux-delete-dialog.component';
import { LocauxService } from 'app/entities/locaux/locaux.service';

describe('Component Tests', () => {
  describe('Locaux Management Delete Component', () => {
    let comp: LocauxDeleteDialogComponent;
    let fixture: ComponentFixture<LocauxDeleteDialogComponent>;
    let service: LocauxService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [LocauxDeleteDialogComponent]
      })
        .overrideTemplate(LocauxDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocauxDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocauxService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
