package com.ashysystem.mbhq.Service.impl;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.webkit.MimeTypeMap;

import com.ashysystem.mbhq.video.DemoApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by AQB Solutions PVT. LTD. on 18/10/17.
 */

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private int ignoreFirstNumberOfWriteToCalls;
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private int numWriteToCalls = 0;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError();

        void onFinish();
    }

    public ProgressRequestBody(final File file, final UploadCallbacks listener) {
        mFile = file;
        mListener = listener;
        ignoreFirstNumberOfWriteToCalls = 0;
    }

    public ProgressRequestBody(final File file, final UploadCallbacks listener, final int ignoreFirstNumberOfWriteToCalls) {
        mFile = file;
        mListener = listener;
        this.ignoreFirstNumberOfWriteToCalls = ignoreFirstNumberOfWriteToCalls;
    }

    public File getFile() {
        return mFile;
    }

    public String getFileName() {
        return mFile != null ? mFile.getName() : "";
    }

    private String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = DemoApplication.getInstance().getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(getMimeType(Uri.fromFile(mFile)));
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        numWriteToCalls++;
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                // when using HttpLoggingInterceptor it calls writeTo and passes data into a local buffer just for logging purposes.
                // the second call to write to is the progress we actually want to track
                if (numWriteToCalls > ignoreFirstNumberOfWriteToCalls) {
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                }
                uploaded += read;
                sink.write(buffer, 0, read); // update progress on UI thread

                sink.flush();
            }
        } finally {
            in.close();
        }

    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int) (100 * mUploaded / mTotal));
        }
    }
}