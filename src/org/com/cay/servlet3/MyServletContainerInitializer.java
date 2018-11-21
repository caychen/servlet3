package org.com.cay.servlet3;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;

import org.com.cay.servlet3.filter.UserFilter;
import org.com.cay.servlet3.listener.UserListener;
import org.com.cay.servlet3.service.HelloService;
import org.com.cay.servlet3.servlet.UserServlet;

//容器启动的时候，会将@HandlesTypes指定类的子类（实现类、子接口等）传递过来，赋值给onStartup方法的Set<Class<?>>参数
@HandlesTypes({HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

	/**
	 * 应用启动的时候，会运行onStartup方法：
	 * 	ServletContext arg1: 代表当前Web应用的ServletContext，一个Web应用对应一个ServletContext
	 * 	Set<Class<?>> arg0: 接收 @HandlerTypes 指定类的子类
	 * 
	 */
	@Override
	public void onStartup(Set<Class<?>> arg0, ServletContext arg1) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("传入类型为：");
		arg0.stream().forEach(clazz -> System.out.println(clazz.getName()));
		
		//注册Servlet
		ServletRegistration.Dynamic servlet = arg1.addServlet("userServlet", UserServlet.class);
		servlet.addMapping("/user");
		
		//注册Listener
		arg1.addListener(UserListener.class);
		
		//注册Filter
		FilterRegistration.Dynamic filter = arg1.addFilter("userFilter", UserFilter.class);
		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
	}

}
