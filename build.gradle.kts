plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["auth0Domain"] = "dev-hn57ebtwj18bjj8i.us.auth0.com"
        manifestPlaceholders["auth0Scheme"] = "app"
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

    viewBinding { enable = true }

    room {
        schemaDirectory("com.example.myapplication/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.android.auth)
    implementation(libs.android.jwtdecode)

    implementation(libs.gson)
    implementation(libs.picasso)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    /*annotationProcessor(libs.room.compiler)*/
    /*implementation(libs.room.ktx)
    implementation(libs.room.rxjava2)
    implementation(libs.room.rxjava3)
    implementation(libs.room.guava)
    implementation(libs.room.paging)
    testImplementation(libs.room.testing)
    implementation(libs.room.gradle.plugin)*/
    /*implementation(libs.sqlite)
    implementation(libs.sqlite.ktx)
    implementation(libs.sqlite.framework)*/
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.location)
    implementation(libs.places)
    implementation(libs.agora)

}