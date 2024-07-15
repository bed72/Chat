plugins {
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.compiler) apply false
    alias(libs.plugins.application) apply false
}