# $Id: Makefile 1472 2011-03-31 23:03:06Z davidpiegdon $
# basic alphabet testsuite Makefile

.PHONY: all clean

include ../../config.mk
CXXFLAGS+=-Xlinker -rpath=${LIBDIR}

CPPFLAGS+=-I../../include -I${INCLUDEDIR} -Wall -ggdb3

LDFLAGS+=-L../../src -L${LIBDIR} -lalf -lAMoRE++ -llangen

all: graded_inc_dec

clean:
	-rm -f *.o graded_inc_dec

graded_inc_dec: graded_inc_dec.cpp

