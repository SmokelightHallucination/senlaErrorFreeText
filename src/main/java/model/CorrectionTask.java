package model;

import com.senla.test.senlaerrorfreetext.enums.Language;
import com.senla.test.senlaerrorfreetext.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Data
@Table(name = "correction_tasks")
public class CorrectionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",  updatable = false, nullable = false)
    private UUID id;

    @Column(name = "original_text", nullable = false, columnDefinition = "TEXT")
    private String originalText;

    @Column(name = "corrected_text", columnDefinition = "TEXT")
    private String correctedText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CorrectionTask() {
    }
}
