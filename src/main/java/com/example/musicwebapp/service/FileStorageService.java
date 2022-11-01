package com.example.musicwebapp.service;

import com.example.musicwebapp.entity.FileDB;
import com.example.musicwebapp.repository.FileDBRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file, String category) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), category);

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id){
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllApprovedFiles(){
        return fileDBRepository.findAll().stream().filter(fileDB -> fileDB.getIsApproved() == Boolean.TRUE);
    }

    public Stream<FileDB> getAllFilesForApprovment(){
        return fileDBRepository.findAll().stream().filter(fileDB -> fileDB.getIsApproved() == Boolean.FALSE);
    }

    public boolean deleteFile(String id){
        var file =  fileDBRepository.findById(id).get();
        if(file == null){
            return false;
        }
        else {
            fileDBRepository.deleteById(id);
            return true;
        }
    }

    public boolean updateApprovedFile(String id){
        var file =  fileDBRepository.findById(id).get();
        if(file == null){
            return false;
        }
        else {
            file.setIsApproved(Boolean.TRUE);
            fileDBRepository.save(file);
            return true;
        }
    }
}
