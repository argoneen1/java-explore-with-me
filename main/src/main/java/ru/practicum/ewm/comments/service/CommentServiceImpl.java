package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comments.CommentRepository;
import ru.practicum.ewm.comments.dto.CommentInsertDto;
import ru.practicum.ewm.comments.dto.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Comment create(CommentInsertDto comment) {

        return repository.save(mapper.toEntity(comment));
    }

    @Override
    public Comment update(CommentInsertDto comment) {
        Comment updated = findByIdOrThrow(comment.getId());
        mapper.update(comment, updated);
        return repository.save(updated);
    }

    @Override
    public void deleteByAdmin(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Comment publish(Long id) {
        Comment comment = findByIdOrThrow(id);
        comment.setStatus(Status.PUBLISHED);
        comment.setPublishedOn(LocalDateTime.now());
        return repository.save(comment);
    }

    @Override
    public Comment reject(Long id) {
        Comment comment = findByIdOrThrow(id);
        comment.setStatus(Status.REJECTED);
        return repository.save(comment);
    }

    @Override
    public Page<Comment> search(
            String text,
            List<Long> authorIds,
            Status status,
            LocalDateTime startDateCreated,
            LocalDateTime endDateCreated,
            LocalDateTime startDatePublished,
            LocalDateTime endDatePublished,
            Pageable pageable) {
        return repository.search(
                text,
                authorIds,
                status,
                startDateCreated,
                endDateCreated,
                startDatePublished,
                endDatePublished,
                pageable);
    }
}
