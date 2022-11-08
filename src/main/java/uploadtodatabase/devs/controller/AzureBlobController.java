package uploadtodatabase.devs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uploadtodatabase.devs.FileUpload;
import uploadtodatabase.devs.FileUploadRepository;
import uploadtodatabase.devs.service.AzureBlobService;

import java.io.IOException;
import java.util.Date;

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
}


