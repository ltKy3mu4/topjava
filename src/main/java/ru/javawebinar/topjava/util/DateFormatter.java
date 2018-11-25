package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDateTime> {

    public DateFormatter(){
    }

    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        return null;
    }
//"2015-05-31T00:00:30"
    public LocalDateTime parseFromIso(String pattern, Filter filter) throws RuntimeException {
        if (pattern == null){
            if (filter==Filter.StartPoint){
                return LocalDateTime.MIN;
            }
            else {
                return  LocalDateTime.MAX;
            }
        }

        String[] data = pattern.split("T");
        if (data.length !=2) {
            throw new RuntimeException("String "+pattern+" cannot be parsed as date-time format");
        }
        String[] time = data[1].split(":");
        String[] date = data[0].split("-");
        if (time.length!=3 || date.length !=3){
            throw new RuntimeException("String "+data[1]+" cannot be parsed as time or string "+data[0]+" cannot be parsed as dae format");
        }
        LocalDateTime dateTime = null;
        try{
            dateTime = LocalDateTime.of(
                    Integer.parseInt(date[0]),
                    Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1]),
                    Integer.parseInt(time[2])
            );
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            throw new RuntimeException(("Parsing of offered date-time failed"));
        }
        return dateTime;
    }

    @Override
    public String print(LocalDateTime localDateTime, Locale locale) {
        return null;
    }
}
