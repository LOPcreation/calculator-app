# Google Play Store Publishing Guide

## Pre-Publication Checklist

### 1. App Icon (512×512 px)
- Create a professional icon
- Should be recognizable at small sizes
- PNG or JPG format, max 1 MB

### 2. Feature Graphic (1024×500 px)
- Showcase main features
- No text required but can be helpful
- PNG or JPG format

### 3. Screenshots (at least 2, max 8)
- Recommended size: 1080×1920 px (portrait)
- Or 2560×1440 px (high res)
- Show key features:
  1. Calculator interface
  2. Unit converter with conversion
  3. Currency converter with live rates
  4. Result display

### 4. App Details

**Title**: "Calculator Pro"

**Short Description** (80 characters):
"Advanced calculator with unit and currency conversion"

**Full Description** (4000 characters max):
```
Calculator Pro is a powerful, all-in-one calculator app for Android with advanced features.

Features:
✓ Basic & Advanced Calculator
  - Full mathematical expressions
  - Square root, power, percentage functions
  - Parentheses support
  - Real-time calculation

✓ Unit Converter (Both Ways)
  - Length (mm, cm, m, km, inches, feet, yards, miles)
  - Weight (mg, g, kg, oz, lb, tons)
  - Temperature (°C, °F, K)
  - Volume (ml, l, gallons, pints, fl oz)
  - Area (mm², cm², m², km², in², ft², yd², mi²)
  - Time (ms, s, min, hr, days)
  - Instant swap for reverse conversion

✓ Real-time Currency Converter
  - 30+ supported currencies
  - Live exchange rates from online sources
  - Offline fallback data
  - Exchange rate display with timestamp
  - Instant swap for reverse conversion

Features:
- Clean and intuitive Material Design 3 interface
- Dark and light theme support
- Fast and accurate calculations
- Offline mode support
- No ads, no subscriptions
- Small app size
- Regular updates with new features

Permissions:
- Internet: For live currency exchange rates
- Network state: To check connectivity

No personal data is collected or stored.

Download Calculator Pro now and simplify your calculations!
```

**Category**: Tools or Utilities

**Content Rating**: Everyone

**Privacy Policy**: 
Create and link to a simple privacy policy:
```
Privacy Policy for Calculator Pro

Calculator Pro does not collect, store, or transmit any personal data.

Data Usage:
- Exchange rates are fetched from exchangerate-api.com
- No personal information is sent to any service
- All calculations are performed locally on your device
- Exchange rate data is cached locally and not sold or shared

Permissions Explanation:
- INTERNET: Used only to fetch current exchange rates for currency conversion
- ACCESS_NETWORK_STATE: Used to check if internet is available

For questions, contact: your.email@example.com
```

## Setup Process

### Step 1: Create Google Play Developer Account
1. Go to https://play.google.com/console
2. Pay $25 one-time registration fee
3. Complete payment and account setup

### Step 2: Create Release Key
```bash
keytool -genkey -v -keystore calculator_release.jks \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias calculator_key
```

### Step 3: Configure Signing
Update `build.gradle.kts`:
```kotlin
signingConfigs {
    release {
        storeFile = file("calculator_release.jks")
        storePassword = "your_store_password"
        keyAlias = "calculator_key"
        keyPassword = "your_key_password"
    }
}

buildTypes {
    release {
        signingConfig = signingConfigs.release
        minifyEnabled = true
        shrinkResources = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### Step 4: Build Bundle
```bash
./gradlew clean bundleRelease
```
Output: `app/build/outputs/bundle/release/app-release.aab`

### Step 5: Create App on Google Play Console
1. Click "Create app"
2. Fill in app name: "Calculator Pro"
3. Select category: Tools
4. Indicate if app has ads: No
5. Indicate if app is for children: No
6. Set content rating: Everyone

### Step 6: Fill in Store Listing
- Upload screenshots (4-5 minimum)
- Upload feature graphic
- Upload icon
- Fill in title and descriptions
- Set country distribution
- Choose price: Free

### Step 7: Content Rating
1. Complete content rating questionnaire
2. Receive automatic rating (usually Everyone)

### Step 8: Upload App Bundle
1. Go to "Release" → "Production"
2. Click "Create new release"
3. Upload `app-release.aab` file
4. Fill in "What's new in this version"
5. Review all permissions

### Step 9: Review and Publish
1. Review all information
2. Accept Play App Signing terms
3. Click "Publish"

## Post-Publication

### Monitoring
- Check Play Console daily for the first week
- Monitor crash reports
- Read user reviews and respond
- Track installs and uninstalls

### Updates
- Increment `versionCode` and `versionName`
- Test on multiple devices
- Build new bundle
- Upload to Play Console
- Publish update

### Version Strategy
- Major: 1.0 (initial release)
- Minor: 1.1, 1.2 (feature additions)
- Patch: 1.0.1 (bug fixes)

## Troubleshooting

### App Rejected?
- Check email for specific reason
- Common issues:
  - Missing privacy policy
  - Permissions not justified
  - App crashes on startup
  - Contains offensive content

### Low Downloads?
- Improve app description
- Add more/better screenshots
- Request user reviews
- Promote on social media
- Use relevant keywords

### Updates Not Showing?
- Wait 2-3 hours for propagation
- Clear Play Store cache
- Check device for auto-update enabled

## Marketing Tips

1. **Keywords**: Include in description:
   - calculator, unit converter, currency converter
   - exchange rate, unit conversion
   - calculate, convert, math

2. **Screenshots**: Show real usage with:
   - Clear calculation results
   - Active unit conversions
   - Live currency rates

3. **Description**: Highlight:
   - No ads, no subscriptions
   - Works offline
   - Fast and accurate
   - Beautiful UI

4. **Ratings**: 
   - Encourage reviews from satisfied users
   - Respond to all feedback
   - Fix issues quickly

## APK Size Optimization

Current: ~4-5 MB

Optimizations applied:
- ProGuard code shrinking
- Resource shrinking
- Jetpack Compose
- Minimal dependencies

## Performance Targets

- Launch time: < 2 seconds
- Calculation time: < 100ms
- API response: < 2 seconds
- Memory usage: < 50 MB

## Analytics (Optional)

To add Firebase Analytics:
1. Add Firebase plugin to build.gradle
2. Connect to Firebase Console
3. Track user events:
   - Calculator used
   - Unit conversion performed
   - Currency conversion attempted
   - API errors

## Support Email

Create dedicated email: `support@yourcompany.com`
Respond to user emails within 24 hours.
