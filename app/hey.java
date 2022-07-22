import java.time.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

class hey {
    public static void main(String[] args) {

        LocalDate date = LocalDate.parse("2018-12-29");

        // create a LocalTime object
        LocalTime time = LocalTime.parse("20:12:32");

        // print Instant
        System.out.println("LocalDate: " + date);

        // create ZoneId
        ZoneOffset zone = ZoneOffset.of("Z");

        // print ZoneId
        System.out.println("ZoneOffset: " + zone);

        // apply ofInstant()
        long value = time.toEpochSecond(date, zone);

        // print result
        System.out.println("Epoch Second: " + value);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Instant y = now.atZone(ZoneId.systemDefault()).toInstant();
        LocalDateTime t = LocalDateTime.parse("2021\03/13 14:43:00", dtf);
        Instant i = t.atZone(ZoneId.systemDefault()).toInstant();
        long d = i.toEpochMilli();
        System.out.println(d - y.toEpochMilli());

    }
}