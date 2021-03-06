import { Component, OnInit } from '@angular/core';
import { ICompany } from '../../shared/model/company.model';
import { CompanyService } from './company.service';

@Component({
  selector: 'jhi-company-detail',
  templateUrl: './company-detail.component.html',
})
export class CompanyDetailComponent implements OnInit {
  company: ICompany | null = null;
  imageUrl: string | null = null;

  constructor(protected companyService: CompanyService) {}

  ngOnInit(): void {
    this.companyService.getUserCompany().subscribe(company => {
      this.company = company.body!;
      this.companyService.getFileUrl(this.company?.id!).subscribe(url => {
        this.imageUrl = url.body;
      });
    });
  }
}
