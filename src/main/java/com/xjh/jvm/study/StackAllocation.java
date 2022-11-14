package com.xjh.jvm.study;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Xu
 * @Description :
 *  栈上分配，未发生逃逸的对象可在栈上直接分配内存，入栈使用时分配、出栈时销毁
 *  逃逸分析，对象被定义后只在方法内使用（不为返回值、不被当做参数等）这种就是未发生逃逸，可在当前线程的栈上分配内存
 *
 *  jdk7开始默认开启逃逸分析,设置开启逃逸分析：-XX:+DoEscapeAnalysis （关闭就是-DoEscapeAnalysis）
 *  启动参数设置开启逃逸分析：-Xms1000m -Xmx1000m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails
 *  启动参数设置关闭逃逸分析：-Xms1000m -Xmx1000m -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 *  执行速度方面：
 *      上面对比可以发现未发生逃逸分析创建的实例少很多，执行速度更快
 *
 *  启动参数设置开启逃逸分析：-Xms100m -Xmx100m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails
 *  启动参数设置关闭逃逸分析：-Xms100m -Xmx100m -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 *  gc执行方面：
 *      堆内存减少，再次执行对比可以发现执行了多次ygc，如果堆内存再减少就会发生fgc
 *
 *  开启逃逸分析的优势：
 *      1.对于未发生逃逸的代码可直接在当前线程的栈分配内存，执行速度快
 *      2.不占用堆空间，减少gc次数，相当于变相提升执行速度
 *  开启逃逸分析的缺点：
 *      1.编译阶段需要进行额外的逃逸分析检查，可能会编译时长
 *
 * @date 2022年11月14日 16:23
 */
public class StackAllocation {

  public static void main(String[] args) throws InterruptedException {

      long millis1 = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++){
          allocate();
      }
      long millis2 = System.currentTimeMillis();

      System.out.println("耗时：" + (millis2 - millis1) +"毫秒");

      Thread.sleep(100000000);
  }

  static void allocate(){
      EscapeAnalysis escapeAnalysis = new EscapeAnalysis();
  }

}


class EscapeAnalysis{

}