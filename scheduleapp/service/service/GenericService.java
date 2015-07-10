package service.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import service.domain.GenericObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericService {

    protected void saveList(String fileName, List<? extends GenericObject> objects) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName));

        if (objects != null)
            for (GenericObject object : objects) {
                String[] record = getRecord(object);
                writer.writeNext(record);
            }
        writer.close();
    }

    protected <T extends GenericObject> List<T> readList(String fileName, String[] columns, Class<T> cls) throws FileNotFoundException {
        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(cls);
            strategy.setColumnMapping(columns);

            CsvToBean csv = new CsvToBean();
            CSVReader csvReader = new CSVReader(new FileReader(fileName));

            return csv.parse(strategy, csvReader);
        } else
            return new ArrayList<>();
    }

    protected <T extends GenericObject> long getLastId(List<T> objects) {
        long last=0;
        for (GenericObject object : objects)
            last = Math.max(last, object.getId());

        return last;
    }

    protected abstract String[] getRecord(GenericObject object);
}
