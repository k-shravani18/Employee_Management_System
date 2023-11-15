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

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void sendReminderEmailMorning(Employee employee) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            String htmlTemplate = getHtmlTemplate(1);
            String emailContent = replacePlaceholders(htmlTemplate, employee);

            helper.setFrom("kamilpraseej742@gmail.com");
            helper.setTo(employee.getEmailId());
            helper.setSubject("Check In Reminder!");
            helper.setText(emailContent, true);

            javaMailSender.send(message);
            System.out.println("Email Send Successfully");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendReminderEmailEvening(Employee employee) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            String htmlTemplate = getHtmlTemplate(2);
            String emailContent = replacePlaceholders(htmlTemplate, employee);

            helper.setFrom("kamilpraseej742@gmail.com");
            helper.setTo(employee.getEmailId());
            helper.setSubject("Check Out Reminder!");
            helper.setText(emailContent, true);

            javaMailSender.send(message);
            System.out.println("Email Send Successfully");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    private String getHtmlTemplate(int number) throws IOException {
        Resource resource = null;
        switch (number){
            case 1:
                resource = new ClassPathResource("messages/morning-remainder.html");
                break;
            case 2:
                resource = new ClassPathResource("messages/evening-remainder.html");
                break;

        }

        byte[] fileBytes = resource.getInputStream().readAllBytes();
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    private String replacePlaceholders(String emailContent, Employee employee) {
        emailContent = emailContent.replace("{FULL_NAME}", employee.getFullName());
        return emailContent;
    }



}
