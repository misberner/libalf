# $Id: Makefile 1448 2011-02-28 17:16:10Z davidpiegdon $
# Makefile for java jalf testcase

.PHONY: all clean run

all:	Test.class

Test.class: Test.java
	javac -cp ../jalf.jar Test.java

clean:
	-rm Test.class

run:	Test.class
	java -cp ".:..:../jalf.jar" -Djava.library.path=.. Test

