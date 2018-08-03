#!/usr/bin/env python

import argparse
import sys
from subprocess import call

def generateVersionString(maj, min, patch, isSnapshot=False):
    versionTrail = ""
    if isSnapshot:
        versionTrail = "-SNAPSHOT"
    return str(maj) + "." + str(min) + "." + str(patch) + versionTrail

# open the version file if present (and create one if not)
version = "0.0.0"
try:
    f = open("VERSION")
    version = f.readline().replace("\n", "")
    f.close()
except IOError:
    print("VERSION file not found. Creating one with base version " + version)
    f = open("VERSION", "w")
    f.write(version + "\n")
    f.close

print("Current Version: " + version)
versionParts = version.split(".")
versionMajor = 0
versionMinor = 0
versionPatch = 0

if len(versionParts) > 0:
    versionMajor = int(versionParts[0])
if len(versionParts) > 1:
    versionMinor = int(versionParts[1])
if len(versionParts) > 2:
    versionPatch = int(versionParts[2])

# The default publishing mode
publishMode = "minor"

parser = argparse.ArgumentParser()
parser.add_argument("destination", help="Output folder to publish to")
parser.add_argument("--major", help="Publish a new Major version (i.e. 1.0.0 -> 2.0.0)", action="store_true")
parser.add_argument("--minor", help="Publish a new Minor version (i.e. 1.0.0 -> 1.1.0)", action="store_true")
parser.add_argument("--patch", help="Publish a new Patch version (i.e. 1.0.0 -> 1.0.1)", action="store_true")
parser.add_argument("--snapshot", help="Publish a new Snapshot (i.e. 1.0.0 -> 1.0.0-SNAPSHOT", action="store_true")
parser.add_argument("--no-commit", help="Do not make a new commit with updated version information", action="store_true")
args = parser.parse_args()

# major beats minor beats patch (i.e. if more than one of --major/--minor/--patch is specified
# we use the highest ranking one)

isSnapshot = False
if args.major:
    publishMode = "major"
    versionMajor += 1
elif args.minor:
    publishMode = "minor"
    versionMinor += 1
elif args.patch:
    publishMode = "patch"
    versionPatch += 1
elif args.snapshot:
    publishMode = "snapshot"
    isSnapshot = True
else:
    publishMode = "minor"
    versionMinor += 1

newVersionStr = generateVersionString(versionMajor, versionMinor, versionPatch, isSnapshot)
print ("Updating version from " + version + " -> " + newVersionStr)

shouldContinue = raw_input("Continue? (Y/n): ")
if shouldContinue == "n":
    sys.exit()
elif not(shouldContinue == "" or shouldContinue.lower() == "y"):
    print("Invalid input. Enter Y or N only")
    sys.exit()

#Write the file (if this isn't a snapshot)
if not isSnapshot:
    with open("VERSION", "w") as f:
        f.write(newVersionStr)

    if args.no_commit:
        print "NOT committing new version"
    else:
        commitMsg = "[PUBLISH] Updating version to " + newVersionStr
        retCode = call(["git", "add", "VERSION"])
        if retCode == 0:
            retCode = call(["git", "commit", "-m", commitMsg])
            if retCode == 0:
                print("Successfully committed new version")
            else:
                print("Could not commit VERSION")
        else:
            print("Could not add VERSION to index")
        
# Now we can publish to maven
print("Publishing to " + args.destination)
call(["./gradlew", "-Pmaven_repo="+args.destination, "-Pmaven_version="+newVersionStr, "publish"])