package org.demo.repository;


import org.demo.model.RekamMedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RekamMedisRepository extends JpaRepository<RekamMedis, Long> {
    List<RekamMedis> findByPasienId(Long pasienId);
}