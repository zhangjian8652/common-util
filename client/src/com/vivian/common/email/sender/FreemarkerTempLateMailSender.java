package com.vivian.common.email.sender;

import com.vivian.common.email.entity.Email;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * zhangjian
 * 2015/09/10工具
 * Freemarker 邮件发送
 */
@Component("freemarkerMailSender")
public class FreemarkerTempLateMailSender extends MailSender{

	private Logger logger = Logger.getLogger(FreemarkerTempLateMailSender.class);

	private FreeMarkerConfigurer freeMarkerConfigurer;//注入类

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public boolean sendEmail(Email email,String templateName,Map<String, Object> variables){

			logger.debug("set email context");
			email.setContext(this.getMailText(variables,templateName));
			return super.sendEmail(email);
	}

	private String getMailText(Map<String, Object> variables,
			String templateName) {
		String htmlText = "";
		try {
			// 通过指定模板名获取FreeMarker模板实例
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					templateName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText;
	}


}
