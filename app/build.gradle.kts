plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.apollo)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.app.omnipro_test_rm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.omnipro_test_rm"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// Apollo GraphQL Configuration
apollo {
    service("rickmorty") {
        packageName.set("com.app.omnipro_test_rm.graphql")
        generateQueryDocument.set(true)

        // GraphQL endpoint
        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
        generateKotlinModels.set(true)
        useSemanticNaming.set(true)
        generateFragmentImplementations.set(true)
        generateAsInternal.set(false)
        codegenModels.set("operationBased") // or "responseBased"
        flattenModels.set(true)
        mapScalarToUpload("Upload")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.room)
    implementation(libs.bundles.media3)
    implementation(libs.paging.compose)
    implementation(libs.coil.compose)

    // Apollo GraphQL & Koin DI for Rick & Morty app
    implementation(libs.bundles.apollo)
    implementation(libs.bundles.koin)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Testing
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.androidx.arch.testing)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.koin.test)
    testImplementation(libs.mockk.unit.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.bundles.roborazzi)
    testImplementation(libs.turbine)

    // Annotation processors
    ksp(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}