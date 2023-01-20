package ru.practicum.ewm.comments.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.CommentMapper;
import ru.practicum.ewm.comments.model.Status;
import ru.practicum.ewm.comments.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/comments")
public class CommentAdminController {

    private final CommentService service;
    private final CommentMapper mapper;

    @GetMapping
    public List<CommentDto> findAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> authorsIds,
            @RequestParam(required = false) List<Long> eventIds,
            @RequestParam(required = false) LocalDateTime startDateCreated,
            @RequestParam(required = false) LocalDateTime endDateCreated,
            @RequestParam(required = false) LocalDateTime startDatePublished,
            @RequestParam(required = false) LocalDateTime endDatePublished,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size
    ) {
        return service.search(text,
                        authorsIds,
                        eventIds,
                        status,
                        startDateCreated,
                        endDateCreated,
                        startDatePublished,
                        endDatePublished,
                        PageRequest.of(from / size, size))
                .stream()
                .map(mapper::toGetDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable Long id) {
        return mapper.toGetDto(service.findByIdOrThrow(id));
    }

    @PatchMapping("/{id}/post")
    public CommentDto post(@PathVariable Long id) {
        return mapper.toGetDto(service.publish(id));
    }

    @PatchMapping("/{id}/reject")
    public CommentDto reject(@PathVariable Long id) {
        return mapper.toGetDto(service.reject(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteByAdmin(id);
    }

}
