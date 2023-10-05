package com.chen.controller;

import com.chen.common.CommonResult;
import com.chen.entity.Sudoku;
import com.chen.service.SudokuService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(SudokuController.class)
public class SudokuControllerTest {

    @MockBean
    private SudokuService sudokuService;

    @Resource
    private SudokuController sudokuController;

    @Resource
    private MockMvc mvc;

    @Test
    public void getSudoku() throws Exception {

        List<Sudoku> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new Sudoku(3));
        }
        CommonResult commonResult = CommonResult.success(list);
        // 使用Mockito 模拟service
        Mockito.when(sudokuService.generateSudokuByThreads(3)).thenReturn(commonResult);
        this.mvc.perform(MockMvcRequestBuilders.get("/sudoku")
                        .param("level", "3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
