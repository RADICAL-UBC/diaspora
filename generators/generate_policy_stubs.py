#!/usr/bin/python

import os, sys
import subprocess

if __name__ == '__main__':

    diaspora_home = os.path.normpath(os.path.join(os.path.realpath(__file__), '../..'))
   
    inFolder = diaspora_home + '/diaspora/target/classes/diaspora/dms/'
    package = 'diaspora.dms'
    outFolder = diaspora_home + '/diaspora/app/src/main/java/diaspora/dms/stubs/'
    
    cp_diaspora = diaspora_home + '/diaspora/target/diaspora-1.0-SNAPSHOT.jar'
 
    cmd = ['java', '-cp', cp_diaspora, 'diaspora.compiler.StubGenerator', inFolder, package, outFolder]

    print cmd

    p1 = subprocess.Popen(cmd)
    p1.wait()
    print "Done!"
