package uploadtodatabase.devs.controller;




import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uploadtodatabase.devs.exception.ResourceNotFoundException;
import uploadtodatabase.devs.FileUpload;
import uploadtodatabase.devs.FileUploadRepository;
import uploadtodatabase.devs.service.AzureBlobService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 86000)
@RestController
public class AzureBlobController {

    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file) throws IOException{

        String fileName = azureBlobService.upload(file);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(fileName);
        fileUpload.setFileType(file.getContentType());
        fileUpload.setFileSize(file.getSize());
        fileUpload.setUploadDate(new Date());
        fileUploadRepository.save(fileUpload);
        return ResponseEntity.ok(fileName);
    }
    @GetMapping("/files")
    public List<FileUpload> getAllFiles(){
        return fileUploadRepository.findAll();
    }

    @DeleteMapping("/files/{id}")
    public Map<String, Boolean> deleteFileUpload(@PathVariable(value = "id") Integer userId) throws ResourceNotFoundException {
        FileUpload fileUpload = fileUploadRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found for this id :: "+ userId));

                fileUploadRepository.delete(fileUpload);
                Map<String, Boolean> response = new HashMap<>();
                response.put("delete", Boolean.TRUE);
                return response;

    }
}


