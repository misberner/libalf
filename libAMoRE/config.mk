# $Id: config.mk 1483 2011-04-08 16:11:47Z davidpiegdon $
# vim:syntax=make
#
# directory definitions for Makefile

PREFIX?=/usr/local
LIBDIR?=${PREFIX}/lib
INCLUDEDIR?=${PREFIX}/include
BINDIR?=${PREFIX}/bin
SHAREDIR?=${PREFIX}/share
DOCDIR?=${SHAREDIR}/doc/libAMoRE

ifndef OS
	OS=$(shell uname -s)
endif

ifeq (${OS}, Windows_NT) # Windows
	LIBEXT=dll
	LDFLAGS+=-lws2_32
else ifeq (${OS}, Darwin) # Mac OS
	LIBEXT=dylib
else ifeq (${OS}, Linux) # Linux
	LIBEXT=so
else
	$(error Unsupported operating system ${OS})
endif

# Skip lib prefix on Windows
ifeq (${OS}, Windows_NT)
	LIBPREFIX=
else
	LIBPREFIX=lib
endif

# Use ginstall in Mac OS X
ifeq (${OS}, Darwin)
	INSTALL?=ginstall
else
	INSTALL?=install
endif

# ldconfig needs to be run on Linux
ifeq (${OS}, Linux)
	RUN_LDCONFIG=test `id -u` -eq 0 && /sbin/ldconfig
else
	RUN_LDCONFIG=
endif
