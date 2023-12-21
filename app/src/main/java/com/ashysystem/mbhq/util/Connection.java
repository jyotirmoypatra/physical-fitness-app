package com.ashysystem.mbhq.util;

import static java.net.Proxy.Type.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by android1 on 20/5/15.
 */

public class Connection {

    private static String appType="customer";
    public static boolean checkConnection(Context ctx)
    {
        if(null!=ctx){
            ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo i = conMgr.getActiveNetworkInfo();
            if (i == null)
                return false;
            if (!i.isConnected())
                return false;
            if (!i.isAvailable())
                return false;

            return true;
        }
       return false;
    }

//    public static String serverCall(String Url, JSONArray parameter)
//    {
//        HttpClient client = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
//        HttpResponse response;
//        String jsonstring="";
//        try{
//            HttpPost post = new HttpPost(Url);
//            StringEntity se = new StringEntity(parameter.toString(),"UTF-8");
//
//            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//            post.setEntity(se);
//            response = client.execute(post);
//
//            if(response!=null){
//                InputStream in = response.getEntity().getContent();
//                BufferedInputStream bis = new BufferedInputStream(in);
//                ByteArrayBuffer baf = new ByteArrayBuffer(20);
//
//                int current = 0;
//
//                while((current = bis.read()) != -1){
//                    baf.append((byte)current);
//                }
//                jsonstring = new String(baf.toByteArray());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//
//        }
//
//        return jsonstring;
//    }
    /////////////////////////////////////////////////////////////////






    public static class SimpleSSLSocketFactory extends SSLSocketFactory {
        private SSLSocketFactory sslFactory = HttpsURLConnection.getDefaultSSLSocketFactory();

        public SimpleSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
                UnrecoverableKeyException {
            super();

            try {
                SSLContext context = SSLContext.getInstance("TLS");

                // Create a trust manager that does not validate certificate chains and simply
                // accept all type of certificates
                TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[] {};
                    }
                } };

                // Initialize the socket factory
                context.init(null, trustAllCerts, new SecureRandom());
                sslFactory = context.getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public String[] getDefaultCipherSuites() {
            return new String[0];
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return new String[0];
        }

        @Override
        public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
            return null;
        }

        @Override
        public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
            return null;
        }

        @Override
        public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
            return null;
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
            return null;
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
            return null;
        }
    }

}