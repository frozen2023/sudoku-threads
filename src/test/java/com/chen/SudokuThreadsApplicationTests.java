package com.chen;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import com.chen.service.SudokuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SudokuThreadsApplicationTests {

    @Resource
    private SudokuService sudokuService;

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
            System.out.println("答案：");
            Sudoku.display(result);
        }
    }

    @Test
    void contextLoads() {
    }

}
