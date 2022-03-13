package at.aau.se2.einzelphase.feature;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Andreas Wölbl
 * Unit tests for methods in {@link MatMathOperations}
 */
public class MathOperationsTest {

    @Test
    public void checkSumCorrectTest() {
        assertEquals(26, MatMathOperations.checksumOfMatriculationNumber("12042818"));
    }

    @Test
    public void checkSumTooShortTest() {
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber("1204"));
    }

    @Test
    public void checkSumEmptyStringTest() {
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber(""));
    }

    @Test
    public void checkSumTooLongTest() {
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber("120428185"));
    }

    @Test
    public void checkSumInvalidCharactersTest() {
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber("120428.8"));
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber("a1204288"));
        assertEquals(-1, MatMathOperations.checksumOfMatriculationNumber("aString5"));
    }

}