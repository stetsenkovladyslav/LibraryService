package com.example.library.configuration;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AwsConfig {

    private final String bucketName;

    public AwsConfig(@Value("${aws.s3-bucket}") String bucketName) {
        this.bucketName = bucketName;
    }

    @Bean
    @Profile("dev")

    public AmazonS3 localstackClient() {
        AmazonS3 localstackClient = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localstack:4566", "us-west-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accesskey", "secretkey")))
                .withPathStyleAccessEnabled(true)
                .build();
        if (!localstackClient.doesBucketExistV2(bucketName)) {
            localstackClient.createBucket(bucketName);
        }

        return localstackClient;
    }
}