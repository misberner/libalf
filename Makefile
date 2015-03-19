
-include config.mk

INSTALL_DIR?=$(shell ${READLINK} -f ./install)
PREFIX?=/usr/local

CPPFLAGS += -I${PREFIX}/include
LDFLAGS += -L${PREFIX}/lib

export CPPFLAGS
export LDFLAGS


all: libalf libalf_interfaces examples finite-automata-tool liblangen libmVCA libAMoRE libAMoRE++

libalf: libAMoRE++ liblangen libmVCA
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

libalf_interfaces: libalf
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

libAMoRE:
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

libAMoRE++: libAMoRE
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

liblangen:
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

examples: libalf
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@

libmVCA: libAMoRE++
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

finite-automata-tool: liblangen libalf libAMoRE++ libAMoRE
	PREFIX=${INSTALL_DIR} ${MAKE} -C $@ install

%-clean:
	${MAKE} -C $(patsubst %-clean,%,$@) clean

clean:
	${MAKE} -C libAMoRE clean
	${MAKE} -C libAMoRE++ clean
	${MAKE} -C libmVCA clean
	${MAKE} -C liblangen clean
	${MAKE} -C libalf clean
	${MAKE} -C libalf_interfaces clean
	${MAKE} -C finite-automata-tool clean
	${MAKE} -C examples clean

uninstall:
	PREFIX=${INSTALL_DIR} ${MAKE} -C libAMoRE uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C libAMoRE++ uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C libmVCA uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C liblangen uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C libalf uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C libalf_interfaces uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C finite-automata-tool uninstall
	PREFIX=${INSTALL_DIR} ${MAKE} -C examples uninstall

.PHONY: all libalf libalf_interfaces libAMoRE libAMoRE++ examples liblangen libmVCA clean
