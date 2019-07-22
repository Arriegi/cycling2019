package eus.jarriaga.cycling.business.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DurationEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Duration duration = (Duration) getValue();

        //duration: hh:mm
        //duration: hh



        //return exoticType == null ? "" : Integer.toString(duration.getNano());
        //return duration == null ? "" : LocalTime.MIDNIGHT.plus(duration).format(DateTimeFormatter.ofPattern("hh:mm"));

        if (duration == null) {
            return "";
        }
        else {
            String hours = Long.toString(duration.toHours());
            if (hours.length() == 1) {
                hours = "0" + hours;
            }

            String minutes = Long.toString(duration.toMinutes() - (duration.toHours() * 60));
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }
            String respuesta = hours + ":" + minutes;

            return respuesta;
        }
    }

    @Override
    public void setAsText(String imputedTime) throws IllegalArgumentException {
        Duration duration;

        //duration: hh:mm
        //duration: hh
        int pos = imputedTime.indexOf(":");

        Long minutesDuration = Long.valueOf(0);
        if (pos == -1) {
            minutesDuration = Long.parseLong(imputedTime) * 60;
        }
        else {
            int hours = Integer.parseInt(imputedTime.substring(0, pos));
            int minutes = Integer.parseInt(imputedTime.substring(pos+1));

            minutesDuration = new Long(hours * 60 + minutes);
        }



        duration = Duration.ofMinutes(Long.valueOf(minutesDuration));
        setValue(duration);
    }
}