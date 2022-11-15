package com.xjh.jvm.study.heap;

/**
 * @description: 设置堆空间大小，初始化20m （-Xms20m）最大20m（-Xmx20m）
 * @author: Mr.Xu
 * @createDate: 2022-11-12 13:54
 * @version: 1.0
 */
public class HeapTest2 {

    public static void main(String[] args) {
        System.out.println("HeapTest2启动~~~~~~~~~");
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("HeapTest2 end~~~~~~~~~");
    }

}
