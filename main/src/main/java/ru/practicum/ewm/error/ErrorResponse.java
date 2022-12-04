package ru.practicum.ewm.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.serialization.HttpStatusSerializer;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> errors;
    private String reason;
    @JsonSerialize(using = HttpStatusSerializer.class)
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, String reason, List<String> errors, HttpStatus status) {
        this.message = message;
        this.errors = errors;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
