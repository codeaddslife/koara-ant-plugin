[![Koara](http://www.koara.io/logo.png)](http://www.koara.io)

[![Build Status](https://img.shields.io/travis/codeaddslife/koara-ant-plugin.svg)](https://travis-ci.org/codeaddslife/koara-ant-plugin)
[![Coverage Status](https://img.shields.io/coveralls/codeaddslife/koara-ant-plugin.svg)](https://coveralls.io/github/codeaddslife/koara-ant-plugin?branch=master)
[![Latest Version](https://img.shields.io/maven-central/v/io.koara/koara-ant-plugin.svg?label=Maven Central)](http://search.maven.org/#search%7Cga%7C1%7Ckoara-ant-plugin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/codeaddslife/koara-ant-plugin/blob/master/LICENSE)

# Koara-ant-plugin
This project is a plugin for parsing [Koara](http://www.koara.io) documents with [Apache Ant](http://ant.apache.org).

## Getting Started
[Download](http://repo1.maven.org/maven2/io/koara/koara-ant-plugin/0.1.0/koara-html-0.1.0.jar) the koara-ant-plugin jar file and include the following code in your Ant build file.

```xml
<!-- Define the task -->
<taskdef name="koara" classname="io.koara.ant.ParseTask" 
    classpath="${basedir}/libs/koara-ant-plugin-0.1.0.jar" />

<!-- Lint the code -->
<target name="parseDocuments">  
  <koara dir="${basedir}/src/kd" />
</target>
```