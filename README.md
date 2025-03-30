# Guardian News App

Sample news client app with content fetched from Guardian News API

![](https://github.com/patrickjason91/sample-news-app-android/blob/main/docs/guardian-news-android-screenshot.png)
![](https://github.com/patrickjason91/sample-news-app-android/blob/main/docs/guardian-news-android-screenshot-2.png)

## How to build
### Prerequisites
- Android Studio Ladybug
- Android versions
   - Minimum - API level 24
   - Target SDK - API level 34
- Java version
  - JDK 17

### Set the API Key
The app gets the API key from the `local.properties` file.
Thus add the line `theguardian.apiKey=<API_KEY>`.

Obtain API key from [The Guardian Open Platform](https://open-platform.theguardian.com/access/).

Note that for developer API key, there are restrictions such as a daily request limit of 500.
## What libraries are used
- App Architecture - Android Architecture Components
- Networking - Retrofit+OkHttp
- Persistence - Room Library
- UI - Jetpack Compose
- Dependency Injection - Hilt+Dagger
- Image loading - Glide Compose library