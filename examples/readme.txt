This archive contains examples of how to employ libalf v 0.3 into a user
application. In this archive you find an online- and an offline-example for the
C++ and Java programming language.

In the remaining text you find information on how to compile and run the
examples.

As a preliminary step, you need to compile the libalf and, if desired, the jalf
library. Please follow the instruction on the libalf website
http://libalf.informatik.rwth-aachen.de


-----------------------
         C++
-----------------------

a) Compile the examples
-----------------------

To compile the examples using C++, a Makefile is provided
(http://linux.die.net/man/1/make). Simply use the command

				PREFIX=path_to_libalf make

Thereby the variable PREFIX has to specify the directory where you installed 
libalf (if you did not choose to install libalf into your syste,), i.e.
path_to_libalf has to be replaced with the correct path to libalf.

b) Run the examples
-----------------------

To run the examples, you have to specify the location of the compiled libalf
library (if you did not install libalf into your system). This can be done as
follows:

* On Windows simply put the compiled library into the directory your examples
  are located in. Alternatively, you can also add the path containing the libalf
  library to your PATH variable.

* On Linux you can use the LD_LIBRARY_PATH variable to point to libalf's
  location, e.g.
  
				LD_LIBRARY_PATH=path_to_libalf.so ./online


-----------------------
		 Java
-----------------------

a) Compile the examples
-----------------------

To compile the examples using Java, you can use ANT (http://ant.apache.org/).
Simply use the command

					ant -DPREFIX=path_to_jalf.jar
					
Thereby path_to_jalf.jar has to specify the directory where the jalf.jar is
located in. The option -D is used to specify variables (in this case to add the
jalf.jar to Java's classpath).

b) Run the examples
-----------------------
Running the Java examples is nearly the same as running the C++ examples.
However, additionally you have to be sure that the compiled jalf library and the
jalf.jar can be found. Do this by specifying the following two parameters:

* -classpath "path_to_jalf/jalf.jar:."
  Set the classpath to point to the jalf.jar and the directory the examples are
  located in (here it is assumed that this is the current directory "."). Note
  that you have to use the right separator (":" on Linux and ";" on Windows).

* -Djava.library.path=path_to_jalf
  Let the Java library path variable point to the compiled jalf C++ libary.
  
Running the online-example can look like

				java -classpath "..." -Djava.library.path=... Online
				

================================================================================

For further assistance please refer to the libalf website

				http://libalf.informatik.rwth-aachen.de