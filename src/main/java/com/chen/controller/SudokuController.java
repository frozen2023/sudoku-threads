package com.chen.controller;

import com.chen.common.CommonResult;
import com.chen.service.SudokuServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SudokuController {

    @Resource
    private SudokuServiceImpl sudokuService;

    @GetMapping("/sudoku")
    public CommonResult getSudoku(@RequestParam("level") Integer level) {
        return sudokuService.generateSudokuByThreads(level);
    }

}
