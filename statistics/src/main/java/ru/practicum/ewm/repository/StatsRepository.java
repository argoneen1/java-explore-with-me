package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Hit;

public interface StatsRepository extends JpaRepository<Hit, Long>, StatsRepositoryCustom { }
