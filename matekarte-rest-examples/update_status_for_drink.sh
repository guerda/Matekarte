#!/bin/sh
curl --data "status[drink_id]=50fb3d16ce007c40fc00080d&status[dealer_id]=52cd8b5e7a58b40eae004e40&status[status]=1" https://www.matekarte.de/api/v2/statuses | python -m json.tool


