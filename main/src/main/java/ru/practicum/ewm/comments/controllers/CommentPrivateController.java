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
    public CommentDto create(@RequestBody CommentInsertDto commentInsertDto, @PathVariable Long userId) {
        commentInsertDto.setAuthor(userId);
        return mapper.toGetDto(service.create(commentInsertDto));
    }

    @PatchMapping("/{id}")
    public CommentDto update(@RequestBody CommentInsertDto commentInsertDto, @PathVariable Long userId, @PathVariable Long id) {
        commentInsertDto.setAuthor(userId);
        return mapper.toGetDto(service.update(commentInsertDto));
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
