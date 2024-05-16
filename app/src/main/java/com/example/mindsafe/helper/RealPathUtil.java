package com.example.mindsafe.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class RealPathUtil {

    public static String getRealPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }

        // Check for file type
        if (uri.getScheme().startsWith("content")) {
            return getDataColumn(context, uri, null);
        } else if (uri.getScheme().startsWith("file")) {
            return uri.getPath(); // Already the actual path
        } else {
            return null;
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection) {
        String filePath = null;

        try (Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
