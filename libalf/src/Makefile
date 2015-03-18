# $Id: Makefile 1482 2011-04-08 15:14:13Z davidpiegdon $
# libalf Makefile

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

CPPFLAGS+=-Wall -I../include/ -I${INCLUDEDIR} -shared -fpic
CPPFLAGS+=-Wextra
CPPFLAGS+=-D__cplusplus -DVERSION="\"${VERSION}\""
# for ubuntu, try disabling stack-smashing due to linker failures:
#CPPFLAGS+=-fno-stack-protector
LDFLAGS+=-shared -L${LIBDIR}

OBJECTS=alf.o alphabet.o conjecture.o basic_string.o learning_algorithm.o logger.o minisat_solver.o normalizer_msc.o statistics.o

TARGET=libalf.${LIBEXT}

INSTALL_SHARED_NAME=${TARGET}${LIBVERSIONTAG}
INSTALL_STATIC_NAME=libalf.a${LIBVERSIONTAG}

all:	${TARGET}

${TARGET}: ${OBJECTS}
	${CXX} $(OBJECTS) $(LDFLAGS) -o $@ 

libalf.a: ${OBJECTS}
	${AR} rcs $@ $(OBJECTS)

clean:
	-rm -f *.o ${TARGET} libalf.a

install: ${TARGET} libalf.a
	@echo
	@echo installing libalf library to ${LIBDIR} ...
	@echo
	-${INSTALL} -v -m 755 -d ${LIBDIR}
	${INSTALL} -T -v -m 755 ${TARGET} ${LIBDIR}/${INSTALL_SHARED_NAME}
	${INSTALL} -T -v -m 755 libalf.a  ${LIBDIR}/${INSTALL_STATIC_NAME}
	# symlinks
	-rm -f ${LIBDIR}/${TARGET}
	ln -s ${LIBDIR}/${INSTALL_SHARED_NAME} ${LIBDIR}/${TARGET}
	-rm -f ${LIBDIR}/libalf.a
	ln -s ${LIBDIR}/${INSTALL_STATIC_NAME}  ${LIBDIR}/libalf.a
	-${RUN_LDCONFIG}

uninstall:
	@echo
	@echo removing libalf library from ${LIBDIR} ...
	@echo
	rm -f ${LIBDIR}/${INSTALL_SHARED_NAME}
	rm -f ${LIBDIR}/${INSTALL_STATIC_NAME}
	rm -f ${LIBDIR}/${TARGET}
	rm -f ${LIBDIR}/libalf.a
	-${RUN_LDCONFIG}
