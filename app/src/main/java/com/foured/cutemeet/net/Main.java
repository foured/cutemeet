import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.SecureRandom;

public class Main {

    public static void SendMessage(String Recipient, String Code) {

        // Учетные данные отправителя
        final String username = "cmverifn@gmail.com";
        final String password = "lukd fjpj jchd gzce";

        // Настройки для отправки электронной почты через SMTP сервер Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Получение сессии
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Создание объекта сообщения
            Message message = new MimeMessage(session);
            // Установка отправителя
            message.setFrom(new InternetAddress("cmverifn@gmail.com"));
            // Установка получателя
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Recipient));
            // Установка темы сообщения
            message.setSubject("verification code");
            // Установка текста сообщения
            message.setText(Code);

            // Отправка сообщения
            Transport.send(message);

            System.out.println("Сообщение успешно отправлено.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        SendMessage("tkoptubenko@gmail.com", "696969696969");
    }
}
