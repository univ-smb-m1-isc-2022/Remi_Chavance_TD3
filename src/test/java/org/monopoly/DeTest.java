package org.monopoly;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeTest {
    @Test
    public void testBetween1and6() {
        De de = new De();
        int valeurlancer = de.getValeur();
        assertThat(valeurlancer).isBetween(1, 6);
    }
}
