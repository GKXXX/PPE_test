import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { LocauxDetailComponent } from 'app/entities/locaux/locaux-detail.component';
import { Locaux } from 'app/shared/model/locaux.model';

describe('Component Tests', () => {
  describe('Locaux Management Detail Component', () => {
    let comp: LocauxDetailComponent;
    let fixture: ComponentFixture<LocauxDetailComponent>;
    const route = ({ data: of({ locaux: new Locaux(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [LocauxDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocauxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocauxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locaux).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
