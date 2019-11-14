import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestTestModule } from '../../../test.module';
import { CoachingComponent } from 'app/entities/coaching/coaching.component';
import { CoachingService } from 'app/entities/coaching/coaching.service';
import { Coaching } from 'app/shared/model/coaching.model';

describe('Component Tests', () => {
  describe('Coaching Management Component', () => {
    let comp: CoachingComponent;
    let fixture: ComponentFixture<CoachingComponent>;
    let service: CoachingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [CoachingComponent],
        providers: []
      })
        .overrideTemplate(CoachingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoachingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoachingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Coaching(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coachings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
