package ChatUtils;

public class MessageLine{
    int day;
    int month;
    int year;
    int hour;
    int minutes;
    int sec;
    String author;
    String content;

    public MessageLine(int day, int month, int year, int hour, int minutes, int sec, String author, String content){
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
        this.sec =sec;
        this.author = author;
        this.content = content;
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

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
