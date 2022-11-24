package uploadtodatabase.devs.fileupload;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="file_upload2")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name="upload_date")
    private Date uploadDate;

    @Column(name="File_Type")
    private String fileType;
    @Column(name="job_id")
    private Long jobId;


    @Column(name="File_Path")
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
