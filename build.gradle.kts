plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

// 不使用默认的插件依赖方式, 而是从私有远程maven仓库（Nexus）中安装插件
//buildscript {
//    repositories {
//        maven {
//            url = uri("file:///C:/maven-repo/")
//            credentials {
//                username = findProperty("nexusUsername") as String?
//                password = findProperty("nexusPassword") as String?
//            }
//            allowInsecureProtocol() // groovy写法
//            isAllowInsecureProtocol = true // kotlin写法，允许使用http
//        }
//        mavenCentral()
//    }
//    dependencies {
//        // 使用自定义仓库中的插件
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
//        classpath("org.jetbrains.intellij.plugins:gradle-intellij-plugin:1.17.3")
//    }
//}
//apply(plugin = "org.jetbrains.kotlin.jvm")
//apply(plugin = "org.jetbrains.intellij")

group = "com.spoonb"
version = "0.0.2-alpha"

repositories {
//    maven {
//      预先测试proxy能否生效 curl -I -x http://domain%5cusername:password@hostname:port -u username:password targetUrl
//        url = uri("file:///C:/maven-repo/")
//    }
    // 其他仓库，如 Maven Central
    mavenCentral()
}

// Gradle IDEA插件配置
// 详细文档: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // 使用本地的IDEA
    //    localPath.set("C:\\dev\\JetBrains\\IntelliJ IDEA 2024.1.4")
    // 下载一个idea用于调试
    version.set("2024.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // JVM兼容版本配置
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        // 解决编译时中文报错问题
        options.encoding = "UTF-8"
    }

    // 打包成jar
    jar {
        // 将依赖一并打包进压缩包中
        from(configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        })
    }

// Kotlin编译配置，不需要
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "17"
//    }

    patchPluginXml {
        // IDEA版本配置
        // 最低支持版本
        sinceBuild.set("232")
        // 最高支持版本
        untilBuild.set("242.*")
    }

    // 解决控制台输出中文乱码的问题
    withType<JavaExec> {
        /*
            -Dfile.encoding：指定文件的默认编码。
            -Dsun.stdout.encoding：指定标准输出流 (System.out) 的编码。
            -Dsun.stderr.encoding：指定标准错误流 (System.err) 的编码。
         */
        jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

dependencies {
    // https://mvnrepository.com/artifact/org.freemarker/freemarker
    implementation("org.freemarker:freemarker:2.3.31")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    // 添加 Lombok 的注解处理器
    annotationProcessor("org.projectlombok:lombok:1.18.24")
// 如果有测试代码中使用 Lombok，可以添加以下依赖
//    testCompileOnly("org.projectlombok:lombok:1.18.24")
//    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}
