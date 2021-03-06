package com.qiongsong.ficus.ibatis.xml;

import java.util.regex.Pattern;

import com.qiongsong.ficus.dal.support.parser.GenericTokenParser;
import com.qiongsong.ficus.dal.support.parser.TokenHandler;
import com.qiongsong.ficus.ibatis.builder.DynamicContext;

/**
 * @author Clinton Begin
 */
public class TextSqlNode implements SqlNode {
  private String text;
  private Pattern injectionFilter;

  public TextSqlNode(String text) {
    this(text, null);
  }

  public TextSqlNode(String text, Pattern injectionFilter) {
    this.text = text;
    this.injectionFilter = injectionFilter;
  }

  public boolean isDynamic() {
    DynamicCheckerTokenParser checker = new DynamicCheckerTokenParser();
    GenericTokenParser parser = createParser(checker);
    parser.parse(text);
    return checker.isDynamic();
  }

  public boolean apply(DynamicContext context) {
    GenericTokenParser parser = createParser(new BindingTokenParser(context, injectionFilter));
    context.appendSql(parser.parse(text));
    return true;
  }

  private GenericTokenParser createParser(TokenHandler handler) {
    return new GenericTokenParser("${", "}", handler);
  }

  private static class BindingTokenParser implements TokenHandler {

    private DynamicContext context;
    private Pattern injectionFilter;

    public BindingTokenParser(DynamicContext context, Pattern injectionFilter) {
      this.context = context;
      this.injectionFilter = injectionFilter;
    }

    public String handleToken(String content) {
      Object parameter = context.getBindings().get("_parameter");
      if (parameter == null) {
        context.getBindings().put("value", null);
      }
      // else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
      // context.getBindings().put("value", parameter);
      // }
      // Object value = OgnlCache.getValue(content,
      // context.getBindings());
      Object value = null;
      String srtValue = (value == null ? "" : String.valueOf(value)); // issue
                                      // #274
                                      // return
                                      // ""
                                      // instead
                                      // of
                                      // "null"
      checkInjection(srtValue);
      return srtValue;
    }

    private void checkInjection(String value) {
      if (injectionFilter != null && !injectionFilter.matcher(value).matches()) {
        throw new UnsupportedOperationException(
            "Invalid input. Please conform to regex" + injectionFilter.pattern());
      }
    }
  }

  private static class DynamicCheckerTokenParser implements TokenHandler {

    private boolean isDynamic;

    public DynamicCheckerTokenParser() {
      // Prevent Synthetic Access
    }

    public boolean isDynamic() {
      return isDynamic;
    }

    public String handleToken(String content) {
      this.isDynamic = true;
      return null;
    }
  }

}
