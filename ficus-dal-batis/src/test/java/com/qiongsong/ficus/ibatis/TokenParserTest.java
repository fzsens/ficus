package com.qiongsong.ficus.ibatis;

import org.junit.Test;

import com.qiongsong.ficus.dal.support.parser.GenericTokenParser;
import com.qiongsong.ficus.dal.support.parser.TokenHandler;

public class TokenParserTest {

  @Test
  public void testToken() {
    GenericTokenParser tp = new GenericTokenParser("#{", "}", new MyTokenHandler());
    String result = tp.parse("#{abc}");
    System.out.println(result);
  }
}

class MyTokenHandler implements TokenHandler {

  @Override
  public String handleToken(String content) {
    // TODO Auto-generated method stub
    return content;
  }

}
