package ru.practicum.ewm.comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("from Comment c " +
            "where (:status is null or :status = c.status) and " +
            "   (:authorIds is empty or c.author.id in :authorIds) and " +
            "   (:eventIds is empty or c.event.id in :eventIds) and " +
            "   (:status is null or :status = c.status) and " +
            "   (:startDate is null or c.createdOn > :startDateCreated) and " +
            "   (:endDate is null or c.createdOn < :endDateCreated) and" +
            "   (:startDate is null or c.publishedOn > :startDatePublished) and " +
            "   (:endDate is null or c.publishedOn < :endDatePublished)")
    Page<Comment> search(
            String text,
            List<Long> authorIds,
            List<Long> eventIds,
            Status status,
            LocalDateTime startDateCreated,
            LocalDateTime endDateCreated,
            LocalDateTime startDatePublished,
            LocalDateTime endDatePublished,
            Pageable pageable);
}
