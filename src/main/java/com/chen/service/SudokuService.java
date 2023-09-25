package com.chen.service;

import com.chen.common.CommonResult;

public interface SudokuService {

    CommonResult generateSudokuByThreads(int level);
}
