package org.thinkit.neumann;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class NeumannTest {

    @Test
    void testNeumann() {
        String result = Neumann.input("3*(4-9)/3").evaluate();

        assertNotNull(result);
        assertEquals("-5", result);
    }
}
