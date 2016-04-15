package com.vivian.common.directive.auto;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModelException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * author:zhangjian
 * date:2016-01-10
 * describe:
 **/
@Component
public class FreeMarkerExtendHandler implements ApplicationContextAware {

    private final Logger logger = Logger.getLogger(FreeMarkerExtendHandler.class);

    private Map<String, TemplateDirectiveModel> directiveMap;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
        logger.info("Freemarker directives and methods Handler started");
        Map<String, Object> freemarkerVariables = new HashMap<String, Object>();

        directiveMap = new ClassPathXmlApplicationContext("classpath*:*-bean.xml").getBeansOfType(TemplateDirectiveModel.class);

        StringBuffer directives = new StringBuffer();

        for (Map.Entry<String, TemplateDirectiveModel> entry : directiveMap.entrySet()) {
            freemarkerVariables.put(entry.getKey(), entry.getValue());
            if (0 != directives.length())
                directives.append(",");
            directives.append(entry.getKey());
        }

        try {
            freeMarkerConfigurer.getConfiguration().setAllSharedVariables(
                    new SimpleHash(freemarkerVariables, freeMarkerConfigurer.getConfiguration().getObjectWrapper()));
            logger.info((directiveMap.size()) + " directives created:[" + directives.toString() + "];");
        } catch (TemplateModelException e) {
            logger.info(e);
        }
    }

}
