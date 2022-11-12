package com.xjh.jvm.study;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @description: 类初始化
 * @author: Mr.Xu
 * @createDate: 2022-07-19 21:41
 * @version: 1.0
 */
public class ClassInit {

    //类常量，在编译阶段就初始化了（.java文件编译为.class文件），不可变，不能再次赋值
    private final static int constant = 66;

    private final static int p = 1;

    static{
        //类变量赋值可以在声明之前，但是不能在这里输出
        num = 20;
        //Illegal forward reference (编译不通过，非法的向前引用)
        //System.out.println(num);
    }

    //类变量，在类初始化时就一起赋值了
    private static int num = 10;

    public static void main(String[] args) {
        System.out.println("num = " + num);
    }

    /** 演示子类初始化，会先执行父类的初始化（clinit） */
    @Test
    public void test(){
        System.out.println("Son.b = " + Son.b);
    }

    /** 演示多线程下也只会执行一次static，也就是说只会有一个类模板 */
    @Test
    public void testClassLoadLock() throws InterruptedException {

        Runnable run1 = () ->{
            Parent parent = new Parent();
        };

        Thread thread2 = new Thread(run1, "线程2");
        thread2.start();

        Thread thread1 = new Thread(run1, "线程1");
        thread1.start();

        TimeUnit.SECONDS.sleep(20);
    }

}



class Parent{

    public static int a = 1;

    static {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        a = 2;
        System.out.println("parent 静态代码块执行~~");
        if (true){

            System.out.println("parent 构造器执行~~当前线程：" + Thread.currentThread().getName());
            while (true){
            }
        }
    }
}

class Son extends Parent{

    public static int b = a;

}


