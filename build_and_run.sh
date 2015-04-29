#!/bin/bash

if [ ! -d src ]
then echo "Run this in the root of the project!"
	exit 1
fi

[ -d bin ] || mkdir ./bin/

[[ $(ls bin | wc -l) > 0 ]] && rm -r bin/*
javac -cp lib/*:bin -d bin src/main/java/*.java
jar -vcfm Eternity.jar Manifest.mf README.md LICENSE.txt build_and_run.* bin/ lib/ src/
java -cp lib/*:bin main.java.Eternity

exit 0
