Diaspora
========

## Organization

This directory contains all of the code for running Diaspora as a Java
library with your Android apps. We also include our example
applications, performance testing applications and scripts for
deploying applications across servers. 

- deployment: scripts for running Diaspora applications on
  servers. Starts the Diaspora servers for the "cloud side" and the
  OMS.
  
- example\_apps: our todo list application and twitter-like
  application.

- generators: scripts for running our compiler for generating Diaspora
  object stubs and DM stubs.

- diaspora: the core Diaspora library. Contains the following packages
  (located in `app/src/main/java/diaspora`):
  - app: Application specific classes, like the starting point for bootstrapping a Diaspora app.
  - common: Basic data structures.
  - compiler: our compiler for generating Diaspora object and DM component stubs.
  - dms: some example deployment managers
  - kernel: the Diaspora kernel server that runs as a library on every node that runs a Diaspora app.
  - oms: the Object Management/Tracking Service. 
  - runtime: library functions for creating a Diaspora object (hack because we don't have diaspora keywords in the JVM).

- tests: performance testing apps. Good example simple example
  application with one Diaspora object.

## Building Diaspora for x86

The core Diaspora library and example apps can be compiled as x86 Java apps
using Maven. The dependencies for building Diaspora for x86 are Java, Python,
and Maven (`apt install openjdk-8-jdk python maven` on Ubuntu).

1. Compile the core Diaspora library:

        $ cd diaspora
        $ mvn package

2. Generate the stubs for the core Diaspora library:

        $ cd ../generators
        $ python generate_policy_stubs.py

3. Compile the core Diaspora library again so that it includes the stubs:

        $ cd ../diaspora
        $ mvn package

4. Compile an example app:

        $ cd ../example_apps/ToDoList
        $ mvn package

5. Generate the stubs for the example app:

        $ cd ../../generators
        $ python generate_app_stubs.py

6. Compile the example app again so that it includes the stubs:

        $ cd ../example_apps/ToDoList
        $ mvn package

## Running Diaspora on x86

1. Place your servers in deployment/servers.json.

2. Place the config for the app that you want to run in
deployment/app.py. This file needs a starting point for the
server-side and the client-side of your Diaspora app.

3. Run deploy.py to run the app. Make sure the variable `log_folder` at the
top of deploy.py is set to a valid path.

## Building and running Diaspora on Android

The Diaspora core library and example app directories are Android Studio
projects, so these components can each be built using the Android Studio IDE
or command-line tools. Before building Diaspora components for Android, make
sure to compile the components for x86 and generate stub files as described
above. Also, the example apps contain dependencies on the AAR file generated
by building the core Diaspora library, so build the core library before
building example apps.

## Third-party licenses

The subdirectories `java`, `javax`, and `org` of `diaspora/app/src/main/java/`
contain source code from the Apache Harmony project (with slight modifications
to some files). These source files are distributed under the Apache License.
The full license is in the file `LICENSE-apache-harmony`.
