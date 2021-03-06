package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.JobType;
import com.mycompany.myapp.service.view.JobTypeView;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    String JOB_TYPE_FROM_COMPANY_IN_CACHE = "jobTypeByCompany";

    @Query("select j from JobType j join Company c on c.id = j.company.id " +
        "where c.id = :id and lower(j.name) like concat('%',lower(:searchTerm),'%')")
    Page<JobTypeView> findAllByCompanyIdPaginated(@Param("id") Long id, @Param("searchTerm") String searchTerm, Pageable pageable);

    @Cacheable(cacheNames = JOB_TYPE_FROM_COMPANY_IN_CACHE)
    List<JobTypeView> findAllByCompany_Id(Long id);

    List<JobType> findAllByCompany(Company company);
}
