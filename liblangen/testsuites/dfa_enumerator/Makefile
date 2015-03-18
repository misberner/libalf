# $Id: Makefile 1473 2011-04-01 00:09:31Z davidpiegdon $
# dfa_enumerator testsuite Makefile

.PHONY: all clean run nodata jpegs

include ../../config.mk

CPPFLAGS+=-I../../include -I${INCLUDEDIR} -Wall -ggdb3

LDFLAGS+=-L../../src -L${LIBDIR} -llangen -lalf

all: dfa_enumerator

clean: nodata
	-rm -f *.o dfa_enumerator

nodata:
	-rm -f *.dot *.jpg

jpegs:
	for EACH in *.dot; do dot -Tjpg "$$EACH" -o "$$EACH.jpg"; done;

