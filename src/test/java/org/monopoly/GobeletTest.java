package org.monopoly;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GobeletTest {

    @Test
    public void testGobelet() {
        Gobelet gobelet = new Gobelet();
        int valeurlancer = gobelet.lancer();
        assertThat(valeurlancer).isBetween(2, 12);
    }
}
