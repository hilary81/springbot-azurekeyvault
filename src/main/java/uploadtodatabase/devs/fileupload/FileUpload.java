package uploadtodatabase.devs.fileupload;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name ="file_upload4", uniqueConstraints = @UniqueConstraint(columnNames = {"file_name", "file_path"}))
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name="upload_date", nullable = false)
    private Date uploadDate;

    @Column(name="file_type", nullable = false)
    private String fileType;
    @Column(name="job_id", nullable = false)
    private Long jobId;

    @Column(name="file_path", nullable = false, unique = true)
    private String filePath;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
