package com.qiongsong.ficus.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import com.qiongsong.ficus.commons.untils.NameUtils;

public class NameUtilsTest {

  @Test
  public void camelName() {
    String ficusCommons = NameUtils.camelCaseName("FICUS_COMMONS");
    Assert.assertEquals(ficusCommons, "ficusCommons");
    System.out.println(ficusCommons);

    String FICUS_COMMONS = NameUtils.underscoreName(ficusCommons);
    Assert.assertEquals(FICUS_COMMONS, "FICUS_COMMONS");
    System.out.println(FICUS_COMMONS);

    FICUS_COMMONS = NameUtils.underscoreName("AbcABBcCC44t");
    Assert.assertEquals(FICUS_COMMONS, "ABC_A_B_BC_C_C44T");
    System.out.println(FICUS_COMMONS);

    String name = NameUtils.uncapitalize("Ficus");
    Assert.assertEquals(name, "ficus");
    System.out.println(name);

    name = NameUtils.capitalize(name);
    Assert.assertEquals(name, "Ficus");
    System.out.println(name);

    name = NameUtils.uncapitalize("44Ficus");
    Assert.assertEquals(name, "44Ficus");
    System.out.println(name);

    name = NameUtils.capitalize(name);
    Assert.assertEquals(name, "44Ficus");
    System.out.println(name);

  }

}
