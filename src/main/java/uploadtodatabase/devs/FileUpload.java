package uploadtodatabase.devs;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name ="File_Upload")
@Data
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Client_ID")
    private Integer userId;

    @Column(name = "File_Name")
    private String fileName;

    @Column(name="Upload_Date")
    private Date uploadDate;

    @Column(name="File_Type")
    private String fileType;

    @Column(name="File_Size")
    private Long fileSize;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
