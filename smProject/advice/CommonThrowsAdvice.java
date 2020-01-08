package com.smProject.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

public class CommonThrowsAdvice implements ThrowsAdvice {

	private static final Logger logger = LoggerFactory.getLogger("exception");
	private static Exception prevEx;

	public void afterThrowing(Exception ex) throws Throwable {
		// if ("AuthException".equals(ex.getClass().getSimpleName())) {
		// return;
		// }
		ex.printStackTrace();

		logger.error("## CommonThrowsAdvice.afterThrowing Start");
		if (ex != prevEx) {
			sendErrorMessage(ex);
		}
		prevEx = ex;
		logger.error("## CommonThrowsAdvice.afterThrowing End");
	}

	public void sendErrorMessage(Exception ex) {
		ex.printStackTrace();
		logger.error("## CommonThrowsAdvice.sendErrorMessage Start");
		try {
			logger.error(ex.getMessage());
		} catch (Exception e) {
			logger.error("## CommonThrowsAdvice.sendErrorMessage Exception", e);
		}
		logger.error("## CommonThrowsAdvice.sendErrorMessage End");
	}
}
