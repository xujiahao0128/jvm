package com.xjh.jvm.study.StringTest;

import cn.hutool.core.date.StopWatch;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @author Mr.Xu
 * @Description TODO
 * @date 2023年01月10日 14:50
 */
public class StringTest {

    final UserTest userTest= new UserTest();
    String str = new String("good");
    char [] chars = {'a','b','c'};

    public void change(UserTest userTest,String str , char [] chars){
        userTest.setName("java");
        userTest.setAge(99);
        System.out.println("change userTest=>" + userTest.hashCode());
        System.out.println("change start str =》" + str.hashCode() + str);
        str = "bad";
        str.intern();
        System.out.println("change end str=》" + str.hashCode() + str);
        chars[0] = 'z';
    }

    public static void main(String[] args) {

        /** tips:可以看到idea提示new String是可忽略的，但并不意味着 new String("str") 和 String str = "str"是没区别的
         *  new String是会在堆中存储而String str不会在堆中，只在字符串常量池中存在，
         *  当然不在堆中的说法只是为了区分堆和字符串常量池，因为字符串常量池也在堆中*/

        //在字符串常量池创建了一个字符串java
        String sb1 = "java";
        //返回字符串常量池的字符串java引用
        String sb2 = sb1.intern();
        //所以sb1和sb2的都指向同一引用地址true
        System.out.println(sb1 == sb2);

        //new了两个字符串1和1在堆中，拼接操作也是在堆中创建了一个字符串11，同时字符串常量池存在了1和11
        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);//true

        String sb3 = new String("hello");
        String sb4 = sb3.intern();
        System.out.println(sb3 == sb4);//false
    }

    @Test
    public void test(){

        StringTest stringTest = new StringTest();
        System.out.println("begin userTest=>" + stringTest.userTest.hashCode());
        System.out.println("begin =》"+ stringTest.hashCode() + stringTest.str);

        stringTest.change(stringTest.userTest,stringTest.str,stringTest.chars);

        System.out.println("end =》"+ stringTest.hashCode() + stringTest.str);
        System.out.println(stringTest.chars);
        System.out.println(stringTest.userTest);
    }

    @Test
    public void test1(){
        // 编译成class文件后其实是（编译优化）
        // String str1 = "abc";
        // String str2 = "abc";
        String str1 = "a" + "b" + "c";
        String str2 = "abc";

        // 注意不能这样子输出，会变成false，因为str会先拼接"str1 == str2:"再去比较str2  System.out.println("str1 == str2:" + str1 == str2);
        System.out.println(str1 == str2);
    }

    @Test
    public void test2(){
        String str0 = "c";
        String str1 = "ab" + str0;
        String str2 = "a" + "b" + "c";
        //false，拼接存在变量就相当于在堆里new的字符串，所以str1返回在堆里的abc地址，str2返回字符串常量池的地址，
        // 存在变量拼接查看字节码底层是new StringBuilder()，通过append方法拼接最终toString方法返回地址，该方法类似于new String
        System.out.println(str1 == str2);

        final String str3 = "c";
        final String str4 = "ab" + str3;
        //true，final修饰的常量是不可变的，返回的是引用
        System.out.println(str4 == str2);

    }

    @Test
    public void test3(){
        int count = 100000;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("字符串拼接方式");
        String str1 = "";
        for (int i = 0; i < 100000; i++) {
            //通过字节码可以分析出来每次拼接都会new StringBuilder，并且str1重新赋值会重新创建Sting对象，这种方式占用内存更多执行时间更长
            str1 = str1 + i;
        }
        stopWatch.stop();

        stopWatch.start("append拼接方式起始");
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 100000; i++) {
            sb.append(i);
        }
        stopWatch.stop();
        System.out.println("stopWatch.prettyPrint() = " + stopWatch.prettyPrint());
        // 27s   字符串拼接方式
        // 0.2s  append拼接方式起始
  }

    @Test
    public void test4(){
        String str = new String("a") + new String("b");
        str.intern();
        String str2 = "ab";
        //true，str是堆空间地址，str2指向的是字符串常量池，但是jdk7起为了节省内存，调用intern方法如果不存在当前字符串那么就会在字符串常量池新增ab，
        // 因为在堆中已经存在ab所以字符串常量池不会再创建ab对象，而是让字符串常量池ab去引用堆中的ab，所以地址是一样的，
        // 前提是字符串常量池不存在当前字符串对象但是字符串常量池外的堆空间有
        System.out.println(str == str2);

        //堆空间和字符串常量池都有一个a对象，所以调用intern直接返回字符串常量池地址
        String str3 = new String("a");
        str3.intern();
        String str4 = "a";
        //false,str3是堆空间地址，str4是字符串常量池地址
        System.out.println(str3 == str4);

        String str5 = new String("a") + new String("b");
        //字符串常量池加入ab
        String str6 = "ab";
        //字符串常量池有ab直接返回地址
        String intern = str5.intern();
        //false
        System.out.println(str5 == str6);
        //true
        System.out.println(intern == str6);
    }


}

@Data
class UserTest{

    public String name = "xjh";

    public int age = 22;

}