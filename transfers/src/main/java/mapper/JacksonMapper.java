package mapper;

/**
 * Created by alexander.zelentsov on 24.03.2017.
 */
public interface JacksonMapper {

    String writeToString(Object o);

    <T> T readObject(String data, Class<T> clazz);
}
