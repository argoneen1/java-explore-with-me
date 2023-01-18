package ru.practicum.ewm.comments.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.comments.dto.CommentInsertDto;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.model.Status;
import ru.practicum.ewm.service.FindByIdService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentService extends FindByIdService<Comment, Long> {
    Comment create(CommentInsertDto comment);

    Comment update(CommentInsertDto comment);

    default Optional<Comment> findByIdPublished(Long id) {
        Comment comment = findByIdOrThrow(id);
        if (comment.getStatus() == Status.PUBLISHED) {
            return Optional.of(comment);
        } else {
            return Optional.empty();
        }
    }

    default Optional<Comment> findAuthorComment(Long userId, Long id) {
        Comment comment = findByIdOrThrow(id);
        if (comment.getStatus() == Status.PUBLISHED || comment.getAuthor().getId().equals(userId)) {
            return Optional.of(comment);
        } else {
            return Optional.empty();
        }
    }

    void deleteByAdmin(Long id);

    default void delete(Long userId, Long id) {
        Comment comment = findByIdOrThrow(id);
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("user id and comment author id don`t match");
        }
        deleteByAdmin(id);
    }

    Comment publish(Long id);

    Comment reject(Long id);

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
