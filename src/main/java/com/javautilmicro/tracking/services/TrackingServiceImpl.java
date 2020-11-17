package com.javautilmicro.tracking.services;

import java.io.File;
import java.io.FileNotFoundException;

import lombok.extern.slf4j.Slf4j;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import java.io.InputStream;


@Slf4j
@Service
public class TrackingServiceImpl implements TrackingService {
    
    
    //@Autowired
    //ResourceLoader resourceLoader;
    
    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
    
    private InputStream getFileFromResourceAsStream(String fileName) {
        
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        
        // the stream holding the file content
        if (inputStream == null) {
            log.warn(">   - failed to retrieve file-["+fileName+"] as a stream: importStream is null");
            log.warn(">   - no way to get file from null inputStream, returning null now");
            return null;
        } else {
            return inputStream;
        }
        
    }
    
    public String getOkTempFile() {
        //
        String fileAsString = null;
        InputStream initialStream = null;
        try {
            
            String defaultBaseDir = System.getProperty("java.io.tmpdir");
            
            String filePath = defaultBaseDir + "/ok";
            log.info(">   - going for 'ok' file at: ->" + defaultBaseDir + "<-");
            File okfile = new File(filePath);
            boolean isFile = okfile.isFile();
            boolean canRead = okfile.canRead();
            
            if(isFile == true && canRead == true) {
    
                initialStream = new FileInputStream(okfile);
                fileAsString = readFromInputStream(initialStream);
            } else {
                log.warn(">   - failed to retrieve 'ok' file-["+filePath+"]: file.isFile()=["+isFile+"], file.canRead()=["+canRead+"] ");
                log.warn(">   - must return null 'fileAsString' now");
            }
            
            
            
        } catch(Exception fx) {
            log.warn(">   - caught exception going for tmp/ok file, nothing to do but return null now!");
            
        } finally {
            if(initialStream != null) {
                try {
                    initialStream.close();
                } catch(Exception xu) {
                    //
                }
            }
        }
        
        
        return fileAsString;
    }
    
    
    
    public File getImageFile() {
        
        String filePath = "static/spacer.gif";
    
        File file = new File(getClass().getResource(filePath).getFile());
        
        return file;
        
        
    }
    
    private byte[] readSmallBinaryFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);
    }
    
    private void writeSmallBinaryFile(byte[] bytes, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, bytes); //creates, overwrites
    }
    
    private static void printInputStream(InputStream is) {
        
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void putSpecialSinglePixelImgIntoTmp() {
        
        
        
        
        
        
        // get https://upload.wikimedia.org/wikipedia/commons/c/ce/Transparent.gif
    }
    
    
    
    //public File loadEmployeesWithSpringInternalClass() throws FileNotFoundException {
    //    return ResourceUtils.getFile("classpath:data/employees.dat");
    //}
    
    
    ///*
    //    @GetMapping(value = "/download")
    //    public ResponseEntity<Object> downloadFile() throws IOException  {
    //        String filename = "/var/tmp/mysql.png";
    //        File file = new File(filename);
    //        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    //        HttpHeaders headers = new HttpHeaders();
    //
    //        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
    //        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    //        headers.add("Pragma", "no-cache");
    //        headers.add("Expires", "0");
    //
    //        ResponseEntity<Object>
    //                responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
    //                MediaType.parseMediaType("application/txt")).body(resource);
    //
    //        return responseEntity;
    //    }
    //    */
    
    // /ping - returns response code 200 and string OK when file /tmp/ok is present,
    //         if file is not present returns 503 service unavailable
    
    /// img - returns a 1x1 gif image, and log the request in apache common log format
}
