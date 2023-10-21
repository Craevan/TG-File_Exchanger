package dev.crevan.dao;

import dev.crevan.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDao extends JpaRepository<RawData, Long> {
}
