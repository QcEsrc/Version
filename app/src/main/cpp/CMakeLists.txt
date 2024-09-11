cmake_minimum_required(VERSION 3.22.1)

find_library(log-lib log)
find_library(android-lib android)

project(YourProjectName)

set(CMAKE_C_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(SOURCE_DIR src/main/cpp)

add_library(cpu SHARED apk.cpp app.cpp apply.cpp shell.cpp null.cpp url.cpp)

set_target_properties(cpu PROPERTIES OUTPUT_NAME "cpu")