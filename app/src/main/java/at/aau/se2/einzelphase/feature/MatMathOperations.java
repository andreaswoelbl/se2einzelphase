package at.aau.se2.einzelphase.feature;

import java.util.Arrays;

/**
 * @author Andreas Wölbl
 * Class providing mathematical operations for matriculation numbers
 */
public class MatMathOperations {

    public static int checksumOfMatriculationNumber(String matriculationNumber) {
        if (!matriculationNumber.matches("[0-9]{8}"))
            return -1;
        return Arrays.stream(matriculationNumber.split("")).mapToInt(Integer::parseInt).sum();
    }
}
