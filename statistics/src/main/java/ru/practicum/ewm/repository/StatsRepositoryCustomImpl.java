package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.View;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class StatsRepositoryCustomImpl implements StatsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<View> findAllByRange(LocalDateTime start, LocalDateTime end, List<URI> uris, Boolean unique) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<View> query = cb.createQuery(View.class);
        Root<Hit> hit = query.from(Hit.class);
        CriteriaBuilder.In<URI> in = cb.in(hit.get("uri"));
        uris.forEach(in::value);
        query.multiselect(
                    hit.get("app"),
                    hit.get("uri"),
                    unique ? cb.countDistinct(hit.get("ip")) : cb.count(hit.get("ip")))
                .where(cb.and(cb.between(hit.get("timestamp"), start, end), in))
                .groupBy(hit.get("app"), hit.get("uri"));
        return entityManager.createQuery(query).getResultList();
    }
}
