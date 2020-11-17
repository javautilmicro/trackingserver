package com.javautilmicro.tracking.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;
import java.io.FileInputStream;

import com.javautilmicro.tracking.services.TrackingService;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class TrackingController {

    // private final BeerServiceV2 beerServiceV2;
    private final TrackingService trackingService;
    private final ServletContext context;
    
    public TrackingController(TrackingService trackSrvs, ServletContext servltCtx) {
        this.trackingService = trackSrvs;
        this.context = servltCtx;
    }
    
    // /ping - returns response code 200 and string OK when file /tmp/ok is present,
    //         if file is not present returns
    //          503 service unavailable
    @GetMapping({"/ping"})
    public ResponseEntity handlePing(HttpServletRequest request) {
        ResponseEntity re = null;
        HttpHeaders responseHeaders = new HttpHeaders();
        String okFileStr = trackingService.getOkTempFile();
        if(okFileStr == null) {
            log.info(">   - file does not exist, so we are done here!");
            log.info(">   - sort of, there are two paths for this stuff!!");
            
        } else {
            log.info(">   - file is NOT Null, here is the string version:.......v");
            log.info(">   - file:.......>"+okFileStr+"<..........");
        }
        
        //return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    @GetMapping({"/img"})
    public ResponseEntity handleImg(HttpServletRequest request){
        
    
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        byte[] media = null;
        try {
            
            File file = ResourceUtils.getFile("classpath:static/spacer.gif");
    
            //in = context.getResourceAsStream("/static/spacer.gif");
            in = new FileInputStream(file);
            
        
        
            media = IOUtils.toByteArray(in);
    
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            
            headers.setContentType(MediaType.IMAGE_GIF);
            
            responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
            
            String reqUri = request.getRequestURI();
            String reqId = request.getRequestedSessionId();
            String reqUser = request.getRemoteUser();
            String reqAddr = request.getRemoteAddr();
            // this is where we log our important information to our logs?
            log.info(">   - image file success :for: reqURI-["+reqUri+"] reqSessId-["+reqId+"] remoteUsr-["+reqUser+"] remoteAddr-["+reqAddr+"]");
            
            
            
            
            
            
        } catch(Exception ix) {
            log.warn(">   - failed to retrieve image file: this is a problem that needs addressing!");
            log.warn(">   - here is error-msg: "+ix.getMessage());
            log.warn(">   - and the whole stacktrace:......v");
            ix.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                    
                } catch(Exception fx) {
                    //
                }
            }
        }
        
        if(responseEntity == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    
        
        return responseEntity;
        
        
        
    }
    
    
    
    
    //@ExceptionHandler(ConstraintViolationException.class)
    //public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e){
    //    List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
    //
    //    e.getConstraintViolations().forEach(constraintViolation -> {
    //        errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
    //    });
    //
    //    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    //}

}
