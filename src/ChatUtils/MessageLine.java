package ChatUtils;

import org.joda.time.DateTime;

public class MessageLine{
    int day;
    int month;
    int year;
    int hour;
    int minutes;
    int sec;
    String author;
    String content;
    DateTime date;

    public MessageLine(int day, int month, int year, int hour, int minutes, int sec, String author, String content){
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
        this.sec =sec;
        this.author = author;
        this.content = content;

//        Set date
        this.date = new DateTime(year, month, day, hour - 1, minutes, sec);
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getMonth() {
        return month;
    }

    public int getSec() {
        return sec;
    }

    public int getYear() {
        return year;
    }

    public DateTime getDate(){
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
