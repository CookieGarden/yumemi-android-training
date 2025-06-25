plugins {
    val gradle = "7.4.2"
    id("com.android.application") version gradle apply false
    id("com.android.library") version gradle apply false

    val kotlin = "1.9.10"
    kotlin("android") version kotlin apply false

    val ksp = "1.9.10-1.0.13"
    id("com.google.devtools.ksp") version ksp apply false
}
