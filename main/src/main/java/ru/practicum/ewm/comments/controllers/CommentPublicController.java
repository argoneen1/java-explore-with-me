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
@RequestMapping("/comments")
public class CommentPublicController {
    private final CommentService service;
    private final CommentMapper mapper;

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable Long id) {
        return mapper.toGetDto(service.findByIdPublished(id).orElseThrow());
    }

    @GetMapping
    public List<CommentDto> findAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> authorsIds,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size
    ) {
        return service.search(text,
                        authorsIds,
                        Status.PUBLISHED,
                        null,
                        null,
                        startDate,
                        endDate,
                        PageRequest.of(from / size, size))
                .stream()
                .map(mapper::toGetDto).collect(Collectors.toList());
    }
}
