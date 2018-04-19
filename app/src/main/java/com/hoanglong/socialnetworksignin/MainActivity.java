package com.hoanglong.socialnetworksignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.hoanglong.socialsignin.SocialResultHandler;
import com.hoanglong.socialsignin.SocialSigninUtils;

public class MainActivity extends AppCompatActivity implements SocialResultHandler{

    private SocialSigninUtils socialUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        socialUtils = new SocialSigninUtils.GoogleBuilder(this, this)
                            .setGoogleSignInOptions(new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken("538676902422-p0qrtbblus07sfolcl96fla4ffbl3l3s.apps.googleusercontent.com")
                                    .requestEmail()
                                    .build())
                            .build();
                            //.setSignInCallback(this).build();
    }

    public void onClicked(View v){
        socialUtils.requestGoogleSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        socialUtils.resultCallback(requestCode, data);
    }

    @Override
    public void handleGoogleCallback(GoogleSignInAccount account) {
        Toast.makeText(this, "Account : " + account.getDisplayName() + "Token: " + account.getIdToken(), Toast.LENGTH_SHORT).show();
    }
}
