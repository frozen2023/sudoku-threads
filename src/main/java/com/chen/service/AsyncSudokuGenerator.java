package com.chen.service;

import com.chen.entity.Sudoku;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncSudokuGenerator {

    @Async
    public Future<Sudoku> createSudoku(int level) {
        System.out.println("线程名：" + Thread.currentThread().getName());
        Sudoku result = new Sudoku(level);
        return new AsyncResult<>(result);
    }

}
