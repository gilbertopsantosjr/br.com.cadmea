package br.com.cadmea.spring.util;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingApplication {

  private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "cadmea.package.entities";

  @Autowired
  private Environment env;

  /**
   * logging the application
   *
   * @return CommonsRequestLoggingFilter
   */
  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
    crlf.setIncludeClientInfo(true);
    crlf.setIncludeQueryString(true);
    crlf.setIncludePayload(true);
    return crlf;
  }

  /**
   * log the different method invocation, including method arguments and/or
   * return values or errors
   *
   * @return CustomizableTraceInterceptor.
   */
  @Bean
  public CustomizableTraceInterceptor customizableTraceInterceptor() {
    CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
    cti.setEnterMessage("Entering method '" + cti.PLACEHOLDER_METHOD_NAME + "("
        + cti.PLACEHOLDER_ARGUMENTS + ")' of class ["
        + cti.PLACEHOLDER_TARGET_CLASS_NAME + "]");
    cti.setExitMessage("Exiting method '" + cti.PLACEHOLDER_METHOD_NAME
        + "' of class [" + cti.PLACEHOLDER_TARGET_CLASS_NAME + "] took "
        + cti.PLACEHOLDER_INVOCATION_TIME + "ms.");
    return cti;
  }

  /**
   * enabling AOP.
   *
   * @return Advisor.
   */
  @Bean
  public Advisor traceAdvisor() {
    String packages = env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN)
        .replace("model", "");
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(public * " + packages + ".*.*(..))");
    return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
  }

}
