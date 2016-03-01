package com.autobon.order.repository;

import com.autobon.order.entity.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuh on 2016/2/23.
 */
@Repository
public interface ConstructionRepository extends JpaRepository<Construction, Integer> {
    Construction findByTechnicianId(int technicianId);

    List<Construction> findByOrderIdAndTechnicianId(int orderId, int mainTechId);
}
