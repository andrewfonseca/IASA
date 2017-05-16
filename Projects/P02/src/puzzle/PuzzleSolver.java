package puzzle;

import org.apache.commons.lang.StringUtils;
import puzzle.models.PuzzleOperator;
import puzzle.models.PuzzleProblem;
import search_strategies.informed.AStarSearch;
import search_strategies.informed.GreedyBestFirstSearch;
import search_strategies.uninformed.UniformCostSearch;
import search_strategies.uninformed.BreadthFirstSearch;
import search_strategies.uninformed.DepthFirstSearch;
import search_strategies.uninformed.IterativeSearch;
import search_strategies.engine.SearchMechanism;
import search_strategies.problem.Solution;
import search_strategies.problem.SolutionStep;

public class PuzzleSolver {

    public final static String LINE_FORMAT = "| %-25s | %6s | %15s | %21s | %19s |\n";

    public static void main(String[] args) {

        String s = String.format(
                LINE_FORMAT,
                "Search type",
                "Puzzle",
                "Cost",
                "Max nodes in frontier",
                "Max nodes expanded"
        );

        System.out.printf(s);
        System.out.printf("%s\n", StringUtils.center("-", 102, "-"));

        SearchMechanism[] search_list = {
                new BreadthFirstSearch(),
                new UniformCostSearch(),
                new DepthFirstSearch(),
                new IterativeSearch(),
                new GreedyBestFirstSearch(),
                new AStarSearch(),
        };

        Puzzle puzzle_A = new Puzzle(new int[][] { {1, 2, 3}, {8, 4, 5}, {6, 7, 0} });
        Puzzle puzzle_B = new Puzzle(new int[][] { {8, 4, 5}, {6, 1, 2}, {3, 7, 0} });
        Puzzle puzzle_C = new Puzzle(new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} });

        for (SearchMechanism search : search_list) {
            run_search(search, puzzle_A, puzzle_C, "A");
            run_search(search, puzzle_B, puzzle_C, "B");
        }
    }

    public static void run_search(SearchMechanism search, Puzzle puzzle, Puzzle goal_puzzle, String name) {
        PuzzleOperator[] operators = new PuzzleOperator[] {
                new PuzzleOperator(Puzzle.Movimento.BAIXO, 1),
                new PuzzleOperator(Puzzle.Movimento.CIMA, 1),
                new PuzzleOperator(Puzzle.Movimento.ESQ, 1),
                new PuzzleOperator(Puzzle.Movimento.DIR, 1)
        };

        PuzzleProblem p = new PuzzleProblem(puzzle, goal_puzzle, operators);

        Solution s = search.solve(p);
        show_puzzle(search, s, name);
    }

    private static void show_puzzle(SearchMechanism search, Solution solution, String puzzle_name) {
        if (solution == null) {
            String s = String.format(
                    LINE_FORMAT,
                    search.get_title(),
                    puzzle_name, "", "", ""
            );

            System.out.printf("%s", s);
            return;
        }

        double cost = 0;

        for (SolutionStep step : solution) {
            cost = step.get_cost();
        }

        String s = String.format(
                LINE_FORMAT,
                search.get_title(),
                puzzle_name,
                String.valueOf(cost),
                search.get_search_memory().get_max_frontier_nodes(),
                search.get_search_memory().get_max_expanded_nodes()
        );

        System.out.printf(s);
    }
}
