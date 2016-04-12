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
<taskdef name="koara" classname="io.koara.ant.ConvertTask"
    classpath="${basedir}/libs/koara-ant-plugin-all.jar" />

<!-- Lint the code -->
<target name="convertDocuments">  
    <koara todir="${basedir}/output" modules="paragraphs,headings,lists" outputFormat="html5" >
        <fileset dir="${basedir}/input" />
    </koara>
</target>
```

## Task attributes
- `todir`: 
  Location to which all rendered documents should be written
  
- `modules`:
  Optional comma-seperated string of modules used to render the koara documents. By default, all modules will be used. Possible values: paragraphs, headings, lists, links, images, formatting, blockquote, code

- `outputFormat`:
  The format in which the koara documents should be rendered. Possible values: html5, xml