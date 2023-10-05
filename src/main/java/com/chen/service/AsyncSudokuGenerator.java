package com.chen.service;

import com.chen.entity.Sudoku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncSudokuGenerator {

    @Async
    public Future<Sudoku> createSudoku(int level) {
        log.info("开启线程[{}]进行数独的生成", Thread.currentThread().getName());
        Sudoku result = new Sudoku(level);
        return new AsyncResult<>(result);
    }

}
