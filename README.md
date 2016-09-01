[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidOAuth-green.svg?style=true)](https://android-arsenal.com/details/1/3837) [![Release](https://jitpack.io/v/adrielcafe/AndroidOAuth.svg)](https://jitpack.io/#adrielcafe/AndroidOAuth)

![AndroidOAuth](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/logo.png)

> A simple way to authenticate with **Google** and **Facebook** using **OAuth 2.0** in Android 

## How To Use

### Google

![Google Consent](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/google-consent.jpg) ![Google Auth](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/google-auth.jpg)

#### Login
```java
// Use a Web credential instead of Android credential
GoogleOAuth.login(this)
    .setClientId(Credentials.GOOGLE_CLIENT_ID)
    .setClientSecret(Credentials.GOOGLE_CLIENT_SECRET)
    .setAdditionalScopes("https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/user.birthday.read")
    .setRedirectUri(Credentials.GOOGLE_REDIRECT_URI)
    .setCallback(new OnLoginCallback() {
        @Override
        public void onSuccess(String token, SocialUser user) {
            Log.d("Google Token", token);
            Log.d("Google User", user+"");
        }
        @Override
        public void onError(Exception error) {
            error.printStackTrace();
        }
    })
    .init();
```

#### Logout ([revoke token](https://developers.google.com/identity/protocols/OAuth2WebServer#tokenrevoke))
```java
GoogleOAuth.logout(this)
    .setToken(currentToken)
    .setCallback(new OnLogoutCallback() {
        @Override
        public void onSuccess() {
            
        }
        @Override
        public void onError(Exception error) {
            
        }
    })
    .init();
```

### Facebook

![Facebook Consent](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/facebook-consent.jpg) ![Facebook Auth](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/facebook-auth.jpg)

#### Login
```java
// No need to configure Android section on Facebook app
FacebookOAuth.login(this)
    .setClientId(Credentials.FACEBOOK_APP_ID)
    .setClientSecret(Credentials.FACEBOOK_APP_SECRET)
    .setAdditionalScopes("user_friends user_birthday")
    .setRedirectUri(Credentials.FACEBOOK_REDIRECT_URI)
    .setCallback(new OnLoginCallback() {
        @Override
        public void onSuccess(String token, SocialUser user) {
            Log.d("Facebook Token", token);
            Log.d("Facebook User", user+"");
        }
        @Override
        public void onError(Exception error) {
            error.printStackTrace();
        }
    })
    .init();
```

#### Logout ([revoke token](https://developers.facebook.com/docs/facebook-login/permissions/requesting-and-revoking#revokelogin))
```java
FacebookOAuth.logout(this)
    .setToken(currentToken)
    .setCallback(new OnLogoutCallback() {
        @Override
        public void onSuccess() {
            
        }
        @Override
        public void onError(Exception error) {
            
        }
    })
    .init();
```


## Import to your project
Put this into your `app/build.gradle`:
```
repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.adrielcafe:AndroidOAuth:1.1.3'
}
```

## TODO
- [ ] Twitter support
- [X] Get `name`, `email`, `profileUrl` and `coverUrl` from authenticated user
- [X] `logout()` method to revoke token
- [X] `setAdditionalScopes()` method to add more scopes from [Google](https://developers.google.com/identity/protocols/googlescopes) and [Facebook](https://developers.facebook.com/docs/facebook-login/permissions)

## Dependencies
* [ScribeJava](https://github.com/scribejava/scribejava)
* [HttpAgent](https://github.com/studioidan/HttpAgent)

## License
```
The MIT License (MIT)

Copyright (c) 2016 Adriel Café

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
