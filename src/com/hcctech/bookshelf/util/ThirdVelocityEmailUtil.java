package com.hcctech.bookshelf.util;

import java.io.File;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * @author Administrator
 * 
 */
public class ThirdVelocityEmailUtil {
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private String fromMail;
	/**
	 * @param model
	 *            替换velocity模板的变量和值对 Map<String, Object>
	 * @param subject
	 *            邮件主题
	 * @param vmfile
	 *            模板文件
	 * @param mailTo
	 *            收信人 String 数组 {ss@x.com,xx@ss.com}
	 * @param files
	 *            附件new String[]{"F:/Sunset.jpg","F:/spring-hibernate.rar"}
	 */
	public void sendEmail(final Map<String, Object> model,
			final String subject, final String vmfile, final String[] mailTo,
			final String[] files) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				// 注意MimeMessagePreparator接口只有这一个回调函数
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
							true, "UTF-8");
					// 这是一个生成Mime邮件简单工具，如果不使用GBK这个，中文会出现乱码
					// 如果您使用的都是英文，那么可以使用MimeMessageHelper message = new
					// MimeMessageHelper(mimeMessage);
					message.setTo(mailTo);// 设置接收方的email地址
					message.setSubject(subject);// 设置邮件主题
					message.setFrom(fromMail);// 设置发送方地址
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, vmfile, "UTF-8", model);
					// 从模板中加载要发送的内容，vmfile就是模板文件的名字
					// 注意模板中有中文要加GBK，model中存放的是要替换模板中字段的值
					message.setText(text, true);
					// 将发送的内容赋值给MimeMessageHelper,后面的true表示内容解析成html
					// 如果您不想解析文本内容，可以使用false或者不添加这项
					FileSystemResource file;
					if (null != files) {
						for (String s : files)// 添加附件
						{
							file = new FileSystemResource(new File(s));// 读取附件
							message.addAttachment(s, file);// 向email中添加附件
						}

					}
				}
			};
			mailSender.send(preparator);// 发送邮件
		} catch (MailException e) {
			
		}
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
}
