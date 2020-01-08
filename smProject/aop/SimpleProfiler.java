package com.smProject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

/**
 * Our profiler that logs process time, and remark if process had exception.
 */
public class SimpleProfiler {

	private Logger logger = LoggerFactory.getLogger(SimpleProfiler.class);

	/**
	 * Spring AOP 'around' reference method signature is bounded like this, the
	 * method name "profile" should be same as defined in spring.xml aop:around
	 * section.
	 **/
	public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(proceedingJoinPoint.toShortString());
		boolean isExceptionThrown = false;
		try {
			// execute the profiled method
			return proceedingJoinPoint.proceed();
		} catch (RuntimeException e) {
			isExceptionThrown = true;
			throw e;
		} finally {
			stopWatch.stop();
			TaskInfo taskInfo = stopWatch.getLastTaskInfo();
			// Log the method's profiling result
			String profileMessage = taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + " ms"
					+ (isExceptionThrown ? " (thrown Exception)" : "");

			logger.info(profileMessage);
		}
	}
}
