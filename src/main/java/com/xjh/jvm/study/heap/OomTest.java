package com.xjh.jvm.study.heap;

import java.sql.Array;
import java.util.ArrayList;

/**
 * @description:
 * 设置堆的大小：
 *  1.-Xms600m -Xmx800m (堆的初始化内存为600m，最大内存为800m)
 *  2.新生代和老年代内存分配比默认为1:2（可能会自适应分配），新生代里Eden区和S0、S1区的内存配比为8:1:1(可能会自适应分配)
 *  3.手动设置新生代和老年代的占比：-XX:-NewRatio2,手动设置Eden和幸存者区(S0+S1)的配比：-XX:-SurvivorRatio=1
 * @author: Mr.Xu
 * @createDate: 2022-11-13 19:11
 * @version: 1.0
 */
public class OomTest {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Object> arrayList = new ArrayList<>();
        while(true){
            //不休眠会马上出现OutOfMemoryError，和堆大小有关
            Thread.sleep(10);
            arrayList.add(new byte[1024 * 10]);
        }

    }

}
