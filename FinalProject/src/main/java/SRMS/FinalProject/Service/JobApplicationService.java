package SRMS.FinalProject.Service;



import SRMS.FinalProject.Repository.JobApplicationRepository;
import SRMS.FinalProject.UserEntity.JobApplication;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class JobApplicationService {

    private static final String UPLOAD_DIR = "D:/SpringBoot/FinalProject/FinalProject-JobApplication-1/uploads/";

    private final JobApplicationRepository jobApplicationRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository, JavaMailSender mailSender) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.mailSender = mailSender;
    }

    public JobApplication submitApplication(Long userId, Long interviewerId, LocalDateTime interviewDate, MultipartFile file) {
        try {
            // Ensure upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate unique filename
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR).resolve(uniqueFileName).normalize();

            // Save file to system
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save job application details in DB
            JobApplication application = new JobApplication();
            application.setUserId(userId);
            application.setInterviewerId(interviewerId);
            application.setFilePath(filePath.toString());
            application.setInterviewDate(interviewDate);
            application.setAppliedAt(LocalDateTime.now());
            //application.setReviewed(false);
            application.setStatus("PENDING");

            // Save to database
            JobApplication savedApplication = jobApplicationRepository.save(application);

            // Debug Log: Check if data is correctly saved
            System.out.println("Saved JobApplication: " + savedApplication);

            // Send email notification with attachment
            sendEmailNotification(interviewerId, filePath.toString(), userId, interviewDate);

            return savedApplication; // Return the saved application
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // Send Email Notification with Resume Attachment
    private void sendEmailNotification(Long interviewerId, String filePath, Long userId, LocalDateTime interviewDate) {
        try {
            String interviewerEmail = getInterviewerEmail(interviewerId);
            String userEmail = getUserEmail(userId); // Fetch user email from DB

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(interviewerEmail);
            helper.setSubject("New Job Application Received");
            helper.setText(
                    "Dear Interviewer,\n\nA new job application has been submitted.\n\n" +
                            "🔹 **Applicant ID:** " + userId + "\n" +
                            "🔹 **Interview Date:** " + interviewDate + "\n" +
                            "🔹 **Resume Attached** 📎\n\n" +
                            "Please review the application.\n\nBest Regards,\nJob Portal Team"
            );

            // Attach Resume File
            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment("Resume.pdf", file);

            mailSender.send(message);
            System.out.println(" Email sent successfully to: " + interviewerEmail);

        } catch (MessagingException e) {
            System.err.println(" Failed to send email: " + e.getMessage());
        }
    }

    // Get All Job Applications
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    // Update Application Status (PENDING, REVIEWED, ACCEPTED, REJECTED)
    public void updateApplicationStatus(Long applicationId, String status) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);
        jobApplicationRepository.save(application);
    }

    // Fetch Interviewer's Email from Database
    public String getInterviewerEmail(Long interviewerId) {
        // TODO: Fetch from actual DB instead of returning a placeholder
        return "interviewer" + interviewerId + "@example.com";
    }

    // Fetch User's Email from Database
    private String getUserEmail(Long userId) {
        // TODO: Fetch from actual DB instead of returning a placeholder
        return "user" + userId + "@example.com";
    }
}