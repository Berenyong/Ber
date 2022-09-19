package bssm.major.club.ber.global.config.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;

    public FileResponseDto saveFile(MultipartFile file) throws IOException {
            String imgPath = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            amazonS3.putObject(new PutObjectRequest(bucket, imgPath, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return FileResponseDto.builder()
                    .imgPath(imgPath)
                    .imgUrl(String.valueOf(amazonS3Client.getUrl(bucket, imgPath)))
                    .build();
    }

    public void deleteFile(String imgPath) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imgPath));
    }
}
