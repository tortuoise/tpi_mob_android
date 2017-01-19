package biz.stratadigm.tpi.di.module;

import biz.stratadigm.tpi.BuildConfig;
import biz.stratadigm.tpi.di.scope.PerApplication;
import biz.stratadigm.tpi.manager.ApiInterface;
import biz.stratadigm.tpi.manager.AppPreferences;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    @PerApplication
    @Provides
    OkHttpClient provideOkHttpClient(Interceptor headerInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();
    }

    @PerApplication
    @Provides
    Interceptor provideHeaderInterceptor(AppPreferences appPreferences) {
        return chain -> {
            Request.Builder builder = chain.request()
                    .newBuilder()
                    .addHeader("User-agent", System.getProperty("http.agent"))
                    .addHeader("Content-Type", "application/json");
            appPreferences.getToken().ifPresent(token -> builder.addHeader("authorization", token));
            return chain.proceed(builder.build());
        };
    }

    @PerApplication
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    @PerApplication
    @Provides
    ApiInterface provideApiInterface(OkHttpClient httpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiInterface.class);
    }

    @PerApplication
    @Provides
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }
}
