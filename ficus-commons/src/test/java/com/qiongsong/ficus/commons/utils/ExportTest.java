package com.qiongsong.ficus.commons.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.qiongsong.ficus.commons.untils.ExportUtils;

public class ExportTest {
  @Test
  public void test() throws IllegalAccessException, InvocationTargetException,
      NoSuchMethodException, IOException {
    List<Item> items = new ArrayList<Item>();
    for (int i = 0; i < 10; i++) {
      Item e = new Item();
      e.setName(i + "abc");
      e.setValue("zhongguo和ihao" + i);
      e.setContent("你好你好你好你好你好初めまして  ひとこと 「好きです」 スキデス " + i);
      items.add(e);
    }
    List<String> headers = new ArrayList<String>();
    headers.add("名字");
    headers.add("值值");
    headers.add("内容");
    List<String> fields = new ArrayList<String>();
    fields.add("name");
    fields.add("value");
    fields.add("content");
    ExportUtils.createCsvFile(items, headers, fields, "c:/export/", "test");
  }
}
