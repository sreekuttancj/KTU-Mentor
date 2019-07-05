package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list;


import android.graphics.Color;

public class ColorCollection  {

    private static final int[] colorList;
    private static final int[] colorListFont;
    private static final String[] colorListHex;
    private static final String[] colorListFontHex;


    public static final int defaultColor;
    public static final int defaultStrokeColor;
    public static final int onClickAlpha;
    public static final int onClickDefault;
    public static final int defaultSubDialogColor;
    public static final int noSubject;
    public static final int noSubjectFont;




    static {

        defaultSubDialogColor = Color.argb(255, 221, 221, 221);
        defaultColor = Color.argb(0, 0, 0, 0);
        defaultStrokeColor = Color.argb(220, 255, 255, 255);
        onClickAlpha= Color.argb(50, 0, 0, 0);
        onClickDefault= Color.argb(100, 255, 255, 255);
        noSubject = Color.argb(255, 85, 85, 85);
        noSubjectFont = Color.argb(255, 224, 224, 224);


        colorList = new int[]{
                Color.rgb(194, 222, 242), Color.rgb(149, 203, 229), Color.rgb(129, 159, 210), Color.rgb(79, 150, 208),
                Color.rgb(20, 121, 204), Color.rgb(17, 99, 166), Color.rgb(255, 204, 221), Color.rgb(242, 157, 200),
                Color.rgb(221, 153, 200), Color.rgb(184, 152, 217), Color.rgb(134, 115, 191),
                Color.rgb(117, 74, 148), Color.rgb(247, 242, 148), Color.rgb(243, 210, 48), Color.rgb(248, 156, 56),
                Color.rgb(255, 115, 115), Color.rgb(240, 72, 83), Color.rgb(179, 18, 18), Color.rgb(254, 187, 127),
                Color.rgb(198, 143, 121), Color.rgb(163, 95, 82), Color.rgb(153, 64, 46), Color.rgb(204, 200, 82),
                Color.rgb(204, 122, 82), Color.rgb(184, 230, 184), Color.rgb(122, 204, 122), Color.rgb(83, 166, 83),
                Color.rgb(51, 128, 51), Color.rgb(51, 128, 115), Color.rgb(89, 179, 164), Color.rgb(217, 217, 217),
                Color.rgb(176, 176, 176)};

        colorListFont= new int[]{
                Color.rgb(68, 68, 68), Color.rgb(0, 0, 0), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255),
                Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(68, 68, 68), Color.rgb(0, 0, 0),
                Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255),
                Color.rgb(68, 68, 68), Color.rgb(0, 0, 0), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255),
                Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(0, 0, 0), Color.rgb(255, 255, 255),
                Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255),
                Color.rgb(68, 68, 68), Color.rgb(0, 0, 0), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255),
                Color.rgb(255, 255, 255), Color.rgb(0, 0, 0), Color.rgb(0, 0, 0), Color.rgb(0, 0, 0)
        };

        colorListHex = new String[]{"#C2DEF2", "#95CBE5", "#819FD2", "#4F96D0", "#1479CC", "#1163A6", "#FFCCDD", "#F29DCB",
                "#DD99E1", "#B898D9", "#8673BF", "#754A94", "#F7F294", "#F3D230", "#F89C38", "#FF7373", "#F04853", "#B31212",
                "#FEBB7F", "#C68F79", "#A35F52", "#99402E", "#CC6652", "#CC7A52", "#B8E6B8", "#7ACC7A", "#53A653", "#338033",
                "#338073", "#59B3A4", "#D9D9D9", "#B0B0B0"};

        colorListFontHex = new String[]{"#444444", "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#444444", "#000000",
                "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#444444", "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF",
                "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#444444", "#000000", "#FFFFFF", "#FFFFFF",
                "#FFFFFF", "#000000", "#000000", "#000000"};

    }

    public static int colorDailog(int  position){
        try {
            return colorList[position];
        } catch (Exception e) {
            return colorList[0];
        }

    }

    public static String colorDailogHex(int position){
        try {
            return colorListHex[position];
        } catch (Exception e) {
            return colorListHex[0];
        }

    }

    public static String colorDialogFontHex(int position){

        try {
            return colorListFontHex[position];
        } catch (Exception e) {
            return colorListFontHex[0];
        }

    }

    public static int colorDialogFont(int position){

        try {
            int i2 = colorListFont[position];
            if (i2 == -16777216) {
                return ColorCollection.checkAlpha(i2, 85);
            }
            return ColorCollection.checkAlpha(i2, 136);
        } catch (Exception e) {
            return ColorCollection.checkAlpha(colorListFont[0], 136);
        }

    }

    public static int colorAlpha(int position){
        try {
            int i2 = colorListFont[position];
            if (i2 == -16777216) {
                return ColorCollection.checkAlpha(i2, 170);
            }
            return ColorCollection.checkAlpha(i2, 245);
        } catch (Exception e) {
            return ColorCollection.checkAlpha(colorListFont[0], 229);
        }

    }

    public static int parseColorHex(String str) {
        try {
            return Color.parseColor(str);
        } catch (Exception e) {
            return colorList[0];
        }
    }


    public static int parseFontColorHex(String str){

        try {
            int parseColor = Color.parseColor(str);
            if (parseColor == -16777216) {
                return ColorCollection.checkAlpha(parseColor, 170);
            }

            return ColorCollection.checkAlpha(parseColor, 245);
        } catch (Exception e) {
            return ColorCollection.checkAlpha(colorListFont[0], 229);
        }

    }



    public static int checkAlpha(int i, int i2) {
        return Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
    }



    public static int getSubColorPosition(int i) {
        for (int i2 = 0; i2 < colorList.length; i2++) {
            if (i == colorList[i2]) {
                return i2;
            }
        }
        return -1;
    }

    public static int defaultFontColor(int i) {
        if (i == -16777216) {
            return ColorCollection.checkAlpha(i, 85);
        }
        return ColorCollection.checkAlpha(i, 136);
    }


}
