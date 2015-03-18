# $Id: config.mk 1482 2011-04-08 15:14:13Z davidpiegdon $
# vim:syntax=make
#
# directory definitions for Makefile

CFLAGS += -O3
CPPFLAGS += -O3

PREFIX?=/usr/local
LIBDIR?=${PREFIX}/lib
INCLUDEDIR?=${PREFIX}/include
BINDIR?=${PREFIX}/bin
SHAREDIR?=${PREFIX}/share
JARDIR?=${SHAREDIR}/libalf
DOCDIR?=${SHAREDIR}/doc/libalf

