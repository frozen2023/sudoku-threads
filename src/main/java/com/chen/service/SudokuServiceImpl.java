package com.chen.service;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SudokuServiceImpl implements SudokuService{

    private final AsyncSudokuGenerator generator;

    private final AsyncSudokuSolver solver;

    @Override
    public CommonResult generateSudokuByThreads(int level) {
        List<Future<Sudoku>> futures = new ArrayList<>(9);
        /* 线程安全 */
        synchronized (futures) {
            for (int i = 0; i < 9; i ++) {
                Future<Sudoku> future = generator.createSudoku(level);
                futures.add(future);
            }
        }

        List<Sudoku> sudokus = futures.stream().map(sudokuFuture -> {
            try {
                return sudokuFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        if (futures.size() < 9) {
            return CommonResult.error();
        }
        return CommonResult.success(sudokus);
    }

    @Override
    public CommonResult solveSudokuByThreads(List<int[][]> sudoku) {
        List<int[][]> result = sudoku.stream()
                .map(solver::solve)
                .map(each -> {
                    try {
                        return each.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return CommonResult.success(result);
    }

}
