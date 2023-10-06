package com.chen;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import com.chen.service.AsyncSudokuSolver;
import com.chen.service.SudokuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SudokuThreadsApplicationTests {

    @Resource
    private SudokuService sudokuService;
    @Resource
    private AsyncSudokuSolver solver;

    @Test
    public void testGenerateSudoku() {
        int level = 4;
        CommonResult commonResult = sudokuService.generateSudokuByThreads(level);
        List<Sudoku> sudokus = (List<Sudoku>) commonResult.getData();
        for (Sudoku sudoku : sudokus) {
            int[][] result = sudoku.getResult();
            int[][] finalResult = sudoku.getFinalResult();
            System.out.println("题目：");
            Sudoku.display(finalResult);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSolveSudoku() {
        int level = 4;
        List<Sudoku> sudokus = (List<Sudoku>) sudokuService.generateSudokuByThreads(level).getData();
        List<int[][]> solution = new ArrayList<>();
        for (Sudoku each : sudokus) {
            solution.add(each.getFinalResult());
        }
        List<int[][]> arrs = (List<int[][]>) sudokuService.solveSudokuByThreads(solution).getData();

        for (int i = 0; i < solution.size(); i++) {
            System.out.println("题目" + i);
            Sudoku.display(sudokus.get(i).getFinalResult());
            System.out.println("题解" + i);
            Sudoku.display(arrs.get(i));
        }
    }


    @Test
    void contextLoads() throws InterruptedException {
    }

}
