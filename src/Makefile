JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
    $(JC) $(JFLAGs) $*.java

CLASSES = \
        Command.java \
        CommandReader.java \
        Job.java \
        RedBlackNode.java \
        MinHeap.java \
        RedBlackTree.java \
        Scheduler.java \
        jobscheduler.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
