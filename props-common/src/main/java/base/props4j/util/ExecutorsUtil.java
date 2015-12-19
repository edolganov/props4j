package base.props4j.util;

import base.props4j.service.BaseThreadFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsUtil {
	
	public static ExecutorService newSingleThreadExecutor(String namePrefix){
		return newSingleThreadExecutor(namePrefix, false, null);
	}
	
	public static ExecutorService newSingleThreadExecutor(String namePrefix, UncaughtExceptionHandler handler){
		return newSingleThreadExecutor(namePrefix, false, handler);
	}
	
	public static ExecutorService newSingleThreadExecutor(String namePrefix, boolean daemon, UncaughtExceptionHandler handler){
		return Executors.newSingleThreadExecutor(new BaseThreadFactory.Builder()
		    .namingPattern(namePrefix+"-%d")
		    .daemon(daemon)
		    .priority(Thread.NORM_PRIORITY)
		    .uncaughtExceptionHandler(handler)
		    .build());
	}
	
	
	public static ScheduledExecutorService newScheduledThreadPool(String namePrefix,int corePoolSize){
		return newScheduledThreadPool(namePrefix, corePoolSize, false, null);
	}
	
	public static ScheduledExecutorService newScheduledThreadPool(String namePrefix,int corePoolSize, UncaughtExceptionHandler handler){
		return newScheduledThreadPool(namePrefix, corePoolSize, false, handler);
	}
	
	public static ScheduledExecutorService newScheduledThreadPool(String namePrefix,int corePoolSize, boolean daemon, UncaughtExceptionHandler handler){
		return Executors.newScheduledThreadPool(corePoolSize, new BaseThreadFactory.Builder()
		    .namingPattern(namePrefix+"-%d")
		    .daemon(daemon)
		    .priority(Thread.NORM_PRIORITY)
		    .uncaughtExceptionHandler(handler)
		    .build());
	}
	
	
	
	public static ExecutorService newFixedThreadPool(String namePrefix, int poolSize) {
		return newFixedThreadPool(namePrefix, poolSize, false, null);
	}
	
	public static ExecutorService newFixedThreadPool(String namePrefix, int poolSize, UncaughtExceptionHandler handler) {
		return newFixedThreadPool(namePrefix, poolSize, false, handler);
	}
	
    public static ExecutorService newFixedThreadPool(String namePrefix, int poolSize, boolean daemon, UncaughtExceptionHandler handler) {
        return new ThreadPoolExecutor(poolSize, poolSize,
        		0L, TimeUnit.MILLISECONDS,
        		new LinkedBlockingQueue<Runnable>(),
  	            new BaseThreadFactory.Builder()
  		    	    .namingPattern(namePrefix+"-%d")
  		    	    .daemon(daemon)
  		    	    .priority(Thread.NORM_PRIORITY)
  		    	    .uncaughtExceptionHandler(handler)
  		    	    .build());
    }

}
