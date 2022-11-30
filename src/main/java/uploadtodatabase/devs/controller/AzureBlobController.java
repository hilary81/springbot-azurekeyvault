package uploadtodatabase.devs.controller;

import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uploadtodatabase.devs.fileupload.FileUpload;
import uploadtodatabase.devs.fileupload.FileUploadRepository;
import uploadtodatabase.devs.exception.ResourceNotFoundException;
import uploadtodatabase.devs.jobID.Job;
import uploadtodatabase.devs.jobID.JobIdRepository;
import uploadtodatabase.devs.service.AzureBlobService;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 86000)
@RestController
public class AzureBlobController {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private JobIdRepository jobIdRepository;
    @Autowired
    private AzureBlobService azureBlobService;

    @Autowired
    BlobContainerClient blobContainerClient;

    public static MultimediaInfo getInfo(String videoUrl) throws MalformedURLException, EncoderException {
        URL url = new URL(videoUrl);

        try {
            MultimediaObject multimediaObject = new MultimediaObject(url);
            MultimediaInfo multimediaInfo = multimediaObject.getInfo();
            return multimediaInfo;

        } catch (InputFormatException e) {
            e.printStackTrace();
            return null;

        }
    }
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException, EncoderException {
            Job job = new Job();
            jobIdRepository.save(job);
            String fileName = azureBlobService.upload(file);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setFileName(fileName);
            fileUpload.setFileType(file.getContentType());
            fileUpload.setUploadDate(new Date());
            fileUpload.setJobId(job.getId());
            fileUpload.setFilePath(blobContainerClient.getBlobClient(fileName).getBlobUrl());
            fileUploadRepository.save(fileUpload);
            return ResponseEntity.ok(fileName);
    }

    @GetMapping("/files")
    public List<FileUpload> getAllFiles() {

        return fileUploadRepository.findAll();
    }

    @DeleteMapping("/files/{id}")
    public Map<String, Boolean> deleteFileUpload(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        FileUpload fileUpload = fileUploadRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found for this id :: " + userId));

        fileUploadRepository.delete(fileUpload);

        jobIdRepository.delete(jobIdRepository.getReferenceById(fileUpload.getJobId()));

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



