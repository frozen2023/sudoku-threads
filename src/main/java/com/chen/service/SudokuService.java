package com.chen.service;

import com.chen.entity.Sudoku;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class SudokuService {

    @Async
    public Future<Sudoku> createSudoku(int level) {
        System.out.println("线程名：" + Thread.currentThread().getName());
        Sudoku result = new Sudoku(level);
        return new AsyncResult<>(result);
    }
}
