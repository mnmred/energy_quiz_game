package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmoteTest {
    public String path;
    public String name;

    @BeforeEach
    void init(){
        path = "/path";
        name= "Someone";
    }

    @Test
    void getPath() {
        Emote e = new Emote(path,name);
        assertEquals(path,e.getPath());
    }

    @Test
    void setPath() {
        Emote e = new Emote(path,name);
        e.setPath("/o");
        assertEquals("/o",e.getPath());
    }

    @Test
    void getName() {
        Emote e = new Emote(path,name);
        assertEquals(name,e.getName());
    }

    @Test
    void setName() {
        Emote e = new Emote(path,name);
        e.setName("new");
        assertEquals("new",e.getName());
    }

    @Test
    void testEquals() {
        Emote e = new Emote(path,name);
        Emote e1 = new Emote(path,name);
        assertEquals(e,e1);
    }

    @Test
    void testNotEquals() {
        Emote e = new Emote("/o",name);
        Emote e1 = new Emote(path,name);
        assertNotEquals(e,e1);
    }

    @Test
    void testHashCode() {
        Emote e = new Emote(path,name);
        Emote e1 = new Emote(path,name);
        Emote e2 = new Emote("/o",name);
        assertEquals(e.hashCode(),e1.hashCode());
        assertNotEquals(e.hashCode(),e2.hashCode());
    }
}