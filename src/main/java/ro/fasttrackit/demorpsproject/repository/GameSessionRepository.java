package ro.fasttrackit.demorpsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.demorpsproject.domain.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {
}
