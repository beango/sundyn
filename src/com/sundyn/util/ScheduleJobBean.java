package com.sundyn.util;

import com.google.gson.Gson;
import com.sundyn.entity.QrzSchedulejob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 */
public class ScheduleJobBean extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String jsonJob = context.getMergedJobDataMap().getString(QrzSchedulejob.JOB_PARAM_KEY);
        QrzSchedulejob scheduleJob = new Gson().fromJson(jsonJob, QrzSchedulejob.class);

		//获取scheduleJobLogService
        //ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        /*ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCtime(new Date());*/

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
        	logger.info("任务准备执行，任务ID：" + scheduleJob.getJobid());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanname(),
            		scheduleJob.getMethodname(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

			future.get();

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			//log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			//log.setStatus(0);

			logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobid() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
            e.printStackTrace();
            System.out.println("任务执行失败，任务ID：" + scheduleJob.getJobid());
			logger.error("任务执行失败，任务ID：" + scheduleJob.getJobid(), e);

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			//log.setTimes((int)times);

			//任务状态    0：成功    1：失败
			//log.setStatus(1);
			//log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			//scheduleJobLogService.save(log);
		}
    }
}
