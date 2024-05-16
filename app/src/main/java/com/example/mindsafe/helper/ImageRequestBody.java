package com.example.mindsafe.helper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ImageRequestBody {

    public static RequestBody createImageRequestBody(File imageFile) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
    }
}
