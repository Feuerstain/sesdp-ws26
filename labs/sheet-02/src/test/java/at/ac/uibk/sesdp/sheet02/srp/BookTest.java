package at.ac.uibk.sesdp.sheet02.srp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void getters_returnValuesFromConstructor() {
        Book book = new Book("Interesting title", "Nice content");

        assertEquals("Interesting title", book.getTitle());
        assertEquals("Nice content", book.getContent());
    }
}
