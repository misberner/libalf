# $Id: Makefile 1473 2011-04-01 00:09:31Z davidpiegdon $
# libmVCA Makefile

.PHONY: install uninstall

include ../config.mk

install:
	@echo
	@echo installing libmVCA headers to ${INCLUDEDIR} ...
	@echo
	-install -v -m 755 -d ${INCLUDEDIR}/libmVCA
	rm -f ${INCLUDEDIR}/libmVCA/*
	install -v -m 644 libmVCA/*.h ${INCLUDEDIR}/libmVCA

uninstall:
	@echo
	@echo removing libmVCA headers from ${INCLUDEDIR} ...
	@echo
	rm -Rf ${INCLUDEDIR}/libmVCA

