package com.xjh.jvm.study.heap;

/**
 * @description: 设置堆空间大小，初始化10m （-Xms10m）最大10m（-Xmx20m）
 * @author: Mr.Xu
 * @createDate: 2022-11-12 13:54
 * @version: 1.0
 */
public class HeapTest1 {

    public static void main(String[] args) {
        System.out.println("HeapTest1 start~~~~~~~~~");
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("HeapTest1 end~~~~~~~~~");
    }

}
