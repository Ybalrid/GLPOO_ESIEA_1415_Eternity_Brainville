#!/bin/bash

if [ ! -d src ]
then echo "Run this in the root of the project!"
	exit 1
fi

[ -d bin ] || mkdir ./bin/

[[ $(ls bin | wc -l) > 0 ]] && rm -r bin/*
javac -cp lib/*:bin -d bin $(find src/main/java/ -type f) \
&& javac -cp lib/*:bin -d bin $(find src/test/java/ -type f) \
&& java -cp bin:lib/* org.junit.runner.JUnitCore test.java.GamePanelTest test.java.SolutionTest \
&& jar -cfm Eternity.jar Manifest.mf README.md LICENSE.txt build_and_run.* bin/ lib/ src/ res/ pom.xml \
&& java -cp lib/*:bin main.java.Eternity

exit 0
