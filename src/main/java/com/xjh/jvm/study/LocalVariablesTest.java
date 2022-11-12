package com.xjh.jvm.study;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @description: 通过javap -v xxx.class 查看本地变量表
 * @author: Mr.Xu
 * @createDate: 2022-10-15 14:54
 * @version: 1.0
 */
public class LocalVariablesTest {

    //java八大基本数据类型（byte、char、short、int、float、double、long、boolean）作为类的成员变量时会初始化比如aDouble就为0.0
    //但是如果是包装类那就是null，比如Double就是null,所以只要是引用对象没有进行赋值那就是null
    public double aDouble;
    public Son son;

    public static void main(String[] args) {
        LocalVariablesTest localVariablesTest = new LocalVariablesTest();
        int num = 10;
        localVariablesTest.test();
    }

    public void test(){
        Date date = new Date();
        String str = "test";
        System.out.println("str = " + str);
        test2(date);
    }

    //本地变量表实际上是一个数字类型的数组，局部变量、方法返回地址，byte、char、short、boolean都会转化为int类型存入，
    // 查看test2的字节码指令发现double、long（8byte）类型的占用两个slot，其余占用1个slot
    public String test2(Date date){
        char c = 'c';
        double d = 2.22;
        String str2 = "test2";
        System.out.println("str2 = " + str2);
        return str2;
    }

    public void test3(){
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        //通过jclasslib查看本地变量表发现本来是有四个槽位（this/a/b/c）但是实际上只有三个
        //因为b的作用域只在40行，过了就销毁了，于是c就继续使用b的槽位，达到节省资源的目的
        int c = a + 1;
        throw new NumberFormatException();
    }

    @Test
    public void test5(){
        System.out.println("son = " + son);
    }


    /**         test6的字节码指令
     0 iconst_1
     1 istore_1
     2 iconst_2
     3 istore_2
     4 iload_1
     5 iload_2
     6 iadd
     7 istore_3
     8 aload_0
     9 iload_3 */
    @Test
    public void test6(){
        //     0 iconst_1 定义了变量a并且加入当前线程的栈帧的操作数栈
        //     1 istore_1 存入当前线程的本地变量表下标为1
        int a = 1;
        //     2 iconst_2 定义了变量b并且加入当前线程的栈帧的操作数栈
        //     3 istore_2 存入当前线程的本地变量表下标为2
        int b = 2;
        //     4 iload_1 从本地变量表加载下标为1的变量到栈帧
        //     5 iload_2 从本地变量表加载下标为2的变量到栈帧
        //     6 iadd 执行add操作并且出栈
        //     7 istore_3 将变量c存入当前线程的本地变量表下标为3
        int c = a + b;
        //     8 aload_0 加载getSum方法到当前线程的栈帧
        //     9 iload_3 调用方法getSum需要变量c，所以加载本地变量表下标为3的变量c到栈帧的操作数栈
        //     10 invokevirtual #25 <com/xjh/jvm/study/LocalVariablesTest.getSum : (I)I>  调用getSum方法
        //     注意此时新开了一个栈帧执行getSum方法的内部逻辑了，可以发现11、12步不在这里，但是我们可以知道：
        //     11 执行代码int d = sum * 2;
        //     12 将getSum方法的返回值压入栈帧
        //     13 istore 4 将getSum方法的返回值赋值给sum变量存入当前线程的本地变量表下标为4
        int sum = getSum(c);
        System.out.println("sum = " + sum);
    }

    /**
     0 iload_1
     1 iconst_2
     2 imul
     3 istore_2
     4 iload_2
     5 ireturn
     */
    public int getSum(int sum){
        //     0 iload_1 有参数sum，位于本地变量表下标为1的位置，加载sum
        //     1 iconst_2 声明变量d加入当前栈帧的操作数栈
        //     2 imul 执行乘法操作
        //     3 istore_2 将值赋予变量的，同时存入本地变量表下标为2
        int d = sum * 2;
        //     4 iload_2 加载本地变量表下标为2的变量
        //     5 ireturn 返回变量值加入栈帧
        return d;
    }

}
