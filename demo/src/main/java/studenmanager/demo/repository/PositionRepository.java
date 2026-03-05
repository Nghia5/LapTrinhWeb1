package studenmanager.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import studenmanager.demo.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
}