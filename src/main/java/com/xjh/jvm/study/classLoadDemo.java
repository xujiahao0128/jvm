package com.xjh.jvm.study;

import com.sun.nio.zipfs.ZipInfo;
import org.junit.jupiter.api.Test;
import sun.misc.Launcher;

import java.net.URL;

import static java.lang.System.out;
import static sun.misc.Version.print;

/**
 * @description: 类加载器类型：
 *              1.引导类加载器/启动类加载器（BootStrap ClassLoader）：C语言编写,加载java的核心类库，包括系统类加载器和自定义类加载器
 *              2.系统类加载器（ClassLoader）：是一个抽象类，除了引导类加载器其他类加载器都是该类的派生类
 *              3.用户自定义类加载器（User DeFined ClassLoader）: 派生于ClassLoader类加载器（系统类加载器） 的都是用户自定义类加载器
 * @author: Mr.Xu
 * @createDate: 2022-07-25 21:21
 * @version: 1.0
 */
public class classLoadDemo {

    public static void main(String[] args) {


        //系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        //systemClassLoader.toString() = sun.misc.Launcher$AppClassLoader@18b4aac2    class: sun.misc.Launcher$AppClassLoader
        out.println("systemClassLoader.toString() = " + systemClassLoader.toString() + "class:" + systemClassLoader.getClass().getName());

        //系统类加载器的上层(扩展类加载器)
        ClassLoader extClassLoader = systemClassLoader.getParent();
        //extClassLoader.toString() = sun.misc.Launcher$ExtClassLoader@5305068a    class:  sun.misc.Launcher$ExtClassLoader
        out.println("extClassLoader.toString() = " + extClassLoader.toString() + "class:" + extClassLoader.getClass().getName());

        //扩展类加载器加载器的上层（引导类加载器)
        ClassLoader parent2 = extClassLoader.getParent();
        //null
        out.println("parent2 = " + parent2);

        //当前类的类加载器
        ClassLoader currentClassLoad = classLoadDemo.class.getClassLoader();
        //currentClassLoad = sun.misc.Launcher$AppClassLoader@18b4aac2 ===== class: sun.misc.Launcher$AppClassLoader
        out.println("currentClassLoad = " + currentClassLoad + " ===== class: " + currentClassLoad.getClass().getName());

        //String类的类加载器,String类使用的是遇到类加载器   ----》 Java的核心类库都是引导类加载的
        ClassLoader stringClassLoader = String.class.getClassLoader();
        //null
        out.println("stringClassLoader = " + stringClassLoader);

    }

    @Test
    public void test() throws ClassNotFoundException {

        //引导类加载器加载的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            out.println("urL = " + urL.toExternalForm());
        }
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/resources.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/rt.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/sunrsasign.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/jsse.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/jce.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/charsets.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/lib/jfr.jar
        //urL = file:/C:/Program%20Files/Java/jdk1.8.0_152/jre/classes

        //rt.jar  java.lang下面有String类，看下String的类加载器
        ClassLoader stringClassLoader = String.class.getClassLoader();
        //stringClassLoader = null 再次验证java核心类库都是由引导类加载器加载的
        out.println("stringClassLoader = " + stringClassLoader);

        //扩展类加载器加载目录
        String extProperty = System.getProperties().getProperty("java.ext.dirs");
        //extProperty = C:\Program Files\Java\jdk1.8.0_152\jre\lib\ext;  C:\WINDOWS\Sun\Java\lib\ext
        out.println("extProperty = " + extProperty);
        //去ext目录下找一个类查看其类加载器
        ClassLoader extClassLoader = ZipInfo.class.getClassLoader();
        //extClassLoader = sun.misc.Launcher$ExtClassLoader@76ed5528  可以看到是扩展类加载器
        out.println("extClassLoader = " + extClassLoader);

        // 获取类加载器的几种方式

        //1.获取当前类的类加载器,Class.forName返回的是个类，我们也可以使用具体的类
        Class<?> aClass = Class.forName("java.lang.String");
        ClassLoader forName = aClass.getClassLoader();
        out.println("forName = " + forName);

        //2.获取当前线程上下文的类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        out.println("contextClassLoader = " + contextClassLoader);

        //3.获取当前系统的类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        out.println("systemClassLoader = " + systemClassLoader);



    }

}
