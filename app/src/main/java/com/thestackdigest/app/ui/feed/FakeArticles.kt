package com.thestackdigest.app.ui.feed

import com.thestackdigest.app.domain.model.Article

val fakeArticles = listOf(

    Article(
        id = "1",
        title = "Introducing the AndroidX Security State Libraries: A Unified View of Device Security",
        description = "AndroidX Security State libraries provide a centralized way to understand the security posture and pending updates of Android devices.",
        articleUrl = "https://android-developers.googleblog.com/2026/09/introducing-androidx-security-state-libraries.html",
        imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjIF-dBH1TU5g6y4jdOLCKBo8KGPlPMUNQAGib3WSE8HPaLHG8iMXhgCiMZ2wyQ4kK93wQGmLiUEWuzlJRKcMVhPak5LantVgo3DEOcRORRJw8lRJk6FytYtUmF6xXyWNTVa2Ki7OD2tEHBS63e4VsDnhW3CkB3YCrWlZzv0DBoaX0v-ZQHBsDMvgmiUXI/s72-c/AndroidX%20Security%20State%20Library_Meta.png",
        sourceName = "Android Developers",
        authorName = "Android Developers",
        publishedAt = System.currentTimeMillis() - (2 * 60 * 60 * 1000),
        updatedAt = null,
        categories = listOf("Android", "Security"),
        isSaved = false
    ),

    Article(
        id = "2",
        title = "Kotlin 2.4.20 Released",
        description = "The Kotlin 2.4.20 release is out with improvements to the standard library, Kotlin Native, Wasm, Gradle, build tools, and the compiler.",
        articleUrl = "https://blog.jetbrains.com/kotlin/2026/09/kotlin-2-4-20-released/",
        imageUrl = "https://blog.jetbrains.com/wp-content/uploads/2026/09/KT-releases-BlogSocialShare-1280x720-1.png",
        sourceName = "Kotlin Blog",
        authorName = "Daniel Csorba",
        publishedAt = System.currentTimeMillis() - (5 * 60 * 60 * 1000),
        updatedAt = null,
        categories = listOf("Kotlin", "Releases"),
        isSaved = true
    ),

    Article(
        id = "3",
        title = "Android Bench 2.0: Pushing the Frontier With Challenging Long-Horizon Tasks",
        description = "Android Bench 2.0 introduces more challenging long-horizon tasks designed to evaluate AI models and coding agents on realistic Android development work.",
        articleUrl = "https://android-developers.googleblog.com/2026/09/android-bench-2-long-horizon-tasks.html",
        imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhCs6gPNr-l6f79eAyix8OZ59gg6K5y8QVTb6vuU2mNR9qdIlN2VUvGzbTenI-pIEGhMYql-E-t7Hs2Z0vI_UYnHte1w3vPRpjk7E0DPenuSkt-3gUM3y5GYZKHgciA4o3Ox2oxVkNuHiCwUX1WKCUkQhzBAd2FJhFiB-k5UKXYA67hXQHRVdFwrjHWFLQ/s72-c/Bench%202.0%20Metadata-bench.png",
        sourceName = "Android Developers",
        authorName = "Android Developers",
        publishedAt = System.currentTimeMillis() - (10 * 60 * 60 * 1000),
        updatedAt = null,
        categories = listOf("Android", "AI"),
        isSaved = false
    ),

    Article(
        id = "4",
        title = "Kotlin Toolchain 0.12: Multiplatform Library Publishing, Wasm Apps, and More",
        description = "Kotlin Toolchain 0.12 adds multiplatform library publishing, Wasm application support, Compose Hot Reload improvements, and more.",
        articleUrl = "https://blog.jetbrains.com/kotlin/2026/09/kotlin-toolchain-0-12-multiplatform-library-publishing-wasm-apps-and-more/",
        imageUrl = "https://blog.jetbrains.com/wp-content/uploads/2026/09/KT-social-BlogFeatured-1280x720-1.png",
        sourceName = "Kotlin Blog",
        authorName = "Joffrey Bion",
        publishedAt = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
        updatedAt = null,
        categories = listOf(
            "Kotlin",
            "Multiplatform",
            "Toolchain"
        ),
        isSaved = false
    ),

    Article(
        id = "5",
        title = "Compose Multiplatform 1.12.0 Released",
        description = "Compose Multiplatform 1.12.0 introduces new tooling for AI assistants, web resource improvements, and more control over desktop window states.",
        articleUrl = "https://blog.jetbrains.com/kotlin/2026/08/compose-multiplatform-1-12-0/",
        imageUrl = "https://blog.jetbrains.com/wp-content/uploads/2026/08/CMP-social-BlogFeatured-1280x720-1.png",
        sourceName = "Kotlin Blog",
        authorName = "Elvira Mustafina",
        publishedAt = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000),
        updatedAt = null,
        categories = listOf(
            "Kotlin",
            "Compose",
            "Multiplatform"
        ),
        isSaved = true
    )
)