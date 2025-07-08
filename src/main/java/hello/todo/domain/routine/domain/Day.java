package hello.todo.domain.routine.domain;

import java.time.DayOfWeek;

public enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN;

    public static Day from(DayOfWeek dayOfWeek) {
        //dayOfWeek는 월~일을 1~7까지 숫자로 나타낸다.
        Day result = switch (dayOfWeek) {
            case MONDAY -> Day.MON;
            case TUESDAY -> Day.TUE;
            case WEDNESDAY -> Day.WED;
            case THURSDAY -> Day.THU;
            case FRIDAY -> Day.FRI;
            case SATURDAY -> Day.SAT;
            case SUNDAY -> Day.SUN;
        };
        return result;
    }
}
