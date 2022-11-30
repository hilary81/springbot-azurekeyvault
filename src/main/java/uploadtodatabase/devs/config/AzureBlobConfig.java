package uploadtodatabase.devs.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobConfig {
    @Value("${spring.azure.storage.connection.string}")
    private String connectionString;

    @Value("user5")
    private String containerName;
    @Bean
    public BlobServiceClient blobServiceClient(){
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString)
                .buildClient();

        return blobServiceClient;
    }
    @Bean
    public BlobContainerClient blobContainerClient(){
        BlobContainerClient blobContainerClient = blobServiceClient().getBlobContainerClient(containerName);

        if (blobContainerClient.exists()){
            System.out.println("container exist");

        }else{
            blobServiceClient().createBlobContainer(containerName);
        }
        return blobContainerClient;
    }

}
