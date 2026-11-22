// Plugins disponibles para los modulos (capitulo 02, paso 2).
plugins {
    // Pone en el classpath el compilador Kotlin 2.4.10 que exigen las librerias (AGP 9 usa Kotlin integrado).
    id("org.jetbrains.kotlin.android") version "2.4.10" apply false
    id("com.android.application") version "9.4.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.10" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("com.google.devtools.ksp") version "2.3.12" apply false
    id("com.google.gms.google-services") version "4.6.0" apply false
}
