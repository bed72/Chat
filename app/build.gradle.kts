plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compiler)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.application)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    compileSdk = 35
    namespace = "com.bed.chat"

    defaultConfig {
        minSdk = 29
        targetSdk = 35
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
        }

        register("profile") {
            isMinifyEnabled = true
//            isShrinkResources = true
            initWith(getByName("debug"))
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-staging.pro"
            )
        }

        release {
            isMinifyEnabled = true
//            isShrinkResources = true
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
    implementation(libs.exifinterface)
    // Others
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.core)
    implementation(platform(libs.compose))

    // Test
    testImplementation(libs.bundles.test)

    // Test Android
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(platform(libs.compose))
    androidTestImplementation(libs.bundles.android.test)

    // Debug
    debugImplementation(libs.bundles.debug)
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


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.4"
    }

    generateProtoTasks {
        all().configureEach {
            builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}