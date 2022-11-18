package com.mck.global.mail;

import com.mck.domain.useremail.UserEmail;

public interface EmailService {

    void  sendEmail(EmailMessage emailMessage);

    // Email 인증 확인
    boolean checkCertifyEmail(UserEmail userEmail);
}