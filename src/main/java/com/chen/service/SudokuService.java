package com.chen.service;

import com.chen.common.CommonResult;

import java.util.List;

/**
 * @author Frozen
 */
public interface SudokuService {
    CommonResult generateSudokuByThreads(int level);
    CommonResult solveSudokuByThreads(List<int[][]> sudokus);
}
