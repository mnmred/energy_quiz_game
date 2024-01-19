package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static commons.Config.*;

class AnswerTest {

    @Test
    void getAnswer() {
        Answer a = new Answer(1,5050);
        assertEquals(1,a.getAnswer());
    }

    @Test
    void getTimeToAnswer() {
        Answer a = new Answer(1,5050);
        assertEquals(5050, a.getTimeToAnswer());
    }

    @Test
    void testEquals() {
        Answer a = new Answer(1,5050);
        Answer b = new Answer(1,5050);
        assertTrue(a.equals(b));
    }

    @Test
    void testNotEqualsTime(){
        Answer a = new Answer(1,5050);
        Answer b = new Answer(1,5051);
        assertFalse(a.equals(b));
    }

    @Test
    void testNotEqualsAnswer(){
        Answer a = new Answer(2,5050);
        Answer b = new Answer(1,5050);
        assertFalse(a.equals(b));
    }

    @Test
    void testPoints1(){
        Answer a = new Answer(1,(int) (0.75 * timePerQuestion));
        int expected = 125;
        assertEquals(expected,a.getPoints());
    }

    @Test
    void testPoints2(){
        Answer a = new Answer(1,(int) (0 * timePerQuestion));
        int expected = 200;
        assertEquals(expected,a.getPoints());
    }

    @Test
    void testPoints3(){
        Answer a = new Answer(1,(int) (0.5 * timePerQuestion));
        int expected = 150;
        assertEquals(expected,a.getPoints());
    }

    @Test
    void testHash(){
        Answer a = new Answer(1,(int) (0.5 * timePerQuestion));
        Answer a1 = new Answer(1,(int) (0.5 * timePerQuestion));
        assertEquals(a.hashCode(), a1.hashCode());
    }
}