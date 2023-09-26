package com.chen.service;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class SudokuServiceImpl implements SudokuService{
    @Resource
    private AsyncSudokuGenerator generator;

    @Override
    public CommonResult generateSudokuByThreads(int level) {
        // 清理缓存
        List<Sudoku> sudokus = new ArrayList<>(9);
        /* 线程安全 */
        synchronized (sudokus) {
            for (int i = 0; i < 9; i ++) {
                Future<Sudoku> future = generator.createSudoku(level);
                try {
                    sudokus.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    return CommonResult.error("多线程获取数独失败");
                }
            }
        }

        if (sudokus.size() < 9) {
            return CommonResult.error();
        }
        return CommonResult.success(sudokus);
    }
}
