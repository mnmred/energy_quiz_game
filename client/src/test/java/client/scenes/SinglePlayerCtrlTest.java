package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SinglePlayerCtrlTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void activateProgressBar() {
        new Thread(() -> activateProgressBar()).start();
    }
}