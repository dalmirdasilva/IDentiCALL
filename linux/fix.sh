#!/bin/bash

sudo modprobe -r xhci_hcd
sudo modprobe xhci_hcd

java -jar -Djava.library.path=/usr/lib/jni "/home/dalmir/NetBeansProjects/IDentiCALL/dist/IDentiCALL.jar"
