package com.chen.controller;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import com.chen.service.SudokuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SudokuController {

    @Resource
    private SudokuService sudokuService;

    @GetMapping("/sudoku")
    public CommonResult getSudoku(@RequestParam("level") Integer level) {
        return sudokuService.generateSudokuByThreads(level);
    }

    @PostMapping("/sudoku/solve")
    public CommonResult solveSudoku(@RequestBody List<int[][]> sudokus) {
        return sudokuService.solveSudokuByThreads(sudokus);
    }
}
