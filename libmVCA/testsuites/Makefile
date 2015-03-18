# $Id: Makefile 1235 2010-03-04 15:28:52Z davidpiegdon $
# libmVCA Makefile

.PHONY: all clean behaviour_graph contains lang_intersect

all: behaviour_graph contains lang_intersect

behaviour_graph:
	make -C behaviour_graph

contains:
	make -C contains

lang_intersect:
	make -C lang_intersect

clean:
	make -C behaviour_graph clean
	make -C contains clean
	make -C lang_intersect clean

