package com.example.mindsafe.helper;

import com.example.mindsafe.responseModels.KeyVaultResponseModel;

import java.util.List;

public class GlobalData {
    private static List<KeyVaultResponseModel> globalList;

    public static List<KeyVaultResponseModel> getGlobalList() {
        return globalList;
    }

    public static void setGlobalList(List<KeyVaultResponseModel> list) {
        globalList = list;
    }
}
