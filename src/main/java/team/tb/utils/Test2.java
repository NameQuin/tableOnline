package team.tb.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Base{
    int i;
    Base(){
        add(1);
        System.out.println("base = "+i);
    }
    void add(int v){
        i+=v;
        System.out.println("base add = "+i);
    }
    void print(){
        System.out.println(i);
    }
}
class MyBase extends Base{
    MyBase(){
        add(2);
    }
    void add(int v){
        i+=v*2;
        System.out.println("MyBase add = "+i);
    }
}

public class Test2 {

    static void go(Base b){
        b.add(8);
    }

    public static void main(String[] args) {
//        go(new MyBase());

//        Stream<Student> studentStream = Stream.of(
//                new Student("赵丽颖", 52, 95),
//                new Student("杨颖", 56, 88),
//                new Student("迪丽热巴", 56, 55),
//                new Student("柳岩", 52, 33)
//        );
//
//        //多级分组
//        //1.先根据年龄分组,然后再根据成绩分组
//        //分析:第一个Collectors.groupingBy() 使用的是(年龄+成绩)两个维度分组,所以使用两个参数 groupingBy()方法
//        //    第二个Collectors.groupingBy() 就是用成绩分组,使用一个参数 groupingBy() 方法
//        Map<Integer, Map<Integer, Map<String, List<Student>>>> map = studentStream.collect(Collectors.groupingBy(str -> str.getAge(), Collectors.groupingBy(str -> str.getScore(), Collectors.groupingBy((student) -> {
//            if (student.getScore() >= 60) {
//                return "及格";
//            } else {
//                return "不及格";
//            }
//        }))));
//
//        map.forEach((key,value)->{
//            System.out.println("年龄:" + key);
//            value.forEach((k2,v2)->{
//                System.out.println("\t分数：" + k2);
//                v2.forEach((k3, v3) -> {
//                    System.out.println("\t\t及格状况: "+k3);
//                    System.out.println("\t\t\t"+v3);
//                });
//            });
//        });

    }
}
