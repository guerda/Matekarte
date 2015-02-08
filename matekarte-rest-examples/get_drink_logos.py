#!/usr/bin/python
import simplejson
import urllib.request, urllib.error, urllib.parse

req = urllib.request.Request("https://www.matekarte.de/api/v2/drinks")
opener = urllib.request.build_opener()
drinks_json = opener.open(req)

drinks = simplejson.load(drinks_json)
for drink in drinks['drinks']:
  url = "https://www.matekarte.de%s" % (drink['image'])
  print(("drinkMap.put(\"%s\",\"%s\");" % (drink['id'], drink['name'])))
  req = urllib.request.Request(url)
  response = opener.open(req)
  contentDisposition = response.info()['Content-Disposition']
  extension =  contentDisposition[10+contentDisposition[10:].index('.'):-1] 
  f = open("%s%s" % (drink['id'], extension), "wb")
  f.write(response.read())
  f.close()
