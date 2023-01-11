package com.xjh.jvm.study.StringTest;

import org.junit.jupiter.api.Test;

/**
 * @author Mr.Xu
 * @Description
 * @date 2023年01月11日 15:38
 */
public class StringNewTest {

    //分析不同方式创建字符串生成了几个对象
    @Test
    public void test(){
        // 0 ldc #2 <a> : 只在字符串常量池创建了一个对象a
        // 2 astore_1
        // 3 return
        String str3 = "a";

        // 0 new #2 <java/lang/String>  :堆空间new了一个String类型的对象
        // 3 dup
        // 4 ldc #3 <ab>  ：在字符串常量池创建了字面量ab对象
        // 6 invokespecial #4 <java/lang/String.<init> : (Ljava/lang/String;)V> ：
        // 调用String的构造方法将ab赋值给Sting
        // 9 astore_1
        // 10 return
        String str = new String("ab");

        // 0 new #2 <java/lang/StringBuilder>   : 1.字符串拼接操作底层创建StringBuilder对象
        // 3 dup
        // 4 invokespecial #3 <java/lang/StringBuilder.<init> : ()V>
        // 7 new #4 <java/lang/String>    ：2.在堆空间创建String("a")对象,此时还未初始化
        // 10 dup
        // 11 ldc #5 <a> ：3.在字符串常量池创建字面量a
        // 13 invokespecial #6 <java/lang/String.<init> : (Ljava/lang/String;)V>
        // ：调用String的构造方法给String("a")对象赋值a
        // 16 invokevirtual #7 <java/lang/StringBuilder.append
        // :(Ljava/lang/String;)Ljava/lang/StringBuilder;>  : 拼接a
        // 19 new #4 <java/lang/String> ：4.在堆空间创建String("b")对象,此时还未初始化
        // 22 dup
        // 23 ldc #8 <b> ：5.在字符串常量池创建字面量b
        // 25 invokespecial #6 <java/lang/String.<init> : (Ljava/lang/String;)V>
        // ：调用String的构造方法给String("b")对象赋值b
        // 28 invokevirtual #7 <java/lang/StringBuilder.append :
        // (Ljava/lang/String;)Ljava/lang/StringBuilder;>  : 拼接b
        // 31 invokevirtual #9 <java/lang/StringBuilder.toString : ()Ljava/lang/String;> :
        // 6.调用StringBuilder的toString方法，实际上底层是new String(value, 0, count);
        // 34 astore_1
        // 35 return
        String str2 = new String("a") + new String("b");
    }
}
