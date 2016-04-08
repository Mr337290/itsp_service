package com.itsp.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
public class EnhancedBeanProxyCreator extends BeanNameAutoProxyCreator
{
  private List<String> beanNames;
  private List<String> beanClassNames;
  private List<Class<?>> excludeClasses;

  protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource targetSource)
  {
    if (this.excludeClasses != null) {
      for (Class excludeClz : this.excludeClasses) {
        if (excludeClz.isAssignableFrom(beanClass)) {
          return DO_NOT_PROXY;
        }
      }
    }
    boolean matchType = (this.beanClassNames != null) && (this.beanClassNames.size() > 0);
    int i = ((this.beanNames != null) && (this.beanNames.size() > 0)) ? 1 : 0;
    String beanClassName;
    if (matchType)
    {
      boolean contains = false;
      beanClassName = beanClass.getName();
      for (String mappedClassName : this.beanClassNames) {
        if (FactoryBean.class.isAssignableFrom(beanClass)) {
          if (!(mappedClassName.startsWith("&"))) {
            continue;
          }
          mappedClassName = mappedClassName.substring("&".length());
        }
        if (isMatch(beanClassName, mappedClassName)) {
          contains = true;
          break;
        }
      }
      if (!(contains))
        return DO_NOT_PROXY;
      if (i == 0) {
        this.logger.debug("Add proxy for " + beanName);
        return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
      }
    }
    if (i != 0) {
      for (String mappedName : this.beanNames) {
        if (FactoryBean.class.isAssignableFrom(beanClass)) {
          if (!(mappedName.startsWith("&"))) {
            continue;
          }
          mappedName = mappedName.substring("&".length());
        }
        if (isMatch(beanName, mappedName)) {
          this.logger.debug("Add proxy for " + beanName);
          return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
        }
        BeanFactory beanFactory = getBeanFactory();
        if (beanFactory != null) {
          String[] aliases = beanFactory.getAliases(beanName);
          for (String alias : aliases) {
            if (isMatch(alias, mappedName)) {
              this.logger.debug("Add proxy for " + beanName);
              return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
            }
          }
        }
      }
    }

    return DO_NOT_PROXY;
  }

  public void setBeanNames(String[] beanNames) {
    this.beanNames = new ArrayList(beanNames.length);
    for (String mappedName : beanNames)
      this.beanNames.add(StringUtils.trimWhitespace(mappedName));
  }

  public void setBeanClassNames(String[] beanClassNames)
  {
    this.beanClassNames = new ArrayList(beanClassNames.length);
    for (String mappedClassName : beanClassNames)
      this.beanClassNames.add(StringUtils.trimWhitespace(mappedClassName));
  }

  public void setExcludeClasses(String[] excludeClasses)
  {
    this.excludeClasses = new ArrayList(excludeClasses.length);
    for (String excludeClass : excludeClasses)
      try {
        this.excludeClasses.add(Class.forName(excludeClass));
      } catch (ClassNotFoundException e) {
        throw new IllegalArgumentException(e);
      }
  }
}
