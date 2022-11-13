package uploadtodatabase.devs.controller;




import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import uploadtodatabase.devs.exception.ResourceNotFoundException;
import uploadtodatabase.devs.FileUpload;
import uploadtodatabase.devs.FileUploadRepository;
import uploadtodatabase.devs.service.AzureBlobService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 86000)
@RestController
public class AzureBlobController {

    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired
    private AzureBlobService azureBlobService;

    @Autowired
    BlobContainerClient blobContainerClient;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName = azureBlobService.upload(file);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(fileName);
        fileUpload.setFileType(file.getContentType());
        fileUpload.setFileSize(file.getSize());
        fileUpload.setUploadDate(new Date());
        fileUpload.setFilePath(blobContainerClient.getBlobClient(fileName).getBlobUrl());
        fileUploadRepository.save(fileUpload);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/files")
    public List<FileUpload> getAllFiles() {

        return fileUploadRepository.findAll();
    }

    @DeleteMapping("/files/{id}")
    public Map<String, Boolean> deleteFileUpload(@PathVariable(value = "id") Integer userId) throws ResourceNotFoundException {
        FileUpload fileUpload = fileUploadRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found for this id :: " + userId));

        fileUploadRepository.delete(fileUpload);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;

    }

    @GetMapping(path = "/download/{name}")
    public ResponseEntity<Resource> getFile(@PathVariable("name") String name) {

        ByteArrayResource resource = new ByteArrayResource(azureBlobService.getFile(name));

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);

    }
}



