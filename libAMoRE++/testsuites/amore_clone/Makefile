# $Id: Makefile 1473 2011-04-01 00:09:31Z davidpiegdon $
# clone testsuite Makefile

.PHONY: all clean run jpegs test

include ../../config.mk
CXXFLAGS+=-Xlinker -rpath=${LIBDIR}

CPPFLAGS+=-I../../include -I${INCLUDEDIR} -Wall -ggdb3

LDFLAGS+=-L../../src -L${LIBDIR} -lAMoRE++

all: amore_clone

clean: nodata
	-rm -f *.o amore_clone

nodata:
	-rm -f *.dot *.jpg testlog

jpegs:
	for EACH in *.dot; do dot -Tjpg "$$EACH" -o "$$EACH.jpg"; done;

amore_clone: amore_clone

