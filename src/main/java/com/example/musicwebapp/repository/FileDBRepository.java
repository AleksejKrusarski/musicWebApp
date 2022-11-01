package com.example.musicwebapp.repository;

import com.example.musicwebapp.entity.FileDB;

import com.example.musicwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

//    @Query("SELECT f FROM FileDB f where f.isApproved = TRUE")
//    List<FileDB> getFile(String id);



}
