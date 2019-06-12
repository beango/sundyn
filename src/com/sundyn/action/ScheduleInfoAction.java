package com.sundyn.action;

import com.sundyn.entity.QrzSchedulejob;
import com.sundyn.service.IQrzSchedulejobService;
import com.sundyn.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.List;

public class ScheduleInfoAction {
    Scheduler scheduler;
    @Autowired
    SchedulerFactoryBean schedulerFactory;
    private void initScheduleJob() throws SchedulerException, ParseException {
        scheduler = schedulerFactory.getScheduler();
        List<QrzSchedulejob> scheduleJobList = scheduleJobService.selectList(null);
        System.out.println("JOBS.COUNT" + scheduleJobList.size());
        for(QrzSchedulejob scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobid());
            if(cronTrigger == null) {
                System.out.println("创建：" + cronTrigger);
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                System.out.println("修改：" + cronTrigger);
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    @Autowired
    public IQrzSchedulejobService scheduleJobService;

    public void destoryScheduleJob(){
        System.out.println("定时任务销毁");
        try {
            scheduler.shutdown(true);
            Thread.sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
