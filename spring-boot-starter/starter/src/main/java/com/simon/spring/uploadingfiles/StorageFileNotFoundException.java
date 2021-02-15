package com.simon.spring.uploadingfiles;

@SuppressWarnings("serial")
public class StorageFileNotFoundException  extends Exception { 
    public StorageFileNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}