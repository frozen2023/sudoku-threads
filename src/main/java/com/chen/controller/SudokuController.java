package com.chen.controller;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import com.chen.service.SudokuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class SudokuController {

    @Resource
    private SudokuService sudokuService;

    @GetMapping("/sudoku")
    public CommonResult getSudoku(@RequestParam("levelList")List<Integer> levelList) {
        List<Sudoku> data = new ArrayList<>(9);
        for (Integer integer : levelList) {
            Future<Sudoku> future = sudokuService.createSudoku(integer);
            try {
                data.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                return CommonResult.error("多线程获取数独失败");
            }
        }

        if (data.size() < 9) {
            return CommonResult.error();
        }

        for (Sudoku item : data) {
            Sudoku.display(item.getFinalResult());
        }

        return CommonResult.success(data);
    }

}
