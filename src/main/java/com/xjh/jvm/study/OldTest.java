package com.xjh.jvm.study;

/**
 * @author Mr.Xu
 * @Description TODO
 * @date 2022年11月14日 15:08
 */
public class OldTest {

    public static void main(String[] args) {
        /** 直接跳过Eden区分配对象在老年代 -Xms10m -Xmx10m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGCDetails */
        byte[] bytes = new byte[1024 * 1024 * 9];
    }

}
