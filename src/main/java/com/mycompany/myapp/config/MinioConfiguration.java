package com.mycompany.myapp.config;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;

import static com.mycompany.myapp.config.Constants.COMPANY_BUCKET;

@Configuration
public class MinioConfiguration {
    @Value("${spring.minio.accessKey}")
    String accessKey;
    @Value("${spring.minio.secretKey}")
    String accessSecret;
    @Value("${spring.minio.url}")
    String minioUrl;
    //not working yet
    @Value("classpath:minio/policy-config.json")
    Resource config;

    @Bean
    public MinioClient generateMinioClient() throws MinioException {
        try {
            MinioClient minio = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, accessSecret)
                .build();

            if(!minio.bucketExists(BucketExistsArgs.builder().bucket(COMPANY_BUCKET).build())){
                minio.makeBucket(MakeBucketArgs.builder().bucket(COMPANY_BUCKET).build());
            }
            String configs = "{\n" +
                "  \"Version\": \"2012-10-17\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": [\n" +
                "        \"s3:GetObject\"\n" +
                "      ],\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Principal\": {\"AWS\": [\"*\"]},\n" +
                "      \"Resource\": [\n" +
                "        \"arn:aws:s3:::company/*\"\n" +
                "      ],\n" +
                "      \"Sid\": \"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
            minio.setBucketPolicy(SetBucketPolicyArgs.builder()
                .config(configs)
                .bucket(COMPANY_BUCKET)
                .build());
            return minio;
        } catch (Exception e) {
            throw new MinioException(e.getMessage());
        }
    }

}