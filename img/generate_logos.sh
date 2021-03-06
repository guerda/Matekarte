#!/bin/bash
# Ratios: 2   :3   :4    :6     :8 
#         mdpi:hdpi:xhdpi:xxhdpi:xxxhdpi 

LOGOS=(matekarte-logo.svg)

for LOGO in ${LOGOS[@]}; do
  inkscape --export-png=../res/drawable-mdpi/ic_launcher.png --export-width=48 --export-height=48 matekarte-logo.svg 
  inkscape --export-png=../res/drawable-hdpi/ic_launcher.png --export-width=72 --export-height=72 matekarte-logo.svg 
  inkscape --export-png=../res/drawable-xhdpi/ic_launcher.png --export-width=96 --export-height=96 matekarte-logo.svg 
  inkscape --export-png=../res/drawable-xxhdpi/ic_launcher.png --export-width=144 --export-height=144 matekarte-logo.svg 
  inkscape --export-png=../res/drawable-xxxhdpi/ic_launcher.png --export-width=192 --export-height=192 matekarte-logo.svg 
done

inkscape --export-png=../res/drawable-mdpi/drink_no_logo.png --export-width=48 --export-height=48 drink_no_logo.svg 
  inkscape --export-png=../res/drawable-hdpi/drink_no_logo.png --export-width=72 --export-height=72 drink_no_logo.svg 
  inkscape --export-png=../res/drawable-xhdpi/drink_no_logo.png --export-width=96 --export-height=96 drink_no_logo.svg 
  inkscape --export-png=../res/drawable-xxhdpi/drink_no_logo.png --export-width=144 --export-height=144 drink_no_logo.svg 
  inkscape --export-png=../res/drawable-xxxhdpi/drink_no_logo.png --export-width=192 --export-height=192 drink_no_logo.svg 

ANDROID_RES=$ANDROID_HOME/platforms/android-21/data/res/

FORMATS=(drawable-mdpi drawable-hdpi drawable-xhdpi drawable-xxhdpi drawable-xxxhdpi)
FILES=(ic_menu_mylocation.png ic_popup_sync_1.png ic_popup_sync_2.png ic_popup_sync_3.png ic_popup_sync_4.png ic_popup_sync_5.png ic_popup_sync_6.png)
for FORMAT in ${FORMATS[@]}; do
  for FILE in ${FILES[@]}; do
     cp $ANDROID_RES$FORMAT/$FILE ../res/$FORMAT
  done
done

