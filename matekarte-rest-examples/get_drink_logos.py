#!/usr/bin/python
import simplejson
import urllib2

req = urllib2.Request("https://www.matekarte.de/api/v2/drinks")
opener = urllib2.build_opener()
drinks_json = opener.open(req)

drinks = simplejson.load(drinks_json)
for drink in drinks['drinks']:
  url = "https://www.matekarte.de%s" % (drink['image'])
  print url
  req = urllib2.Request(url)
  response = opener.open(req)
  contentDisposition = response.info().getheader('Content-Disposition')
  extension =  contentDisposition[10+contentDisposition[10:].index('.'):-1] 
  f = open("%s%s" % (drink['id'], extension), "wb")
  f.write(response.read())
  f.close()
