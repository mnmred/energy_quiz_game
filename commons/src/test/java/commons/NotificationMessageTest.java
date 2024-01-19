package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {

    @Test
    void getMessage() {
        String message = "a";
        NotificationMessage msg = new NotificationMessage(message);
        assertEquals(message, msg.getMessage());
    }
}