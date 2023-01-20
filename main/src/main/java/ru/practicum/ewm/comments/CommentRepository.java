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
            "where (:status is null or :status       = c.status) and " +
            "   (:authorIds is null or c.author.id    in :authorIds) and " +
            "   (:eventIds  is null or c.event.id     in :eventIds) and " +
            "   (:status    is null or :status       = c.status) and " +
            "   (cast(:startDateCreated as timestamp)   is null or c.createdOn   > :startDateCreated) and " +
            "   (cast(:endDateCreated as timestamp)     is null or c.createdOn   < :endDateCreated) and" +
            "   (cast(:startDatePublished as timestamp) is null or c.publishedOn > :startDatePublished) and " +
            "   (cast(:endDatePublished as timestamp)   is null or c.publishedOn < :endDatePublished) and " +
            "   (:text      is null or upper(c.text) like upper(concat('%', :text, '%')))")
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
