package com.xjh.jvm.study;

/**
 * @description: 动态链接
 * @author: Mr.Xu
 * @createDate: 2022-10-16 17:00
 * @version: 1.0
 */
public class DynamicLinkingTest {

    public static void main(String[] args) {
        //父类的声明，子类的引用
        AClass aClass = new BClass();
        //实际上调用子类的方法
        aClass.getDesc();

        //不强转编译不通过，强转后无法转换ClassCastException 也就是说子类可以转父类且是隐式的，父类转子类需要强转
/*        BClass bClass = (BClass) new AClass();
        bClass.getDesc();*/
    }

}

class AClass{

    public void getDesc(){
        System.out.println("AClass");
    }

}

class BClass extends AClass{

    public void getDesc(){
        System.out.println("BClass");
    }

}