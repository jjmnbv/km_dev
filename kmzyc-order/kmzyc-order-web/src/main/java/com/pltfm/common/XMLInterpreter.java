package com.pltfm.common;

import java.io.InputStream;

public class XMLInterpreter {
  public void loadFromXML(InputStream inputstream) {
    ResourceFactory.loadFromXML(inputstream);
  }
}
