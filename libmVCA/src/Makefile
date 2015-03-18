# $Id: Makefile 1491 2011-04-08 17:05:08Z davidpiegdon $
# libmVCA Makefile

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

TARGET=${LIBPREFIX}mVCA.${LIBEXT}

INSTALL_SHARED_NAME=${TARGET}${LIBVERSIONTAG}
INSTALL_STATIC_NAME=libmVCA.a${LIBVERSIONTAG}


CPPFLAGS+=-Wall -I../include/ -I${INCLUDEDIR} -shared -fpic
CPPFLAGS+=-Wextra
CPPFLAGS+=-D__cplusplus -DVERSION="\"${VERSION}\""
# for ubuntu, try disabling stack-smashing due to linker failures:
#CPPFLAGS+=-fno-stack-protector
LDFLAGS+=-shared -L${LIBDIR}

OBJECTS=pushdown.o transition_function.o mVCA.o deterministic_mVCA.o nondeterministic_mVCA.o p-automaton.o

all:	${TARGET}

${TARGET}: ${OBJECTS}
	${CXX} $(OBJECTS) $(LDFLAGS) -o $@ 

libmVCA.a: ${OBJECTS}
	${AR} rcs $@ $(OBJECTS)

clean:
	-rm -f *.o ${TARGET} libmVCA.a

install: ${TARGET} libmVCA.a
	@echo
	@echo installing libmVCA library to ${LIBDIR} ...
	@echo
	-${INSTALL} -v -m 755 -d ${LIBDIR}
	${INSTALL} -T -v -m 755 ${TARGET} ${LIBDIR}/${INSTALL_SHARED_NAME}
	${INSTALL} -T -v -m 755 libmVCA.a  ${LIBDIR}/${INSTALL_STATIC_NAME}
	# symlinks
	-rm -f ${LIBDIR}/${TARGET}
	ln -s ${LIBDIR}/${INSTALL_SHARED_NAME} ${LIBDIR}/${TARGET}
	-rm -f ${LIBDIR}/libmVCA.a
	ln -s ${LIBDIR}/${INSTALL_STATIC_NAME}  ${LIBDIR}/libmVCA.a
	-${RUN_LDCONFIG}

uninstall:
	@echo
	@echo removing libmVCA library from ${LIBDIR} ...
	@echo
	rm -f ${LIBDIR}/${INSTALL_SHARED_NAME}
	rm -f ${LIBDIR}/${INSTALL_STATIC_NAME}
	rm -f ${LIBDIR}/${TARGET}
	rm -f ${LIBDIR}/libmVCA.a
	-${RUN_LDCONFIG}

