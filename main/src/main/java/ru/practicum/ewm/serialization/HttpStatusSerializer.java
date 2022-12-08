package ru.practicum.ewm.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class HttpStatusSerializer extends StdSerializer<HttpStatus> {


    public HttpStatusSerializer(Class<HttpStatus> vc) {
        super(vc);
    }

    public HttpStatusSerializer() {
        this(HttpStatus.class);
    }

    @Override
    public void serialize(HttpStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
}
