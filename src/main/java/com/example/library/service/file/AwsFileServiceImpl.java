package com.example.library.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.example.library.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class AwsFileServiceImpl implements AwsFileService {

    private final AmazonS3 localstackClient;

    private final String bucketName;

    private final BookService bookService;

    public AwsFileServiceImpl(AmazonS3 localstackClient, @Value("${aws.s3-bucket}") String bucketName, @Autowired BookService bookService) {
        this.localstackClient = localstackClient;
        this.bucketName = bucketName;
        this.bookService = bookService;
    }

    @Override
    public void upload(Long id, InputStream inputStream) {
        bookService.getBookById(id);
        String fileName = id + ".jpg";
        localstackClient.putObject(bucketName, fileName, inputStream, null);

    }

    @Override
    public InputStreamResource download(Long id) {
        String fileName = id + ".jpg";
        return localstackClient.doesObjectExist(bucketName, fileName)
                ? new InputStreamResource(localstackClient.getObject(bucketName, fileName).getObjectContent())
                : null;
    }
}