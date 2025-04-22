import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.example.Main;

public class TestMain {

    // общий тест работоспособности
    @Test
    void testReadPeopleFromCsv() {
        List<Main.Person> people = Main.readPeopleFromCsv("people.csv");

        assertFalse(people.isEmpty(), "Список людей не должен быть пустым");

        Main.Person first = people.get(0);
        assertEquals(28281, first.getId());
        assertEquals("Aahan", first.getName());
        assertEquals("Male", first.getGender());
        assertEquals(LocalDate.of(1970, 5, 15), first.getBirthDate());
        assertEquals("I", first.getDivision().getName());
        assertEquals(4800.0, first.getSalary());
    }

    // проверка что у каждого отдела создается уникальный ид
    @Test
    void testDivisionIdAutoIncrement() {
        Main.Division div1 = new Main.Division("HR");
        Main.Division div2 = new Main.Division("IT");

        assertNotEquals(div1.getId(), div2.getId(), "ID разных подразделений должны отличаться");
    }
}
