# $Id: Makefile 1491 2011-04-08 17:05:08Z davidpiegdon $
# liblangen (LANguageGENerator) Makefile

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

TARGET=${LIBPREFIX}langen.${LIBEXT}

INSTALL_SHARED_NAME=${TARGET}${LIBVERSIONTAG}
INSTALL_STATIC_NAME=liblangen.a${LIBVERSIONTAG}

CPPFLAGS+=-Wall -I../include/ -I${INCLUDEDIR} -shared -fpic
CPPFLAGS+=-Wextra
CPPFLAGS+=-D__cplusplus -DVERSION="\"${VERSION}\""
# for ubuntu, try disabling stack-smashing due to linker failures:
#CPPFLAGS+=-fno-stack-protector
LDFLAGS+=-shared -L${LIBDIR} -lgmpxx -lgmp

OBJECTS=dfa_enumerator.o dfa_randomgenerator.o nfa_randomgenerator.o prng.o regex_randomgenerator.o langen.o

all:	${TARGET} liblangen.a

${TARGET}: ${OBJECTS}
	${CXX} $(OBJECTS) $(LDFLAGS) -o $@

liblangen.a: ${OBJECTS}
	${AR} rcs $@ $(OBJECTS)

clean:
	-rm -f *.o ${TARGET} liblangen.a

install: ${TARGET} liblangen.a
	@echo
	@echo installing liblangen library to ${LIBDIR} ...
	@echo
	-${INSTALL} -v -m 755 -d ${LIBDIR}
	${INSTALL} -T -v -m 755 ${TARGET} ${LIBDIR}/${INSTALL_SHARED_NAME}
	${INSTALL} -T -v -m 755 liblangen.a  ${LIBDIR}/${INSTALL_STATIC_NAME}
	# symlinks
	-rm -f ${LIBDIR}/${TARGET}
	ln -s ${LIBDIR}/${INSTALL_SHARED_NAME} ${LIBDIR}/${TARGET}
	-rm -f ${LIBDIR}/liblangen.a
	ln -s ${LIBDIR}/${INSTALL_STATIC_NAME}  ${LIBDIR}/liblangen.a
	-${RUN_LDCONFIG}

uninstall:
	@echo
	@echo removing liblangen library from ${LIBDIR} ...
	@echo
	rm -f ${LIBDIR}/${INSTALL_SHARED_NAME}
	rm -f ${LIBDIR}/${INSTALL_STATIC_NAME}
	rm -f ${LIBDIR}/${TARGET}
	rm -f ${LIBDIR}/liblangen.a
	-${RUN_LDCONFIG}

