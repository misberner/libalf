# $Id: Makefile 1495 2011-04-11 11:07:52Z neider $
# libalf JNI Makefile

.PHONY: all test_java_dir clean install windows-deps

include ../../config.mk

VERSIONTAG:=$(shell cat "../../version")
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

JAVA_INCLUDE?=${JAVA_HOME}/include

# Choose OS
ifeq (${OS}, Windows_NT)
  CPPFLAGS += -I${JAVA_INCLUDE}/win32
else ifeq (${OS}, Linux)
  CPPFLAGS += -I${JAVA_INCLUDE}/linux
else ifeq (${OS}, Darwin)
  CPPFLAGS += -I${JAVA_INCLUDE}/darwin
endif

TARGET=${LIBPREFIX}jalf.${LIBEXT}
TARGET_DYNAMIC=${LIBPREFIX}jalf-dynamic.${LIBEXT}

INSTALL_SHARED_NAME=${TARGET}${LIBVERSIONTAG}

CPPFLAGS += -I../include -I${INCLUDEDIR} -I${JAVA_INCLUDE}
CPPFLAGS += -shared -fpic
CPPFLAGS += -O3

LDFLAGS += -L${LIBDIR}
LDFLAGS += -shared

OBJECTS =	jni_tools.o					\
		jni_object.o 					\
		jni_knowledgebase.o				\
		jni_buffered_logger.o				\
		jni_normalizer.o				\
		jni_learning_algorithm.o			\
		jni_algorithm_angluin.o				\
		jni_algorithm_angluin_column.o			\
		jni_algorithm_nlstar.o				\
		jni_algorithm_kearns_vazirani.o			\
		jni_algorithm_rivest_schapire.o			\
		jni_algorithm_rpni.o				\
		jni_algorithm_biermann_original.o		\
		jni_algorithm_biermann_minisat.o		\
		jni_algorithm_delete2.o


all:	${TARGET}

dynamic: ${TARGET_DYNAMIC}

test_java_includes:
	@echo
	@echo "Testing if JAVA include directory (env JAVA_INCLUDE) is set correctly...."
	echo ${JAVA_HOME}
	test -d ${JAVA_INCLUDE}
	@echo

clean:
	-rm -f *.o ${TARGET} ${TARGET_DYNAMIC}

install: all
	@echo
	@echo installing ${TARGET} to ${LIBDIR} ...
	@echo
	-${INSTALL} -v -m 755 -d ${LIBDIR}
	${INSTALL} -T -v -m 755 ${TARGET} ${LIBDIR}/${INSTALL_SHARED_NAME}
	# symlinks
	-rm -f ${LIBDIR}/${TARGET}
	ln -s ${LIBDIR}/${INSTALL_SHARED_NAME} ${LIBDIR}/${TARGET}
	-${RUN_LDCONFIG}

uninstall:
	@echo
	@echo removing ${TARGET} from ${LIBDIR} ...
	@echo
	rm -f ${LIBDIR}/${INSTALL_SHARED_NAME}
	rm -f ${LIBDIR}/${TARGET}
	-${RUN_LDCONFIG}


# Linux targets
${TARGET}: test_java_includes ${OBJECTS}
	${CXX} ${LDFLAGS} -Xlinker ${OBJECTS} ${LIBDIR}/libalf.a -o $@

${TARGET_DYNAMIC}: test_java_includes ${OBJECTS}
	${CXX} ${LDFLAGS} -Xlinker ${OBJECTS} -lalf -o $@


