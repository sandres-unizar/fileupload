package com.example.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fileupload.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}