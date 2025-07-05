plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "jp.co.yumemi.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1") // Kotlinの拡張機能を含むAndroidXのコアライブラリ
    implementation("androidx.appcompat:appcompat:1.6.1") // 以前のAndroidバージョンとの互換性を提供するライブラリ
    implementation("com.google.android.material:material:1.9.0") // Material Designコンポーネント

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // AndroidXテスト用のJUnit拡張機能
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Android UIテストフレームワーク

    // Jetpack Compose BOM (Bill of Materials)
    val composeBom = platform("androidx.compose:compose-bom:2023.06.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Jetpack Compose UI ライブラリ
    implementation("androidx.compose.material3:material3") // Material Design 3のコンポーネント
    // implementation("androidx.compose.material:material") // (Material Design 2を使用する場合に選択)
    implementation("androidx.compose.foundation:foundation") // UIの基本的な要素とレイアウト
    implementation("androidx.compose.ui:ui-graphics") // UIのグラフィック描画機能
    implementation("androidx.compose.ui:ui-tooling-preview") // Android Studioでのプレビュー機能
    implementation("androidx.activity:activity-compose:1.7.2") // ComposeとActivityの統合

    // テストライブラリ
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // Compose UIのテスト用JUnitルール

    // デバッグ専用ライブラリ
    debugImplementation("androidx.compose.ui:ui-tooling") // 開発時のUIツール（Inspectorなど）
    debugImplementation("androidx.compose.ui:ui-test-manifest") // テストマニフェスト（通常は自動的に処理されます）
}


