.PHONY: all clean

PREFIX ?= /usr/local

CPPFLAGS+=-I${PREFIX}/include -Wall

LDFLAGS+=-L${PREFIX}/lib

# Add winsock2 library if compiled for Windows.
ifeq (${OS}, Windows_NT)
	LDFLAGS += -lws2_32
endif

all: online offline

clean:
	-rm -f online offline

online: online.cpp
	g++ ${CPPFLAGS} online.cpp ${LDFLAGS} -o online -lalf

offline: offline.cpp
	g++ ${CPPFLAGS} offline.cpp ${LDFLAGS} -o offline -lalf