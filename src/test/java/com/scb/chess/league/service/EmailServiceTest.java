package com.scb.chess.league.service;

import com.scb.chess.league.model.Participant;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendCongratulationEmail() throws MessagingException {
        // Arrange
        Participant champion = new Participant();
        champion.setName("John Doe");
        champion.setEmail("john.doe@example.com");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        when(templateEngine.process(eq("congratulation_email"), any(Context.class)))
                .thenReturn("<html><body>Congratulations, John Doe!</body></html>");

        // Act
        emailService.sendCongratulationEmail(champion);

        // Assert
        verify(mailSender).send(mimeMessage);
        verify(templateEngine).process(eq("congratulation_email"), any(Context.class));
    }
}
