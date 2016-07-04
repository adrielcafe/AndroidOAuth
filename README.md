![Logo](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/logo.png)

# AndroidOAuth
> A simple way to authenticate with **Google** and **Facebook** using **OAuth 2.0** in Android

## Example
![Consent WebView](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/consent-webview.jpg) ![Token and User](https://raw.githubusercontent.com/adrielcafe/AndroidOAuth/master/screenshots/token-user.jpg)

### Google
```java
// Use a Browser credential instead of Android credential
GoogleOAuth.Builder(this)
    .setClientId(Credentials.GOOGLE_CLIENT_ID)
    .setClientSecret(Credentials.GOOGLE_CLIENT_SECRET)
    .setTokenCallback(new OnGetTokenCallback() {
        @Override
        public void onSuccess(String token, SocialUser account) {
            Log.d("Google Token", token);
            Log.d("Google User", account+"");
        }
        @Override
        public void onError(Exception error) {
            error.printStackTrace();
        }
    })
    .login();
```

### Facebook
```java
// No need to configure Android on Facebook app
FacebookOAuth.Builder(this)
    .setClientId(Credentials.FACEBOOK_APP_ID)
    .setClientSecret(Credentials.FACEBOOK_APP_SECRET)
    .setRedirectUri(Credentials.FACEBOOK_REDIRECT_URI)
    .setTokenCallback(new OnGetTokenCallback() {
        @Override
        public void onSuccess(String token, SocialUser account) {
            Log.d("Facebook Token", token);
            Log.d("Facebook User", account+"");
        }
        @Override
        public void onError(Exception error) {
            error.printStackTrace();
        }
    })
    .login();
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
  compile 'com.github.adrielcafe:AndroidOAuth:0.8'
}
```

## Dependencies
* [ScribeJava](https://github.com/scribejava/scribejava)

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