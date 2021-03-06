/*
 * File Name: DayEndJob.java

 * Description:
 * Author: Pi Chen
 * Create Date: 2016年9月8日
 *
 */
package cn.edu.hdu.webbf.core.quartzjobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.edu.hdu.webbf.common.constant.BFConstant;
import cn.edu.hdu.webbf.common.log.Logger;
import cn.edu.hdu.webbf.service.user.IUserService;
import cn.edu.hdu.webbf.service.user.impl.UserServiceImpl;
import cn.edu.hdu.webbf.util.BeanUtil;
import cn.edu.hdu.webbf.util.ProjectConfigUtil;

/**
 *
 * 每天 执行一次
 *
 * @author    Pi Chen
 * @version   webbf V1.0.0, 2016年9月8日
 * @see
 * @since     webbf V1.0.0
 */

public class AddUserJob extends QuartzJobBean
{

    private static Logger logger = Logger.getLogger(AddUserJob.class);

    /**
     *
     * 每天具体Job
     *
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        try {
            IUserService userService = BeanUtil.getBean("userService", UserServiceImpl.class);
            Properties properties =ProjectConfigUtil.getConfig();
            logger.info("start calling saveUser.");
            if(BFConstant.TRUE.equals(properties.get("saveUserJob"))){
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("name", UUID.randomUUID().toString());
                params.put("address", new Random().nextInt(10000) + "");
                userService.saveUser(params);
            }

            logger.info("end up calling saveUser.");
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }

    }

}
