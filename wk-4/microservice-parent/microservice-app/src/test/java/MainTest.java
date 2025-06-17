import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void main() {
        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Laptop"), "Output should contain Laptop");
        assertTrue(output.contains("Headphones"), "Output should contain Headphones");
        assertTrue(output.contains("Monitor"), "Output should contain Monitor");
    }
}

