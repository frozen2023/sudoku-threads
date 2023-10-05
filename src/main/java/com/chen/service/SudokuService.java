package com.chen.service;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;

import java.util.List;

public interface SudokuService {

    CommonResult generateSudokuByThreads(int level);

    CommonResult solveSudokuByThreads(List<int[][]> sudokus);
}
