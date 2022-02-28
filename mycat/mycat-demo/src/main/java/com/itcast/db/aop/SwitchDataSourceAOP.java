package com.itcast.db.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.itcast.db.config.DataSourceContextHolder;

// 使用AOP动态切换不同的数据源
@Aspect
@Component
@Lazy(false)
@Order(0) // Order设定AOP执行顺序 使之在数据库事务上先执行
public class SwitchDataSourceAOP {

	// 这里切到你的方法目录
	@Before("execution(* com.itcast.service.*.*(..))")
	public void process(JoinPoint joinPoint) {
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		if (methodName.startsWith("get") || methodName.startsWith("count") || methodName.startsWith("find")
				|| methodName.startsWith("list") || methodName.startsWith("select") || methodName.startsWith("check")) {
			DataSourceContextHolder.setDbType("selectDataSource");
		} else {
			// 切换dataSource
			DataSourceContextHolder.setDbType("updateDataSource");
		}
	}

}