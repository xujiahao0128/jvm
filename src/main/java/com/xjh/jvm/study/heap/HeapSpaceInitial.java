package com.xjh.jvm.study.heap;

/**
 * @description: 设置对空间大小的参数: -Xms10m -Xmx10m
 *  -X:是jvm的运行参数
 *  1. -Xmx[内存大小] 设置堆空间（年轻代+老年代）的初始化内存大小,ms为memory start的缩写
 *  2. -Xsx[内存大小] 设置堆空间（年轻代+老年代）的最大内存大小
 *
 *  堆空间的大小默认为当前运行服务器的内存大小的64分之一，最大为四分之一
 *
 *  查看设置的方式：
 *      1.jstat -gc [pid] :查看当前gc的情况
 *      2.设置启动参数 -XX:+PrintGCDetails，运行结束会输出对应的GC信息
 * @author: Mr.Xu
 * @createDate: 2022-11-12 16:42
 * @version: 1.0
 */
public class HeapSpaceInitial {

    public static void main(String[] args) {

        //当前运行的jvm的runtime实例（理解为运行时数据区）
        Runtime currentRuntime = Runtime.getRuntime();
        //java虚拟机中内存堆的总量
        long totalMemory = currentRuntime.totalMemory() / 1024 / 1024;
        //java虚拟机试图使用最大堆内存量
        long maxMemory = currentRuntime.maxMemory() / 1024 / 1024;

        System.out.println("-Xms  totalMemory = " + totalMemory + "M");
        System.out.println("-Xmx  maxMemory = " + maxMemory + "M");

        System.out.println("系统最大内存大小为：" + totalMemory * 64 / 1024 + "G");
        System.out.println("系统最大内存大小为：" + maxMemory * 4 / 1024 + "G");

    }

}
