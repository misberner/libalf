# $Id: Makefile 1473 2011-04-01 00:09:31Z davidpiegdon $
# a libmVCA testsuite Makefile

.PHONY: all clean

include ../../config.mk

CPPFLAGS+=-I../../include -I${INCLUDEDIR} -Wall -ggdb3

LDFLAGS+=-L../../src -L${LIBDIR} -lmVCA

all: contains

clean:
	-rm -f *.o contains *.png *.dot

contains: contains.cpp

