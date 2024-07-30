cmake_minimum_required(VERSION 3.22.1)

find_library(log-lib log)
find_library(android-lib android)

project(YourProjectName)

set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(SOURCE_DIR src/main/cpp)

add_library(apply SHARED root.cpp shell.cpp c++_shared.cpp)

set_target_properties(apply PROPERTIES OUTPUT_NAME "apply")