
import org.example.Injector;
import org.example.Main;
import org.example.SomeBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MainTest {
    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = new Injector("config.properties");
    }

    // проверка работоспобности инжектора и поля инициализируются
    @Test
    void testInjection() {
        SomeBean bean = injector.inject(new SomeBean());

        assertNotNull(bean, "Bean should not be null");
        assertDoesNotThrow(bean::foo, "Method foo() should not throw exception");
    }

    // проверка что в выводе есть либо А либо В и обязательно С
    @Test
    void testFooOutput() {
        SomeBean bean = injector.inject(new SomeBean());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        bean.foo();

        String output = outContent.toString().trim();
        assertTrue(output.contains("A") || output.contains("B"));
        assertTrue(output.contains("C"));
    }
}