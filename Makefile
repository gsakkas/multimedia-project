JFLAGS = 
JC = /usr/lib/jvm/default-java/bin/javac
MYPATH = ./multimedia/
CLASSES = \
		MenuBarPanel.java \
		TextAreaPanel.java \
		InfoBarPanel.java \
		Airplanes.java \
		Flights.java \
		Airports.java \
		MapPanel.java \
		Map.java \
		MainWindow.java \
		Main.java 

all: compile

make_exec:
	chmod +x run.bash

compile:
	$(JC) $(JFLAGS) $(addprefix $(MYPATH), $(CLASSES))

.PHONY: clean

clean:
	rm -f $(addprefix $(MYPATH), *.class)