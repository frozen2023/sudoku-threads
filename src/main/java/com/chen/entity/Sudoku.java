package com.chen.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sudoku implements Serializable {

    public static final int LEVEL_1 = 16;
    public static final int LEVEL_2 = 25;
    public static final int LEVEL_3 = 36;
    public static final int LEVEL_4 = 49;

    private static final Map<Integer, Integer> levelConverter = new HashMap<>();

    static {
        levelConverter.put(1, LEVEL_1);
        levelConverter.put(2, LEVEL_2);
        levelConverter.put(3, LEVEL_3);
        levelConverter.put(4, LEVEL_4);
    }

    private final Random random = new Random();

    private int[][] result;

    private int[][] finalResult;

    private int level = LEVEL_4;

    public int[][] getResult() {
        return result;
    }

    public int[][] getFinalResult() {
        return finalResult;
    }

    public Sudoku() {
    }

    public Sudoku(int level) {
        // 初始化数独
        this.result = new int[9][9];
        this.level = level;
        initCenter();
        initGround();
        initCorner();
        this.finalResult = handleDifficulty();
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //打印数独
    public static void display(int[][] arr)
    {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }



    // 初始化中间的九宫格
    public void initCenter() {
        int[] arr = new int[10];//初始化数组
        int n = 0;
        while(n < 9) {
            int randNumber = rand() + 1;
            int flag = 0;
            for(int i = 0;i < n + 1;i ++) {
                if(arr[i] == randNumber) {
                    flag = 1;   //如果之前保存的数中出现了和m相同的数，就把flag标记为1并跳出循环，表示需要重新生成随机数m
                    break;
                }
            }
            if(flag == 0) {
                arr[n] = randNumber;    //如果flag是0，表示前n个数中没有和m相同的数，因此可以把第n+1个元素赋值为m
                n ++;
            }
        }

        int k = 0;
        for (int i = 3; i < 6; ++i)
            for (int j = 3; j < 6; ++j) {
                result[i][j] = arr[k++];
            }
    }


    // 由中间的九宫格交叉变换，初始化上下左右四个九宫格 (交换行列)
    public void initGround() {

        for (int i = 3; i < 6; ++i) {
            int l = 0;
            for (int j = 3; j < 6; ++j) {
                if (i == 3) {
                    result[i + 1][l] = result[i][j];
                    result[i + 2][l + 6] = result[i][j];
                    l++;
                } else if (i == 4) {
                    result[i + 1][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                } else {
                    result[i - 2][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                }
            }
        }
        for (int j = 3; j < 6; ++j) {
            int l = 0;
            for (int i = 3; i < 6; ++i) {
                if (j == 3) {
                    result[l][j + 1] = result[i][j];
                    result[l + 6][j + 2] = result[i][j];
                    l++;
                }
                else if (j == 4) {
                    result[l][j + 1] = result[i][j];
                    result[l + 6][j - 1] = result[i][j];
                    l++;
                }
                else {
                    result[l][j - 2] = result[i][j];
                    result[l + 6][j - 1] = result[i][j];
                    l++;
                }
            }
        }
    }


    // 初始化四个角上的四个九宫格（和上面原理相同）
    public void initCorner () {
        for (int i = 0; i < 3; ++i) {
            int l = 0;
            for (int j = 3; j < 6; ++j) {
                if (i == 0) {
                    result[i + 1][l] = result[i][j];
                    result[i + 2][l + 6] = result[i][j];
                    l++;
                }
                else if (i == 1) {
                    result[i + 1][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                }
                else {
                    result[i - 2][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                }
            }
        }

        for (int i = 6; i < 9; ++i) {
            int l = 0;
            for (int j = 3; j < 6; ++j) {
                if (i == 6) {
                    result[i + 1][l] = result[i][j];
                    result[i + 2][l + 6] = result[i][j];
                    l++;
                } else if (i == 7) {
                    result[i + 1][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                } else {
                    result[i - 2][l] = result[i][j];
                    result[i - 1][l + 6] = result[i][j];
                    l++;
                }
            }
        }
    }

    // 根据难度挖空
    public int[][] handleDifficulty() {

        int[][] newArr = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newArr[i][j] = this.result[i][j];
            }
        }

        int digits = convert(this.level);
        for (int i = 0; i < digits;) {
            int x = rand();
            int y = rand();
            if (newArr[x][y] == 0) {
                continue;
            }
            newArr[x][y] = 0;
            i ++;
        }
        return newArr;
    }

    // 生成随机数
    public int rand(Integer... bound) {
        if (bound.length == 0)
            return random.nextInt(9);
        return random.nextInt(bound[0]);
    }

    public int convert(int pre) {
        return levelConverter.get(pre);
    }


    public static void main(String[] args) {
        int[][] sudo = new Sudoku(3).getFinalResult();
        Sudoku.display(sudo);
    }



}
