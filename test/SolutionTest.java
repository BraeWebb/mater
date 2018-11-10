import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import problem.ProblemSpec;
import solution.Solution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SolutionTest {

    private static final boolean DO_HACK = false;

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
        List<String[]> files = listFiles("examples", "input_");
        files.sort((String[] o1, String[] o2) -> o1[0].compareTo(o2[0]));
        return new ArrayList<>(files);
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

    @Test(timeout = 120000)
    public void testSolution() throws IOException {
        ProblemSpec problem = new ProblemSpec(inputFile);

        Solution solution = new Solution(problem);
        if (!DO_HACK) {
            assertTrue("Failed to find a solution", solution.basicMCTS());
        } else {
            assertTrue("Failed to find a solution", solution.advancedMCTS());
        }
    }
}
