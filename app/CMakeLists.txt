# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
# 设置cmake 的最小版本  一般系统自动生成
cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             # 设置生成.so 的文件名
             hardware_lib

             # Sets the library as a shared library.
             #设置库的类型  一种静态文件  STATIC .a   一种动态文件  SHARED .so
             SHARED

             # Provides a relative path to your source file(s).
             # 需要编译的c/c++ 文件
             src/main/cpp/main.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       # 指定链接的目标库
                       hardware_lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
