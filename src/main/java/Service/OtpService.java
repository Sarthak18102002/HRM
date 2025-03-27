package HRM.FinalProject.Service;


import HRM.FinalProject.Repository.OtpRepository;
import HRM.FinalProject.UserEntity.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final int OTP_EXPIRATION_MINUTES = 2;

    public String generateOtp() {
        return String.valueOf(new Random().nextInt(990000) + 20000);
    }

    public void sendOtp(String email) {
        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES);

        Otp otpEntry = new Otp();
        otpEntry.setEmail(email);
        otpEntry.setOtp1(otp);
        otpEntry.setExpiryTime(expiryTime);
        otpRepository.save(otpEntry);

        sendEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        List<Otp> otpEntries = otpRepository.findByEmail(email);

        for (Otp otpEntry : otpEntries) {
            if (otpEntry.getOtp1().equals(otp) && otpEntry.getExpiryTime().isAfter(LocalDateTime.now())) {
                otpRepository.delete(otpEntry);
                return true;
            }
        }
        return false;
    }

    private void sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp +"\n"+"Otp is Valid for "+OTP_EXPIRATION_MINUTES+"Min");
        mailSender.send(message);
    }
}
