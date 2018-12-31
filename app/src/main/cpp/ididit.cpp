#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_io_github_uditkarode_ididit_MainActivity_stringFromJNIX(JNIEnv *env, jobject) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
