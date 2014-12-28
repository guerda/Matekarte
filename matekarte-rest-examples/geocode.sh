#!/bin/sh
curl http://www.matekarte.de/geocode?q=Berlin | python -m json.tool
