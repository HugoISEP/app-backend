package com.mycompany.myapp.service.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CompanyDetailsView extends CompanyView {
    int getTotalUsers();
}
