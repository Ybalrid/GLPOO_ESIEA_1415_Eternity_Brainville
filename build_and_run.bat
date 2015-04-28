javac -cp lib\*:bin -d bin src\main\java\*.java
//jar -vcfm Eternity.jar Manifest.mf README.md LICENSE.txt build_and_run.* bin lib src
java -cp lib\*:bin main.java.Eternity
