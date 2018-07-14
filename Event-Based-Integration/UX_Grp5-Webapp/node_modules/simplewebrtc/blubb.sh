#!/bin/bash
git checkout latest-v2.js; git checkout master; node build; cp latest-v2.js /tmp; git checkout latest-v2.js simplewebrtc.bundle.js; git checkout gh-pages; cp /tmp/latest-v2.js .; python -m SimpleHTTPServer
