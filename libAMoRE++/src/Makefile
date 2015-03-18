# $Id: Makefile 1482 2011-04-08 15:14:13Z davidpiegdon $
# libAMoRE++ Makefile

.PHONY: all clean install uninstall

include ../config.mk

VERSIONTAG:=$(shell cat "../version")
ifeq (${VERSIONTAG},)
# subversion branch
LOCALCHANGES:=$(shell test "`svn status | wc -c`" != "0" && echo "-LocalChanges")
SVNREVISION:=$(shell LC_ALL=POSIX svn info | awk '/^Revision:/ {print $$2}')
VERSION?=svn-r${SVNREVISION}${LOCALCHANGES}
LIBVERSIONTAG?=.svn
else
# release branch
VERSION?=v${VERSIONTAG}
LIBVERSIONTAG?=.${VERSIONTAG}
endif

TARGET=${LIBPREFIX}AMoRE++.${LIBEXT}

INSTALL_SHARED_NAME=$(TARGET)${LIBVERSIONTAG}
INSTALL_STATIC_NAME=libAMoRE++.a${LIBVERSIONTAG}

CPPFLAGS+=-Wall -I../include/ -I${INCLUDEDIR} -shared -fpic
CPPFLAGS+=-Wextra
CPPFLAGS+=-D__cplusplus -DVERSION="\"${VERSION}\""
# for ubuntu, try disabling stack-smashing due to linker failures:
CPPFLAGS+=-fno-stack-protector
#CPPFLAGS+=-DANTICHAIN_DEBUG
LDFLAGS+=-shared -L${LIBDIR} -lAMoRE


OBJECTS=amore.o finite_automaton.o nondeterministic_finite_automaton.o deterministic_finite_automaton.o

all:	${TARGET}

${TARGET}: ${OBJECTS}
	${CXX} $(OBJECTS) $(LDFLAGS) -o $@

	
libAMoRE++.a: ${OBJECTS}
	${AR} crs $@ $?

clean:
	-rm -f *.o ${TARGET} libAMoRE++.a

install: ${TARGET} libAMoRE++.a
	@echo
	@echo installing ${TARGET} to ${LIBDIR} ...
	@echo
	-${INSTALL} -v -m 755 -d ${LIBDIR}
	${INSTALL} -T -v -m 755 ${TARGET} ${LIBDIR}/${INSTALL_SHARED_NAME}
	${INSTALL} -T -v -m 755 libAMoRE++.a  ${LIBDIR}/${INSTALL_STATIC_NAME}
	# symlinks
	-rm -f ${LIBDIR}/${TARGET}
	ln -s ${LIBDIR}/${INSTALL_SHARED_NAME} ${LIBDIR}/${TARGET}
	-rm -f ${LIBDIR}/libAMoRE++.a
	ln -s ${LIBDIR}/${INSTALL_STATIC_NAME}  ${LIBDIR}/libAMoRE++.a
	-${RUN_LDCONFIG}

uninstall:
	@echo
	@echo removing libAMoRE++ library from ${LIBDIR} ...
	@echo
	rm -f ${LIBDIR}/${INSTALL_SHARED_NAME}
	rm -f ${LIBDIR}/${INSTALL_STATIC_NAME}
	rm -f ${LIBDIR}/${TARGET}
	rm -f ${LIBDIR}/libAMoRE++.a
	-${RUN_LDCONFIG}

finite_automaton.cpp: set.h

