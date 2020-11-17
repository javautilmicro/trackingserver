package com.javautilmicro.tracking.services;


import java.util.UUID;
import java.io.File;



public interface TrackingService {
    
    String getOkTempFile();
    
    void putSpecialSinglePixelImgIntoTmp();
    
    File getImageFile();

}
