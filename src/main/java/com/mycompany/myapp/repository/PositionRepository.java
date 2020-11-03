package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Position;
import com.mycompany.myapp.service.view.PositionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<PositionView> findAllByMissionId(Long id);
    List<PositionView> findAllByMission_User_IdAndStatusIsTrue(Long id);

    @Transactional
    @Modifying
    @Query("delete from Position p where p.id = :id")
    void delete(@Param("id") Long id);
}
