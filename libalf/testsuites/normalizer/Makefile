# $Id: Makefile 1472 2011-03-31 23:03:06Z davidpiegdon $
# normalizer testsuite Makefile

.PHONY: all clean

include ../../config.mk
CXXFLAGS+=-Xlinker -rpath=${LIBDIR}

CPPFLAGS+=-I../../include -I${INCLUDEDIR} -Wall -ggdb3

LDFLAGS+=-L../../src -L${LIBDIR} -lalf

all: normalizer

clean:
	-rm -f *.o normalizer

normalizer: normalizer.cpp

