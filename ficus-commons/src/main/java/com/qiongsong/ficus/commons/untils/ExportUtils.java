package com.qiongsong.ficus.commons.untils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @ClassName: ExportUtils
 * @author thierry.fu
 * @date Jan 12, 2016 10:21:44 PM
 */
public class ExportUtils {

  /**
   * suffix
   */
  public static final String CSV = ".csv";

  /**
   * support zh_cn and en_us jp
   */
  public static final String DEFAULT_DECODE = "gb2312";

  /***
   * export CSV file .
   * @param data
   * @param map
   * @param path
   * @param fileName
   * @return
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws IOException
   */
  public static File createCsvFile(List<?> data, Map<String, String> map,
      String path, String fileName) throws IllegalAccessException,
          InvocationTargetException, NoSuchMethodException, IOException {
    List<String> headers = new ArrayList<String>();
    List<String> fields = new ArrayList<String>();
    for (Entry<String, String> entry : map.entrySet()) {
      headers.add(entry.getKey());
      fields.add(entry.getValue());
    }
    return createCsvFile(data, headers, fields, path, fileName);
  }

  /**
   * export csv file.
   * @param data
   * @param header
   * @param fields
   * @param path
   * @param fileName
   * @return
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws IOException
   */
  public static File createCsvFile(List<?> data, List<String> header,
      List<String> fields, String path, String fileName)
          throws IllegalAccessException, InvocationTargetException,
          NoSuchMethodException, IOException {
    File csvFile = null;
    BufferedWriter writer = null;
    try {
      File file = new File(path);
      if (!file.exists()) {
        file.mkdir();
      }
      csvFile = File.createTempFile(fileName, CSV, new File(path));
      writer = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(csvFile), DEFAULT_DECODE),
          1024);
      for (Iterator<String> iter = header.iterator(); iter.hasNext();) {
        String head = iter.next();
        // head = head != null ? head : "";
        writer.write((head != null ? head : ""));
        if (iter.hasNext()) {
          writer.write(',');
        }
      }
      writer.write("\r\n");
      for (Iterator<?> iter = data.iterator(); iter.hasNext();) {
        Object row = iter.next();
        for (Iterator<String> fieldIterator = fields.iterator(); fieldIterator
            .hasNext();) {
          String field = fieldIterator.next();
          String fieldValue = BeanUtils.getProperty(row,
              (field != null ? field : ""));
          // fieldValue = new String(fieldValue.getBytes(DEFAULT_DECODE),
          // DEFAULT_DECODE);
          writer.write(fieldValue);
          if (fieldIterator.hasNext()) {
            writer.write(',');
          }
        }
        if (iter.hasNext()) {
          writer.write("\r\n");
        }
      }
    }
    finally {
      writer.flush();
      writer.close();
    }
    return csvFile;
  }
}
