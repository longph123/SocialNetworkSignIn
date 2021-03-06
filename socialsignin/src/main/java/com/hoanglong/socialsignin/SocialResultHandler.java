package com.hoanglong.socialsignin;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by phamhoanglong on 4/17/18.
 */

public interface SocialResultHandler {
    void handleGoogleCallback(GoogleSignInAccount account);
    void handleFacebookCallbackSuccess(LoginResult loginResult);
    void handleFacebookCallbackError(FacebookException facebookException);
}
