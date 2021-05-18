package team.tb.utils;

import team.tb.common.Task;
import team.tb.common.TimingTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws URISyntaxException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 全都是绝对路径
        // 提供空串参数时，除了CLass.getResource()之外，参数值为空时其余都是file:/C:/Program%20Files/Java/jdk1.8.0_211/jre/lib/ext/X86/目录
        // CLass.getResource()是当前类所在包的字节码文件位置file:/E:/JetBrainJava/tableOnline/target/classes/team/tb/utils/

        // 提供参数为/时，CLass.getResource()获取到file:/C:/Program%20Files/Java/jdk1.8.0_211/jre/lib/ext/X86/目录，其余全是null，

        // 提供参数为db.properties时，Class.getResource()为null，其余全为当前的classPath路径下该文件的完整路径
        // 提供参数为/db.properties时，Class.getResource()为当前的classPath路径下该文件的完整路径file:/E:/JetBrainJava/tableOnline/target/classes/db.properties，其余全为null
//        System.out.println(Test.class.getClassLoader().getResource("/db.properties"));
//        System.out.println(Test.class.getResource("/db.properties"));
//        System.out.println(ClassLoader.getSystemResource("/db.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/db.properties"));

//        Test ts = new Test();
//        Method test = null;
//        try {
//            test = Test.class.getDeclaredMethod("test", Array.class);
//            if(test != null){
//                test.invoke(ts);
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        Test test = new Test();
        Class<?> clazz = test.getClass();
        Method method = clazz.getDeclaredMethod("test", Object[].class);
        Method method1 = clazz.getDeclaredMethod("test2", Object[].class);
        Object params = new Object[]{1, 2, 3, 4};
        method.invoke(test, params);
        System.out.println(method1.invoke(test, params));
        test.test(1, 2, 3);
    }

    public void test(Object... params){
        System.out.println("测试方法"+ Arrays.toString(params));
        String className = this.getClass().getSimpleName();
        System.out.println("类名："+className);
        className = (char)(className.charAt(0) - 'A' + 'a')+className.substring(1);
        System.out.println("修改后的类名："+className);
        System.out.println("参数-------");
        for (Object param : params) {
            System.out.println(param.toString());
        }
    }

    private Boolean test2(Object... params){
        System.out.println("私有的测试方法");
        return params.length > 0;
    }
}
