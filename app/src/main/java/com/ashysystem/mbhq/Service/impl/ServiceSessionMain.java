package com.ashysystem.mbhq.Service.impl;

import android.content.Context;

import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import com.ashysystem.mbhq.util.DateDeserializer;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceSessionMain {

    //private static final String BASE_URL = "http://192.168.2.140:61450/";
    //private static final String BASE_URL = "http://192.168.2.140/ABBBC.API/";

    ////////////////URLURL//////////////////////
    //public static final String BASE_URL = "http://dev1api.abbbconline.com/";
    public static final String BASE_URL = Util.ABBBC_BASE_URL;
    public static OkHttpClient client;
    ////////////////////////////////////////////

    private final Retrofit retrofit;

    public ServiceSessionMain(final Context context)
    {
        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.MINUTES).connectTimeout(60, TimeUnit.MINUTES).writeTimeout(60, TimeUnit.MINUTES);
        OkHttpClient.Builder httpClient = getUnsafeOkHttpClient().newBuilder().readTimeout(60, TimeUnit.MINUTES).connectTimeout(60, TimeUnit.MINUTES).writeTimeout(60, TimeUnit.MINUTES).retryOnConnectionFailure(true);
/*
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
*/
        // for logging requests and responses
        HttpLoggingInterceptor body = new HttpLoggingInterceptor();
        body.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(body);
        client = httpClient.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

/*
    public Service(final SharedPreferences sharedPreferences, final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                String accessToken = PreferenceUtil.getString(sharedPreferences, Constants.ACCESS_TOKEN, null);
                String imei = getImei(context, "");
                if (accessToken != null) {
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + accessToken)
                            .header("x-bebetrack-imei", imei)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                } else {
                    Request request = original.newBuilder()
                            .header("x-bebetrack-imei", imei)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            }
        });
        // for logging requests and responses
        HttpLoggingInterceptor body = new HttpLoggingInterceptor();
        body.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(body);
        OkHttpClient client = httpClient.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
*/

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * Returns imei number of the device .
     *
     * @param context
     * @param default_value
     * @return
     */
    public String getImei(final Context context, String default_value) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            imei = telephonyManager.getDeviceId();
            return imei;

        } catch (Exception e) {
            e.printStackTrace();
            return default_value;
        }

    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create request body for multipart request
     *
     * @param descriptionString
     * @return
     */
    @NonNull
    public RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

}