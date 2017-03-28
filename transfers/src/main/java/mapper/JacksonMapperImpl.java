package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by alexander.zelentsov on 24.03.2017.
 */
public class JacksonMapperImpl implements JacksonMapper {

    private ObjectMapper mapper;

    public JacksonMapperImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public String writeToString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readObject(String data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
