package solution;

import problem.ProblemSpec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    /**
     * Write a formatted solution to an output file.
     *
     * @param output The formatted solution.
     * @param outputFilename The filename to output the solution to.
     */
    public static void writeSolution(String output, String outputFilename) {
        try {
            BufferedWriter input = new BufferedWriter(
                    new FileWriter(outputFilename));
            input.write(output);
            input.flush();
        } catch (IOException e) {
            System.err.println("FileIO Error: could not output solution file");
            System.exit(4);
        }
    }

    /**
     * Load a problem with no solution.
     *
     * @param problemFile Filename of the problem file.
     * @return A problem spec with no solution.
     */
    public static ProblemSpec loadProblem(String problemFile) {
        ProblemSpec problem = null;
        try {
            problem = new ProblemSpec(problemFile);
        } catch (IOException e) {
            System.err.println("FileIO Error: could not load input file");
            System.exit(2);
        }
        return problem;
    }

    /**
     * Load a problem and generate a solution based on the arguments provided.
     *
     * Usage: java ProgramName inputFileName outputFileName
     *
     * @param args Command line args.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid Usage: java ProgramName inputFileName outputFileName");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        ProblemSpec problemSpec = loadProblem(inputFile);
    }
}
