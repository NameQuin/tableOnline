package team.tb.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 定时任务的任务类
 */
public class Task implements Runnable {
    // 任务名字
    private String taskName;
    // 需要执行的函数，对象，参数
    private Method method;
    private Object obj;
    private Object[] params;

    public Task(){}

    public Task(String taskName, Object obj, Method method, Object... params){
        this.taskName = taskName;
        this.obj = obj;
        this.method = method;
        this.params = params;
    }
    @Override
    public void run() {
        try {
            method.setAccessible(true);
            method.invoke(obj, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
