package org.thinkit.neumann;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class NeumannTest {

    @Test
    void testNeumann() {
        String result = Neumann.input("1+2*1*2/2").evaluate();

        assertNotNull(result);
        assertEquals("3", result);
    }
}
