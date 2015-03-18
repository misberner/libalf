# $Id: Makefile 1476 2011-04-01 09:43:43Z davidpiegdon $
# libalf dispatcher testsuite Makefile

.PHONY: all clean

CPPFLAGS+=-Wall -ggdb3 -D__cplusplus -fno-stack-protector
LDFLAGS+=

all:	protocol

protocol: protocol.o
	${CXX} protocol.o ${LDFLAGS} -o protocol

clean:
	-rm -f protocol *.o

