
name: CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

  workflow_dispatch:

jobs:
  build-linux:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Run info
        run: echo Hello, world!

  build-windows:
      runs-on: windows-11


  build-macos:
       runs-on: macos-latest
