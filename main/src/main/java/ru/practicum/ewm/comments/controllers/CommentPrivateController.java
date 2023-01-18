package ru.practicum.ewm.comments.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.CommentInsertDto;
import ru.practicum.ewm.comments.dto.CommentMapper;
import ru.practicum.ewm.comments.service.CommentService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/comments")
public class CommentPrivateController {
    private final CommentService service;
    private final CommentMapper mapper;

    @PostMapping
    public CommentDto create(@RequestBody String text, @PathVariable Long userId) {
        return mapper.toGetDto(service.create(new CommentInsertDto(null, text, userId)));
    }

    @PatchMapping("/{id}")
    public CommentDto update(@RequestBody String text, @PathVariable Long userId, @PathVariable Long id) {
        return mapper.toGetDto(service.update(new CommentInsertDto(id, text, userId)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long userId, @PathVariable Long id) {
        service.delete(userId, id);
    }

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable Long userId, @PathVariable Long id) {
        return mapper.toGetDto(service.findAuthorComment(userId, id).orElseThrow());
    }
}
