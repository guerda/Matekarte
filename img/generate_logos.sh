#!/bin/bash
# Ratios: 2   :3   :4    :6     :8 
#         mdpi:hdpi:xhdpi:xxhdpi:xxxhdpi 

inkscape --export-png=../res/drawable-mdpi/ic_launcher.png --export-width=48 --export-height=48 matekarte-logo.svg 
inkscape --export-png=../res/drawable-hdpi/ic_launcher.png --export-width=72 --export-height=72 matekarte-logo.svg 
inkscape --export-png=../res/drawable-xhdpi/ic_launcher.png --export-width=96 --export-height=96 matekarte-logo.svg 
inkscape --export-png=../res/drawable-xxhdpi/ic_launcher.png --export-width=144 --export-height=144 matekarte-logo.svg 
inkscape --export-png=../res/drawable-xxxhdpi/ic_launcher.png --export-width=192 --export-height=192 matekarte-logo.svg 

ANDROID_RES=$ANDROID_HOME/platforms/android-21/data/res/

FORMATS=(drawable-mdpi drawable-hdpi drawable-xhdpi drawable-xxhdpi drawable-xxxhdpi)
FILES=(ic_menu_mylocation.png ic_popup_sync_1.png ic_popup_sync_2.png ic_popup_sync_3.png ic_popup_sync_4.png ic_popup_sync_5.png ic_popup_sync_6.png)
for FORMAT in ${FORMATS[@]}; do
  for FILE in ${FILES[@]}; do
     cp $ANDROID_RES$FORMAT/$FILE ../res/$FORMAT
  done
done

