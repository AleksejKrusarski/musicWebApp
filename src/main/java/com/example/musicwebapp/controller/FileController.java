package com.example.musicwebapp.controller;


import com.example.musicwebapp.entity.FileDB;
import com.example.musicwebapp.entity.User;
import com.example.musicwebapp.message.ResponseFile;
import com.example.musicwebapp.message.ResponseMessage;
import com.example.musicwebapp.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class FileController {

    @Autowired
    private FileStorageService storageService;



    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam String category){

        String message = category;
        try {
            storageService.store(file, category);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e){
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files = storageService.getAllApprovedFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @PostMapping("/files/fileApprove/{id}")
    public ResponseEntity<String> fileApprove(@PathVariable String id, HttpServletResponse response) throws IOException {
        var successful = storageService.updateApprovedFile(id);
        //String url = "/list-files-unapproved";
        if (successful) {
            response.sendRedirect("/list-files-unapproved");

        }
        else {
            return new ResponseEntity<>(
                    "File not found!",
                    HttpStatus.NOT_FOUND);
        }
        return null;
    }

    @PostMapping("/files/fileDelete/{id}")
    public ResponseEntity<String> fileDelete(@PathVariable String id, HttpServletResponse response) throws IOException {
        var successful = storageService.deleteFile(id);
        if(successful){
            response.sendRedirect("/list-files-unapproved");
        }
        else {
            return new ResponseEntity<>(
                    "File not found!",
                    HttpStatus.NOT_FOUND);
        }
        return null;
    }


    @GetMapping("/list-files")
    public String viewUsersList(Model model){
        List<FileDB> listFiles = storageService.getAllApprovedFiles().toList();
        model.addAttribute("listFiles", listFiles);
        return "files";
    }

    @GetMapping("/list-files-unapproved")
    public String viewFileList(Model model){
        List<FileDB> listFiles = storageService.getAllFilesForApprovment().toList();
        model.addAttribute("listFiles", listFiles);
        return "filesAdmin";
    }

    @GetMapping("/upload-files")
    public String uploadFiles(){
        return "uploadFiles";
    }



}
