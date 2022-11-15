package com.xjh.jvm.study;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author Mr.Xu
 * @Description :正常情况下GC大部分是新生代发送的，且大概率是minor GC
 * 测试 minor Gc（又叫ygc，发送在新生代的Eden、Survivor0、Survivor1）、major GC（又叫ogc，发生在老年代）、full GC（堆的全量gc，包括新生代、老年代、元空间/方法区）
 * 查看gc输出：-XX+PrintGCDetails
 * @date 2022年11月14日 11:37
 */
public class OomTest {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Object> objects = new ArrayList<>();

        while(true){
            objects.add(new byte[1024 * 20]);
            Thread.sleep(10);
        }

    }


}
