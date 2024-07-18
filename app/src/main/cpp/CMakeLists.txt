cmake_minimum_required(VERSION 3.22.1)

find_library(log-lib log)
find_library(android-lib android)

project(YourProjectName)

set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(SOURCE_DIR src/main/cpp)

add_library(root SHARED root.cpp)

add_library(shell SHARED shell.cpp)

add_library(c++_shared SHARED c++_shared.cpp)

set_target_properties(root PROPERTIES OUTPUT_NAME "root")

set_target_properties(shell PROPERTIES OUTPUT_NAME "shell")

set_target_properties(c++_shared PROPERTIES OUTPUT_NAME "c++_shared")
