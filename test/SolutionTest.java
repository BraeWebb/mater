import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import problem.Action;
import problem.ProblemSpec;
import simulator.Simulator;
import simulator.State;
import solution.Solution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class SolutionTest {

    /**
     * Recursively load all files in a directory starting with a specific string.
     *
     * @param folderPath The directory to recurse through
     * @param startsWith The string that a file needs to start with to be returned
     *
     * @return A list of string arrays containing the filename, file path
     */
    private static List<String[]> listFiles(String folderPath, String startsWith) {
        File file = new File(folderPath);

        List<String[]> result = new ArrayList<>();

        if (file.isDirectory()) {
            for (File content : file.listFiles()) {
                result.addAll(listFiles(content.getPath(), startsWith));
            }
        } else if (file.getName().startsWith(startsWith)) {
            result.add(new String[]{file.getName(), file.getPath()});
        }

        return result;
    }

    /**
     * Get the parameter data for the class
     */
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return new ArrayList<>(listFiles("examples", "input_"));
    }

    private String inputFile;

    /**
     * Construct a parameterized solution test class
     *
     * @param name The filename of the input problem file to test
     * @param inputFile The file path to the problem file
     */
    public SolutionTest(String name, String inputFile) {
        this.inputFile = inputFile;
    }

    @Test
    public void testValidProblemSpec(){
        try {
            ProblemSpec problem = new ProblemSpec(inputFile);
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    public void testFindsSolution() throws IOException {
        ProblemSpec problem = new ProblemSpec(inputFile);
        Solution solution = new Solution(problem);
        Simulator sim = new Simulator(problem, "/dev/null");

        State state = sim.reset();
        Action action;

        while (state != null) {
            action = solution.nextAction();
            state = sim.step(action);
            solution.addState(state);

            if (sim.isGoalState(state)) {
                break;
            }
        }

        assertNotNull("Failed to find a solution", state);
    }
}
