package ming.store.utils;

import ming.store.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Ming on 2017/8/24.
 */
public class MailUtils extends Thread  {
	private User user;
	private String link;
	public MailUtils(User user) {
		this.user = user;
		this.link = link;
	}
	@Override
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.host","smtp.qq.com");
			props.setProperty("mail.smtp.port","465");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.auth","true");

			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("969130721@qq.com","pkqowyqqhnaxbgae");
				}
			});
			session.setDebug(true);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("969130721@qq.com"));
			message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
			message.setSubject("小明商城注册激活邮件");
			String html = "<h2>亲爱的"+user.getName()+"用户你好：</h2><br/>";
			html += "&nbsp&nbsp恭喜你的账号："+user.getUsername()+" 注册成功！请48小时内，点击此链接激活" +
					"<a href = 'http://localhost:8080/MingStore/user?action=active&code="+user.getCode()+"'>"
					+user.getCode()+"</a>";
			message.setContent(html,"text/html;charset=utf-8");
			Transport.send(message);
			System.out.println();
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
