package team.tb.common;

import team.tb.log.Logger;
import team.tb.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 定时任务创建于取消类
 */
public class TimingTask {
    // 日志类
    private static final Logger LOGGER = Logger.getLogger(TimingTask.class);
    // 已经产生的任务集合
    private static final ConcurrentHashMap<String, ScheduledFuture> TASKMAP = new ConcurrentHashMap<>();
    // 存放任务的线程池
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(5);

    public static ConcurrentHashMap getTsakMap(){
        return TASKMAP;
    }

    /**
     * 创建一个周期执行的定时任务
     *
     * @param taskName     任务名称
     * @param initialDelay 延时开始
     * @param period       执行周期
     * @param command      任务线程
     * @param mandatory    (存在)强制创建
     */
    public static boolean createTimingTask(String taskName, long initialDelay,
                                           long period, Runnable command, boolean mandatory) {
        if (findTimingTask(taskName)) {
            System.out.println(taskName + "循环定时任务存在");
            LOGGER.info("---->循环定时任务{"+taskName+"} 已经存在");
            if (mandatory) {
                System.out.println("强制创建");
                LOGGER.info("---->循环定时任务{"+taskName+"} 开始强制创建");
                cancelTimingTask(taskName);
                ScheduledFuture future = SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.SECONDS);
                TASKMAP.put(taskName, future);
                LOGGER.info("---->循环定时任务{"+taskName+"} 创建完成");
                return  true;
            } else {
                return false;
            }
        }
        ScheduledFuture future = SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.SECONDS);
        TASKMAP.put(taskName, future);
        LOGGER.info("---->循环定时任务{"+taskName+"} 创建完成");
        return true;
    }

    /**
     * 创建一个一次性的延迟任务
     *
     * @param initialDelay 延时开始
     * @param command      任务线程
     */
    public static void createTask(String name, long initialDelay, Runnable command) {
        LOGGER.info("---->定时任务{"+name+"} 开始创建");
        try {
            ScheduledFuture future = SCHEDULED_EXECUTOR_SERVICE.schedule(command, initialDelay, TimeUnit.SECONDS);
            TASKMAP.put(name, future);
            LOGGER.info("---->定时任务{"+name+"} 创建完成");
        } catch (Exception e) {
            System.out.println("任务执行失败");
            LOGGER.info("---->定时任务{" + name +"} 创建任务出现错误", e);
        }
    }

    /**
     * 取消定时任务
     * @param taskName
     */
    public static int cancelTimingTask(String taskName) {
        LOGGER.info("---->定时任务{"+taskName+"} 取消开始");
        try{
            if(findTimingTask(taskName)){
                // 取消任务
                Boolean ret = TASKMAP.get(taskName).cancel(true);
                // 从任务集合中删除
                TASKMAP.remove(taskName);
                // 返回执行结果
                LOGGER.info("---->定时任务{"+taskName+"} 取消结束："+ret);
                return ret == true ? 1 : 0;
            }
        }catch (Exception e){
            LOGGER.info("---->定时任务{"+taskName+"} 取消出现异常: ", e);
            return -1;
        }
        LOGGER.info("---->定时任务{"+taskName+"}不存在任务列表中");
        return 0;
    }

    /**
     * 查找是否存在定时任务
     * @param taskName
     * @return
     */
    private static boolean findTimingTask(String taskName) {
        return TASKMAP.containsKey(taskName);
    }

    public void clearDoneJob(){
        System.out.println("清理任务开始创建");
        createTimingTask("clearJob", 60, 60, new Runnable() {
            @Override
            public void run() {
                LOGGER.info("---->清理定时任务");
                for(String key : TASKMAP.keySet()){
                    if(!"clearJob".equals(key) && TASKMAP.get(key).isDone()){
                        TASKMAP.remove(key);
                        System.out.println("clearJob清理掉了名为"+key+"的已完成任务");
                        LOGGER.info("---->定时任务{"+key+"} 已被清理");
                    }else{
                        System.out.println("任务->"+key+" 尚未完成");
                        LOGGER.info("---->定时任务{"+key+"} 尚未完成，不做清理");
                    }
                }

            }
        }, false);
        System.out.println("清理任务创建完成");
        LOGGER.info("---->清理任务创建完成");
    }
}
