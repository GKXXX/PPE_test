import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestTestModule } from '../../../test.module';
import { CoachingDeleteDialogComponent } from 'app/entities/coaching/coaching-delete-dialog.component';
import { CoachingService } from 'app/entities/coaching/coaching.service';

describe('Component Tests', () => {
  describe('Coaching Management Delete Component', () => {
    let comp: CoachingDeleteDialogComponent;
    let fixture: ComponentFixture<CoachingDeleteDialogComponent>;
    let service: CoachingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [CoachingDeleteDialogComponent]
      })
        .overrideTemplate(CoachingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoachingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoachingService);
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
