package springboot06mybatis.utils;

import springboot06mybatis.common.Const;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class SendEmail {
    public  String myEmailAccount = Const.myEmailAccount;
    public  String myEmailPassword = Const.myEmailPassword;
    public  String myEmailSMTPHost = Const.myEmailSMTPHost;
    public void email(String receiveMailAccount,int code) throws Exception {
      
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        
      
        final String smtpPort = Const.smtpPort;
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        

      
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,code);

        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);

        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

	public  static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,int code) throws Exception {


		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(sendMail, "智慧帮", "UTF-8"));

		message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(sendMail, "智慧帮官方客服", "UTF-8"));

		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "智慧帮", "UTF-8"));

		message.setSubject("请验证你的邮箱", "UTF-8");

		message.setContent("尊敬的用户，您正在使用智慧帮提供的用户注册服务！\n\n"+"您的验证码为：" +code+"\n\n。请不要透露给任何人，如果这不是您本人操作，请忽略！", "text/html;charset=UTF-8");
		
		message.setSentDate(new Date());

		message.saveChanges();

		return message;
	}
}
