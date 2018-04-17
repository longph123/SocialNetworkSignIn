package com.hoanglong.socialsignin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by phamhoanglong on 4/17/18.
 */

public class SocialSigninUtils {

    private static final int GOOGLE_SIGN_IN = 1203;

    private Activity activity;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private SignInCallback signInCallback;

    private SocialSigninUtils(Builder builder){
        this.activity = builder.activity;
        this.googleSignInOptions = builder.googleSignInOptions;
        this.googleSignInClient = builder.googleSignInClient;
        this.signInCallback = builder.signInCallback;
    }

    public void setGoogleSignInOptions(GoogleSignInOptions options){
        this.googleSignInOptions = options;
    }

    public void setGoogleSignInClient(GoogleSignInClient client){
        this.googleSignInClient = client;
    }

    public void requestGoogleSignIn(){
        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN);
    }

    public void resultCallback(int requestCode, Intent data){
        if(requestCode == GOOGLE_SIGN_IN) {
            signInCallback.handleGoogleCallback(data);
        }
    }

    //Social user infos callback
    public interface SignInCallback{
        void handleGoogleCallback(Intent data);
    }

    private static class Builder{
        private Activity activity;
        private GoogleSignInOptions googleSignInOptions;
        private GoogleSignInClient googleSignInClient;
        private SignInCallback signInCallback;

        public Builder(Activity activity){
            this.activity = activity;
        }

        public Builder setGoogleSignInOptions(GoogleSignInOptions googleSignInOptions) {
            this.googleSignInOptions = googleSignInOptions;
            return this;
        }

        public Builder setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
            this.googleSignInClient = googleSignInClient;
            return this;
        }

        public Builder setSignInCallback(SignInCallback signInCallback){
            this.signInCallback = signInCallback;
            return this;
        }

        public SocialSigninUtils build(){
            return new SocialSigninUtils(this);
        }
    }
}
