plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compiler)
    alias(libs.plugins.application)
    alias(libs.plugins.serialization)
}

android {
    compileSdk = 34
    namespace = "com.bed.chat"

    defaultConfig {
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        applicationId = "com.bed.chat"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }

        register("profile") {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = ".profile"
            initWith(getByName("debug"))
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-staging.pro"
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "19"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Others
    implementation(libs.bundles.core)
    implementation(platform(libs.compose))

    // Test
    testImplementation(libs.bundles.test)

    // Test Android
    androidTestImplementation(platform(libs.compose))
    androidTestImplementation(libs.bundles.android.test)

    // Debug
    debugImplementation(libs.bundles.debug)

//    implementation(libs.ui)
//    implementation(libs.ui.tooling)
//    implementation(libs.ui.graphics)
//    implementation(libs.ui.material)
//
//    implementation(libs.core)
//    implementation(libs.activity)
//    implementation(libs.lifecycle)
//    implementation(platform(libs.compose))
//
//    testImplementation(libs.junit)
//
//    debugImplementation(libs.ui.tooling)
//    debugImplementation(libs.ui.manifest)
//
//    androidTestImplementation(libs.junit)
//    androidTestImplementation(libs.espresso)
//    androidTestImplementation(libs.ui.junit4)
//    androidTestImplementation(platform(libs.compose))
}

detekt {
    toolVersion = libs.versions.detekt.get()

    parallel = true

    debug = false
    allRules = false
    ignoreFailures = false
    buildUponDefaultConfig = false
    disableDefaultRuleSets = false

    basePath = projectDir.absolutePath
    ignoredBuildTypes = listOf("release")
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    source.setFrom(
        "$rootDir/app/src/main/java",
        "$rootDir/app/src/test/java",
        "$rootDir/app/src/androidTest/java"
    )
}

afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("detekt")
    }
}

tasks.detekt.configure {
    reports {
        sarif.required.set(true)
    }
}