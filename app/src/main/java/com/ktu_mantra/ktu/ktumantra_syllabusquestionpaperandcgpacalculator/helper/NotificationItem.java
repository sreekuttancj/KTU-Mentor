package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;


public class NotificationItem {

    private String content;
    private String timestamp;
    private String heading;
    private String link;
    private String pos;

    public NotificationItem(){

    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
