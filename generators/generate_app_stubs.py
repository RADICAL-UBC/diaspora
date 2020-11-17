#!/usr/bin/python

import os, sys
import subprocess
from apps.todo import *
#from apps.minnietwitter import *

if __name__ == '__main__':

    diaspora_home = os.path.normpath(os.path.join(os.path.realpath(__file__), '../..'))

    cp_app =  diaspora_home + '/example_apps/' + app_name + '/target/' + app_name.lower() + '-1.0-SNAPSHOT.jar'
    cp_diaspora = diaspora_home + '/diaspora/target/diaspora-1.0-SNAPSHOT.jar'
    
    cmd = ['java', '-cp',  cp_app + ':' + cp_diaspora, 'diaspora.compiler.StubGenerator', inFolder, package, outFolder]
    p1 = subprocess.Popen(cmd)
    p1.wait()

    print "Done!"
