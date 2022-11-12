package com.xjh.jvm.study;

/**
 * @description: i++ 和 ++i的解读（字节码层面）
 * @author: Mr.Xu
 * @createDate: 2022-10-16 14:46
 * @version: 1.0
 */
public class StackAddITest {

    /**
     * JVM中 int 类型数值，根据 取值范围将 入栈的 字节码指令 就分为4类：
     * 取值 -1~5 采用 iconst 指令；8byte
     * 取值 -128~127 采用 bipush 指令；16byte
     * 取值 -32768~32767 采用 sipush指令；32byte
     * 取值 -2147483648~2147483647 采用 ldc 指令 64byte
     * 其实就是对占用不同字节的变量使用对应的字节码指令
     * */

    public static void main(String[] args) {
        //1.

        //  0 bipush 10
        //  2 istore_1
        int i1 = 10;

        //  3 iinc 1 by 1
        i1++;
        //没想到一个简简单单的输出语句居然使用了这么多的字节码指令 ==>> System.out.println("i1 = " + i1)
        //  6 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;> : 获取输出流对象
        //  9 new #3 <java/lang/StringBuilder> ：由于使用了字符串拼接变量输出，所以新建了一个StringBuilder对象
        // 12 dup ：该指令是类型转换，输出的是字符串变量 i1 是数字类型，需要转换为String
        // 13 invokespecial #4 <java/lang/StringBuilder.<init> : ()V> ：调用StringBuilder的初始化方法
        // 16 ldc #5 <i1 = > ：字符串 "i1 = " 入栈,就是输出语句里面的字符串
        // 18 invokevirtual #6 <java/lang/StringBuilder.append : (Ljava/lang/String;)Ljava/lang/StringBuilder;> ：调用StringBuilder的append方法拼接 "i1 = "
        // 21 iload_1 : 加载本地变量表下标为1的变量（实际上就是i1）到栈帧
        // 22 invokevirtual #7 <java/lang/StringBuilder.append : (I)Ljava/lang/StringBuilder;> 调用StringBuilder的append方法拼接 i1变量的值（已经转换了类型）
        // 25 invokevirtual #8 <java/lang/StringBuilder.toString : ()Ljava/lang/String;> ：调用StringBuilder的toString方法
        // 28 invokevirtual #9 <java/io/PrintStream.println : (Ljava/lang/String;)V> ：调用输出流
        //System.out.println("i1 = " + i1);//11

        int i2 = 10;
        ++i2;
        //System.out.println("i2 = " + i2);//11

        //2.

        //12 bipush 10
        //14 istore_3
        int i3 = 10;
        //先赋值再++
        //15 iload_3
        //16 iinc 3 by 1
        //19 istore 4
        int i4 = i3++;
        //System.out.println("i4 = " + i4);//10

        int i5 = 10;
        //先++再赋值
        int i6 = ++i5;
        //System.out.println("i6 = " + i6);//11

        //3.
        //32 bipush 10
        //34 istore 7
        //36 iload 7
        //38 iinc 7 by 1
        //41 istore 7
        int i7 = 10;
        i7 = i7++;
        //System.out.println("i7 = " + i7);//10

        //这里为什么i7输出10而i8输出11，可以仔细看两边的字节码操作指令：
        //i7是在++之前先load从本地变量表中获取到i7的值为10，当++完成的时候并没有再次去本地变量表中load当前值到当前栈帧中，所以输出时获取到当前栈帧的值还是为10
        //i8是在++之后再去本地变量表中load到i8的值到当前栈帧中所以输出为11

        //43 bipush 10
        //45 istore 8
        //47 iinc 8 by 1
        //50 iload 8
        //52 istore 8
        int i8 = 10;
        i8 = ++i8;
        //System.out.println("i8 = " + i8);//11

        //4.

        //54 bipush 10 ：i9赋值为10加入当前栈帧的操作数栈
        //56 istore 9 ：i9存入当前栈帧的本地变量表下标为9

        int i9 = 10;
        //58 iload 9 ：本地变量表加载下标为9的变量到当前栈帧 此时为 i9 == 10
        //60 iinc 9 by 1 ：加 1
        //63 iinc 9 by 1 ：加 1
        //66 iload 9 :再次从当前栈帧的本地变量表加载下标为9的变量 此时 i9 == 12
        //68 iadd : 两数相加 此时 i10 == 22
        //69 istore 10 : i10加入本地变量表
        int i10 = i9++ + ++i9;
        //System.out.println("i10 = " + i10);//22

        //71 bipush 10 : i11赋值加入当前栈帧的操作数栈
        //73 istore 11 ：i11加入本地变量表下标为11
        int i11 = 10;
        //75 iinc 11 by 1 ： ++i11执行的+1
        //78 iload 11 ：++i11的load 此时 i11 == 11
        //80 iload 11 : i11++的load 此时 i11 == 11
        //82 iinc 11 by 1 ： i11++执行的+1
        //85 iadd ：两数相加 i11 + i11 == 22
        //86 istore 12 : 存入本地变量表
        int i12 = ++i11 + i11++;
        //System.out.println("i12 = " + i12);//22

        //下面其实都差不多，下要注意的就是load的加载位置 i++先加载在赋值，此时加载的值还是原先的值但是已经进入操作数栈 ++i是先赋值在load，此时操作数栈的值是新的

        // 88 bipush 10
        // 90 istore 13
        // 92 iload 13
        // 94 iinc 13 by 1
        // 97 iload 13
        // 99 iinc 13 by 1
        //102 iadd
        //103 istore 14
        int i13 = 10;
        int i14 = i13++ + i13++;
        //System.out.println("i14 = " + i14);//21

        //105 bipush 10
        //107 istore 15
        //109 iinc 15 by 1
        //112 iload 15
        //114 iinc 15 by 1
        //117 iload 15
        //119 iadd
        //120 istore 16
        int i15 = 10;
        int i16 = ++i15 + ++i15;
        //System.out.println("i16 = " + i16);//23
    }


}
