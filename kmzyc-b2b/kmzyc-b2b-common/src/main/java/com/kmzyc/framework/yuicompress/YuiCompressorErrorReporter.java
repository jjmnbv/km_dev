package com.kmzyc.framework.yuicompress;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

class YuiCompressorErrorReporter implements ErrorReporter {
  public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
    if (line < 0) {
      System.out.println("\n[WARNING] " + message);
    } else {
      System.out.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
    }
  }

  public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
    if (line < 0) {
      System.out.println("\n[WARNING] " + message);
    } else {
      System.out.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
    }
  }

  @Override
  public EvaluatorException runtimeError(String arg0, String arg1, int arg2, String arg3, int arg4) {

    return null;
  }
}
