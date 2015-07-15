package server.service.shift;

import server.service.GenericObject;

import java.util.ArrayList;
import java.util.List;

public class ShiftTiming extends GenericObject{

    private String title;
    private List<Shift> shifts;
    private int shiftCount;

    public ShiftTiming() {
    }

    public ShiftTiming(String title) {
        this.title = title;
        shiftCount = shifts==null? 0 : shifts.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Shift> getShifts() {
        if (shifts==null)
            shifts=new ArrayList<>();

        return shifts;
    }

    public void addShift(Shift shift){
        if (shifts==null)
            shifts=new ArrayList<>();

        if (shift!=null)
            shifts.add(shift);
        shiftCount++;
    }

    public int getShiftCount() {
        return shiftCount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("Shift timing id %d\n", getId()));
        stringBuilder.append(String.format("Shift timing title %s\n", title));


        stringBuilder.append("Shifts:");
        if (shifts==null || shifts.isEmpty()){
            stringBuilder.append("<empty>");

        } else {
            stringBuilder.append(String.format("\n       %-5s %-5s %-10s\n",
                    "Start", "End", "Min empl. cnt"));

            for (Shift shift : shifts) {
                stringBuilder.append(String.format("       %-5s %-5s %-10d\n",
                        shift.getTimeFrom(),
                        shift.getTimeTo(),
                        shift.getMinEmployeeCount()
                ));
            }
        }
        return stringBuilder.toString();
    }
}
