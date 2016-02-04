package ru.notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.notes.model.note.Note;
import ru.notes.model.note.Tag;
import ru.notes.repository.note.NoteRepository;

import java.util.Arrays;

@SpringBootApplication
public class NotesWsApplication {

	@Bean
	CommandLineRunner init(final NoteRepository noteRepository) {
		return (evt) -> Arrays.asList(
				"Title1,Title2,Title3,Title4".split(","))
				.forEach(
						a -> {
							Note note = new Note();
							note.setContent("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diem nonummy nibh \n" +
									"  euismod tincidunt ut lacreet dolore magna aliguam erat volutpat. Ut wisis enim \n" +
									"  ad minim veniam, quis nostrud exerci tution ullamcorper suscipit lobortis nisl \n" +
									"  ut aliquip ex ea commodo consequat." +
									"Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diem nonummy nibh \n" +
									"  euismod tincidunt ut lacreet dolore magna aliguam erat volutpat. Ut wisis enim \n" +
									"  ad minim veniam, quis nostrud exerci tution ullamcorper suscipit lobortis nisl \n" +
									"  ut aliquip ex ea commodo consequat." +
									"Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diem nonummy nibh \n" +
									"  euismod tincidunt ut lacreet dolore magna aliguam erat volutpat. Ut wisis enim \n" +
									"  ad minim veniam, quis nostrud exerci tution ullamcorper suscipit lobortis nisl \n" +
									"  ut aliquip ex ea commodo consequat." +
									"Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diem nonummy nibh \n" +
									"  euismod tincidunt ut lacreet dolore magna aliguam erat volutpat. Ut wisis enim \n" +
									"  ad minim veniam, quis nostrud exerci tution ullamcorper suscipit lobortis nisl \n" +
									"  ut aliquip ex ea commodo consequat.");
							note.setTags(Arrays.asList(new Tag("text1"),new Tag("text2")));
							note.setTitle("Title for test");
							noteRepository.save(note);
						});
	}

	public static void main(String[] args) {
		SpringApplication.run(NotesWsApplication.class, args);
	}
}
