package com.example.library.service.file;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public interface AwsFileService {
    void upload(Long id, InputStream inputStream);

    InputStreamResource download(Long id);
}