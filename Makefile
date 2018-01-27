JFLAGS = 
JC = /usr/lib/jvm/default-java/bin/javac
MYPATH = ./multimedia/
CLASSES = \
		MapFrame.java \
		Map.java \
		Main.java 

all: compile

compile:
		$(JC) $(JFLAGS) $(addprefix $(MYPATH), $(CLASSES))

.PHONY: clean

clean:
		rm -f $(addprefix $(MYPATH), *.class)