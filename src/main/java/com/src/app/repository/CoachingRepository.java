package com.src.app.repository;
import com.src.app.domain.Coaching;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coaching entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachingRepository extends JpaRepository<Coaching, Long> {

}
