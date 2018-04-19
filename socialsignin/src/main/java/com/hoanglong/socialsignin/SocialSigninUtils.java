package com.hoanglong.socialsignin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

/**
 * Owner by phamhoanglong.
 */

public class SocialSigninUtils {

    private static enum SocialType {GOOGLE, FACEBOOK, TWITTER, LINKEDIN}
    private static final int GOOGLE_SIGN_IN = 1203;

    private SocialType socialType;

    private Activity activity;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    private SocialResultHandler resultHandler;


    private SocialSigninUtils(Builder builder){
        if(builder instanceof GoogleBuilder){
            GoogleBuilder googleBuilder = (GoogleBuilder) builder;
            this.activity = builder.activity;
            this.googleSignInOptions = googleBuilder.googleSignInOptions;
            this.googleApiClient = googleBuilder.googleApiClient;
            this.resultHandler = googleBuilder.resultHandler;
            socialType = SocialType.GOOGLE;
        }
    }

    public void requestGoogleSignIn(){
        if(!googleApiClient.isConnected()) {
            googleApiClient.connect();
            Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            activity.startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN);
        }else{
            if (GoogleSignIn.getLastSignedInAccount(activity) != null) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        googleApiClient.connect();
                        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        activity.startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN);
                    }
                });
            }
        }
    }

    public void resultCallback(int requestCode, Intent data){
        try {
            if (data != null) {
                if (requestCode == GOOGLE_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result != null) {
                        resultHandler.handleGoogleCallback(result.getSignInAccount());
                    }
                }
            }
        }catch(NullPointerException npex){
            Log.d("NULL EXCEPTION", npex.toString());
        }
    }

    public static class FacebookBuilder extends Builder{

        public FacebookBuilder(Activity activity, SocialResultHandler resultHandler) {
            super(activity, resultHandler);
        }
    }

    public static class GoogleBuilder extends Builder{

        private GoogleSignInOptions googleSignInOptions = null;
        private GoogleApiClient googleApiClient = null;

        public GoogleBuilder(Activity activity, SocialResultHandler resultHandler) {
            super(activity, resultHandler);
        }

        public GoogleBuilder setGoogleSignInOptions(GoogleSignInOptions googleSignInOptions) {
            this.googleSignInOptions = googleSignInOptions;
            return this;
        }

        public GoogleBuilder setGoogleSignInClient(GoogleApiClient googleApiClient) {
            this.googleApiClient = googleApiClient;
            return this;
        }

        public SocialSigninUtils build(){
            if(googleSignInOptions == null) {
                googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail().build();
            }

            if(googleApiClient == null){
                googleApiClient = new GoogleApiClient.Builder(activity)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                        .build();
            }

            return new SocialSigninUtils(this);
        }
    }

    // Base builder
    private static class Builder{
        protected Activity activity;
        protected SocialResultHandler resultHandler;

        public Builder(Activity activity, SocialResultHandler resultHandler){
            this.activity = activity;
            this.resultHandler = resultHandler;
        }
    }
}
