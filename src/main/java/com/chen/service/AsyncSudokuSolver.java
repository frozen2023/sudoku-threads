package com.chen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncSudokuSolver {

    @Async
    public Future<int[][]> solve(int[][] arrayToBeSolved) {
        log.info("开启线程[{}]进行数独的求解", Thread.currentThread().getName());
        // 列的1-9
        boolean[][] cols = new boolean[9][9];
        // 行的1-9
        boolean[][] rows = new boolean[9][9];
        // 块的1-9
        boolean[][] blocks = new boolean[9][9];

        int[][] result = Arrays.copyOf(arrayToBeSolved, 9);

        // 初始化数组
        initBooleanArray(cols, false);
        initBooleanArray(rows, false);
        initBooleanArray(blocks, false);

        // 记录初始值
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 这一格有数
                if (arrayToBeSolved[i][j] != 0) {
                    /* 计算所在块
                     * 1  4  7
                     * 2  5  8
                     * 3  6  9
                     * */
                    int blcIdx = (i / 3) * 3 + j / 3;
                    rows[i][arrayToBeSolved[i][j] - 1] = true;
                    cols[j][arrayToBeSolved[i][j] - 1] = true;
                    blocks[blcIdx][arrayToBeSolved[i][j] - 1] = true;
                }
            }
        }
        // 深度优先遍历
        if (!dfs(result, cols, rows, blocks)) {
            log.info("求解失败！");
            // 求解失败返回原数组
            return new AsyncResult<>(arrayToBeSolved);
        }
        return new AsyncResult<>(result);
    }

    public static boolean dfs(int[][] arr, boolean[][] cols,
                              boolean[][] rows, boolean[][] blocks) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (arr[i][j] == 0) {
                    int k = i / 3 * 3 + j / 3;
                    for (int l = 0; l < 9; l++) {
                        if (!cols[j][l] && !rows[i][l] && !blocks[k][l]) {
                            // l对于的数字l+1没有在行列块中出现
                            rows[i][l] = cols[j][l] = blocks[k][l] = true;
                            // 下标加1
                            arr[i][j] = 1 + l;
                            // 递进则返回true
                            if (dfs(arr, cols, rows, blocks))
                                return true;
                            // 递进失败则回溯
                            rows[i][l] = cols[j][l] = blocks[k][l] = false;
                            arr[i][j] = 0;
                        }
                    }
                    // a[i][j]==0时，l发现都不能填进去
                    return false;
                }
            }
        }
        // 没有a[i][j]==0,则返回true
        return true;
    }

    public void initBooleanArray(boolean[][] arr, boolean with) {
        for (boolean[] booleans : arr) {
            Arrays.fill(booleans, with);
        }
    }

}
