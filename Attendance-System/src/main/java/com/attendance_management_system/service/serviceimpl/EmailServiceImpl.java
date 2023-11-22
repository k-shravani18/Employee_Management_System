package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Employee;
import com.attendance_management_system.repository.EmployeeRepository;
import com.attendance_management_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, EmployeeRepository employeeRepository) {
        this.javaMailSender = javaMailSender;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Sends a reminder email in the morning to the specified employee.
     * @param employee The employee to whom the reminder email is sent.
     * @author Kamil Praseej
     */
    @Override
    public void sendReminderEmailMorning(Employee employee) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            String htmlTemplate = getHtmlTemplate(1);
            String emailContent = replacePlaceholders(htmlTemplate, employee);

            helper.setFrom("kamilpraseej742@gmail.com");
            helper.setTo(employee.getEmailId());
            helper.setSubject("Check-In Reminder!");
            helper.setText(emailContent, true);

            javaMailSender.send(message);
            System.out.println("Email Sent Successfully");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a reminder email in the evening to the specified employee.
     * @param employee The employee to whom the reminder email is sent.
     * @author Kamil Praseej
     */
    @Override
    public void sendReminderEmailEvening(Employee employee) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            String htmlTemplate = getHtmlTemplate(2);
            String emailContent = replacePlaceholders(htmlTemplate, employee);

            helper.setFrom("kamilpraseej742@gmail.com");
            helper.setTo(employee.getEmailId());
            helper.setSubject("Check-Out Reminder!");
            helper.setText(emailContent, true);

            javaMailSender.send(message);
            System.out.println("Email Sent Successfully");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the HTML template for the reminder email based on the specified number.
     * @param number The number indicating the type of reminder (1 for morning, 2 for evening).
     * @return The HTML template as a string.
     * @throws IOException If there is an issue reading the HTML template file.
     */
    private String getHtmlTemplate(int number) throws IOException {
        Resource resource = switch (number) {
            case 1 -> new ClassPathResource("messages/morning-remainder.html");
            case 2 -> new ClassPathResource("messages/evening-remainder.html");
            default -> null;
        };

        byte[] fileBytes = resource.getInputStream().readAllBytes();
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    /**
     * Replaces placeholders in the email content with actual employee details.
     * @param emailContent The email content with placeholders.
     * @param employee     The employee whose details are used to replace placeholders.
     * @return The email content with actual employee details.
     */
    private String replacePlaceholders(String emailContent, Employee employee) {
        emailContent = emailContent.replace(
                "{FULL_NAME}", employee.getFirstName() + " " + employee.getLastName());
        return emailContent;
    }
}
