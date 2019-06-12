package example;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试定时任务(演示Demo，可删除) 
 *
 * testTask为spring bean的名称
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask")
public class TestTask {
	
	public static boolean redisflag;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	//定时任务只能接受一个参数；如果有多个参数，使用json数据即可
	public void test(String params){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "我是带参数的test方法，正在被执行，参数为：" + params);
	}

	public void test2(){
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★");
	}
}
