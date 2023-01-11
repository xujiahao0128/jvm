package com.xjh.jvm.study.metaspace;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author Mr.Xu
 * @Description ：测试元空间内存泄露
 * jdk1.8之前叫方法区，jdk1.8之后叫元空间，在windows默认初始值21m，最大限制为(-1)当前服务器内存，也就是说可以耗尽当前内存
 * 设置元空间的初始值：-XX:MetaSpaceSize=10m ,设置元空间的最大值：-XX:MaxMetaSpaceSize=10m
 * 当元空间的初始化值不够用的时候会触发full GC，所以一般初始化容量适当高一些可以减少gc次数，但是还是需要根据程序设置，初始化值会根据gc动态变化
 * @date 2022年11月15日 11:13
 */
public class MetaSpaceOOM extends ClassLoader{

    public static void main(String[] args) throws InterruptedException {

        //创建次数
        int count = 0;

        System.out.println("测试一个文件部分提交2222");

        try{
            MetaSpaceOOM metaSpaceOOM = new MetaSpaceOOM();

            for (int i = 0; i < 5000; i++) {
                ClassWriter classWriter = new ClassWriter(0);
                String className = "Class" + i;
                classWriter.visit(Opcodes.V1_6,Opcodes.ACC_PUBLIC,className,null,"java/lang/Object",null);
                //生成类字节码
                byte[] code = classWriter.toByteArray();
                //加载到内存
                metaSpaceOOM.defineClass(className,code,0,code.length);
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            System.out.println("count = " + count);
        }

        Thread.sleep(1000000);

    }

}
