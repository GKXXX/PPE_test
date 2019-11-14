import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { CoachingDetailComponent } from 'app/entities/coaching/coaching-detail.component';
import { Coaching } from 'app/shared/model/coaching.model';

describe('Component Tests', () => {
  describe('Coaching Management Detail Component', () => {
    let comp: CoachingDetailComponent;
    let fixture: ComponentFixture<CoachingDetailComponent>;
    const route = ({ data: of({ coaching: new Coaching(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [CoachingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CoachingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoachingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coaching).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
