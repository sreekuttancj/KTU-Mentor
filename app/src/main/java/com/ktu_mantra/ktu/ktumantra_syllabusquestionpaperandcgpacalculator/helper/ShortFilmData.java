package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;


public class ShortFilmData {

    String name;
    String duration;
    String youtube;
    String college;
    String pos;

    public ShortFilmData(){

    }

    public ShortFilmData(String name, String duration, String youtubeLink, String collegeName) {
        this.name = name;
        this.duration = duration;
        this.youtube = youtubeLink;
        this.college= collegeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
