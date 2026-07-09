# Calculator Pro - Advanced Android Calculator App

A feature-rich Android calculator application with unit conversion, currency conversion, and advanced mathematical functions.

## Features

### 1. **Basic Calculator**
- Full mathematical expression support
- Square root (√) and power (x²) functions
- Percentage (%) calculations
- Parentheses support for complex expressions
- Decimal support
- Clear and Delete operations
- Formatted results (removes unnecessary decimals)

### 2. **Unit Converter** (Both Ways)
- **Length**: mm, cm, m, km, inches, feet, yards, miles
- **Weight**: mg, g, kg, oz, lb, tons
- **Temperature**: Celsius, Fahrenheit, Kelvin
- **Volume**: ml, l, gallons, pints, fluid ounces
- **Area**: mm², cm², m², km², in², ft², yd², mi²
- **Time**: ms, seconds, minutes, hours, days
- Swap button for instant reverse conversion
- Real-time conversion as you type

### 3. **Currency Converter** (Real-time)
- 30+ supported currencies
- Real-time exchange rates from exchangerate-api.com
- Fallback mock data for offline usage
- 1-hour caching to reduce API calls
- Exchange rate display with timestamp
- Swap button for quick reverse conversion
- Support for major currencies: USD, EUR, GBP, JPY, AUD, CAD, INR, and more

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM with State Flow
- **Networking**: OkHttp3 + Gson
- **Async**: Kotlin Coroutines
- **Calculator Engine**: exp4j library

## Dependencies

```gradle
// Jetpack Compose
androidx.compose.ui:ui:1.5.0
androidx.compose.material3:material3:1.1.0
androidx.activity:activity-compose:1.7.2

// Networking
com.squareup.okhttp3:okhttp:4.11.0
com.google.code.gson:gson:2.10.1

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1

// Calculator
net.objecthunter:exp4j:0.4.8
```

## Project Structure

```
app/
├── src/main/java/com/example/calculator/
│   ├── MainActivity.kt              # Entry point
│   ├── logic/
│   │   ├── CalculatorLogic.kt      # Calculator operations
│   │   ├── UnitConverter.kt         # Unit conversion logic
│   │   └── CurrencyConverter.kt     # Currency conversion logic
│   └── ui/
│       ├── CalculatorApp.kt         # Main app UI structure
│       ├── theme/
│       │   ├── Theme.kt             # Material 3 theme
│       │   └── Type.kt              # Typography
│       └── screens/
│           ├── CalculatorScreen.kt      # Calculator UI
│           ├── UnitConverterScreen.kt   # Unit converter UI
│           └── CurrencyConverterScreen.kt # Currency converter UI
├── src/main/res/
│   ├── values/
│   │   ├── strings.xml
│   │   ├── colors.xml
│   │   └── themes.xml
│   └── AndroidManifest.xml
├── build.gradle.kts
└── proguard-rules.pro
```

## Building and Publishing

### Building Release APK

```bash
./gradlew clean bundleRelease
```

### Building Release Bundle for Play Store

```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

### Setup for Google Play Store

1. **Create Signed Key**
   ```bash
   keytool -genkey -v -keystore calculator_key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias calculator
   ```

2. **Configure Signing in build.gradle.kts**
   ```gradle
   signingConfigs {
       release {
           storeFile = file("calculator_key.jks")
           storePassword = System.getenv("KEYSTORE_PASSWORD")
           keyAlias = "calculator"
           keyPassword = System.getenv("KEY_PASSWORD")
       }
   }

   buildTypes {
       release {
           signingConfig = signingConfigs.release
       }
   }
   ```

3. **Update App Metadata** (in AndroidManifest.xml)
   - App name: "Calculator Pro"
   - Version code: 1
   - Version name: "1.0.0"

4. **Create Store Listing**
   - App title: "Calculator Pro"
   - Short description: "Advanced calculator with unit and currency conversion"
   - Full description: [See above]
   - Screenshots: 5-8 screenshots
   - Feature graphic: 1024x500px
   - Icon: 512x512px
   - Content rating questionnaire

5. **Upload to Play Store**
   - Go to Google Play Console
   - Create new app
   - Upload .aab file
   - Fill in store listing details
   - Review and publish

## Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## Permissions

- `INTERNET` - Required for currency conversion API calls
- `ACCESS_NETWORK_STATE` - To check internet connectivity

## API Integration

### Currency Exchange Rates
- **Provider**: exchangerate-api.com (Free tier)
- **Endpoint**: `https://api.exchangerate-api.com/v4/latest/{currency}`
- **Rate Limit**: 1500 requests/month (free tier)
- **Cache**: 1 hour to reduce API calls
- **Fallback**: Mock data for offline mode

## Offline Support

- Calculator: Fully functional offline
- Unit Converter: Fully functional offline
- Currency Converter: Uses cached rates if available, falls back to mock data if API unavailable

## Performance Optimizations

1. **Lazy Loading**: Screens load only when needed
2. **Caching**: 1-hour cache for exchange rates
3. **Coroutines**: Non-blocking API calls
4. **Proguard**: Code shrinking for smaller APK size
5. **Material Design 3**: Optimized UI rendering

## Security

- Cleartext traffic disabled for HTTPS enforcement
- Input validation for all calculations
- Safe exception handling
- No sensitive data logging

## Future Enhancements

- [ ] Scientific calculator functions (sin, cos, tan, etc.)
- [ ] Calculator history
- [ ] Dark theme toggle
- [ ] Multiple language support
- [ ] Widget support
- [ ] Cryptocurrency conversion
- [ ] Custom unit support
- [ ] Voice input for calculations

## License

MIT License - Feel free to use and modify

## Support

For issues or feature requests, please create an issue on GitHub.

## Author

Your Name
Version: 1.0.0
