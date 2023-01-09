package org.monopoly;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GobeletTest {

    @Test
    public void testGobelet() {
        Gobelet gobelet = new Gobelet();
        int valeurlancer = gobelet.lancer();
        assertThat(valeurlancer).isBetween(2, 12);
    }

    @Test
    public void testDouble() {
        De mockedDe1 = mock(De.class);
        when(mockedDe1.getValeur()).thenReturn(2);

        De mockedDe2 = mock(De.class);
        when(mockedDe2.getValeur()).thenReturn(2);

        Gobelet gobelet = new Gobelet(mockedDe1, mockedDe1);
        assertThat(gobelet.estUnDouble()).isEqualTo(true);
    }
}
