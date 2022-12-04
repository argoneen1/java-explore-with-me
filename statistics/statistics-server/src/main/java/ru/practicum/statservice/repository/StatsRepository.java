package ru.practicum.statservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.statservice.model.Hit;

public interface StatsRepository extends JpaRepository<Hit, Long>, StatsRepositoryCustom {
}
