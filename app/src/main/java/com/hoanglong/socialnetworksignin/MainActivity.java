package com.hoanglong.socialnetworksignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.hoanglong.socialsignin.SocialResultHandler;
import com.hoanglong.socialsignin.SocialSigninUtils;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SocialResultHandler{

    private SocialSigninUtils socialUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signInByGoogle(){
        socialUtils = new SocialSigninUtils.GoogleBuilder(this, this)
                .setGoogleSignInOptions(new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("538676902422-p0qrtbblus07sfolcl96fla4ffbl3l3s.apps.googleusercontent.com")
                        .requestEmail()
                        .build())
                .build();

        socialUtils.requestSignIn(null);
    }

    public void signInByFacebook(){
        socialUtils = new SocialSigninUtils.FacebookBuilder(this, this).build();
        socialUtils.requestSignIn(Arrays.asList("public_profile"));
    }

    public void onClicked(View v){
        switch (v.getId()){
            case R.id.activity_main_btn_sign_in_google:
                signInByGoogle();
                break;
            case R.id.activity_main_btn_sign_in_facebook:
                signInByFacebook();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        socialUtils.resultCallback(requestCode, resultCode, data);
    }

    @Override
    public void handleGoogleCallback(GoogleSignInAccount account) {
        Toast.makeText(this, "Account : " + account.getDisplayName() + "Token: " + account.getIdToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleFacebookCallbackSuccess(LoginResult loginResult) {
        Toast.makeText(this, "Facebook Token: " + loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleFacebookCallbackError(FacebookException facebookException) {
        Toast.makeText(this, facebookException.toString(), Toast.LENGTH_SHORT).show();
    }
}
