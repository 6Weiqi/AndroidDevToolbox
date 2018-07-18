# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# RemoteViews might need annotations.

#-keepattributes *Annotation*
-keep public class * extends java.lang.annotation.Annotation

# Preserve all fundamental application classes.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Preserve all View implementations, their special context constructors, and
# their setters.

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# Preserve all classes that have special context constructors, and the
# constructors themselves.

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# Preserve all classes that have special context constructors, and the
# constructors themselves.

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve all possible onClick handlers.

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}
# Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
# databinding
#-dontwarn android.databinding.**
#-keep class android.databinding.** { *; }
#-keep class android.databinding.annotationprocessor.** { *; }
#-keep class * extends android.arch.lifecycle.AndroidViewModel

# Album
-dontwarn com.yanzhenjie.album.**
-keep class com.yanzhenjie.album.**{*;}

-dontwarn com.yanzhenjie.fragment.**
-keep class com.yanzhenjie.fragment.**{*;}

-dontwarn com.yanzhenjie.mediascanner.**
-keep class com.yanzhenjie.mediascanner.**{*;}

-dontwarn com.yanzhenjie.loading.**
-keep class com.yanzhenjie.loading.**{*;}

-dontwarn com.yanzhenjie.statusview.**
-keep class com.yanzhenjie.statusview.**{*;}

# Durban
-dontwarn com.yanzhenjie.durban.**
-keep class com.yanzhenjie.durban.**{*;}

# Guava
-dontwarn sun.misc.**
-dontwarn javax.lang.model.element.Modifier
-ignorewarnings
-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**
-dontwarn com.google.common.**
#-keep class com.google.common.base.** {*;}
-dontwarn com.google.errorprone.annotations.**
#-keep class com.google.errorprone.annotations.** {*;}
#-dontwarn com.google.j2objc.annotations.**
#-keep class com.google.j2objc.annotations.** { *; }
-dontwarn java.lang.ClassValue
-ignorewarnings
#-keep class java.lang.ClassValue { *; }
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement { *; }

# Okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
#-ignorewarnings
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retrofit
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# EventBus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# LoadSir
-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}

# banner
-keep class com.youth.banner.** {
     *;
}

# Remove Log.
-assumenosideeffects class android.util.Log {
#    public static boolean isLoggable(java.lang.String, int);
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
#    public static *** e(...);
}

# Gson
-keep class com.google.gson.stream.** {*;}
-keepattributes EnclosingMethod