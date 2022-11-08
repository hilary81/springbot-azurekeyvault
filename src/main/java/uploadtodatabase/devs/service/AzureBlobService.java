package uploadtodatabase.devs.service;

import com.azure.storage.blob.BlobClient;


import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AzureBlobService {

    @Autowired
    BlobContainerClient blobContainerClient;
    public String upload(MultipartFile multipartFile) throws IOException{
         BlobClient blob = blobContainerClient.getBlobClient(multipartFile.getOriginalFilename());
        blob.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);

        return multipartFile.getOriginalFilename();

    }
}
