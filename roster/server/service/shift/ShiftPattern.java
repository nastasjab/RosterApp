package server.service.shift;

import server.service.GenericObject;

import java.util.ArrayList;
import java.util.List;

public class ShiftPattern extends GenericObject{

    private String title;
    private List<String> dayDefinitions;

    public ShiftPattern() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addDayDefinition(String day){
        if (dayDefinitions==null)
            dayDefinitions=new ArrayList<>();

         dayDefinitions.add(day);
    }

    public int getPatternLength(){
        return (dayDefinitions==null) ? 0 : dayDefinitions.size();
    }

    public List<String> getDayDefinitions() {
        return dayDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Shift pattern id: %d\n", getId()));
        stringBuilder.append(String.format("Shift pattern title: %s\n", title));
        stringBuilder.append("Pattern: ");
        for (String day : dayDefinitions){
            stringBuilder.append(String.format("%s ", day));
        }
        return stringBuilder.toString();
    }
}
