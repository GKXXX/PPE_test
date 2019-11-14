package com.src.app.repository;
import com.src.app.domain.Coach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

}
