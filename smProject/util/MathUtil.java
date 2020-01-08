package com.smProject.util;

public class MathUtil
{
  public static synchronized double round(double d, int n)
  {
    double exp = 1.0D;
    for (int i = 0; i < n; i++)
    {
      exp *= 10.0D;
    }
    double dResult = Math.round(d * exp) / exp;

    return dResult;
  }

  public static synchronized float roundFloat(float f, int n)
  {
    float exp = 1.0F;
    for (int i = 0; i < n; i++)
    {
      exp *= 10.0F;
    }

    float fResult = Math.round(f * exp) / exp;

    return fResult;
  }

  public static synchronized int parseInt(String str)
  {
    int rtn = 0;
    try {
      if ((str != null) && (!str.equals(""))) rtn = Integer.parseInt(str); else
        return 0;
    } catch (Exception ignore) {
      rtn = 0;
    }
    return rtn;
  }

  public static synchronized long parseLong(String str)
  {
    long rtn = 0L;
    try {
      if ((str != null) && (!str.equals(""))) rtn = Long.parseLong(str); else
        return 0L;
    } catch (Exception ignore) {
      rtn = 0L;
    }
    return rtn;
  }

  public static synchronized double parseDouble(String str)
  {
    double rtn = 0.0D;
    try {
      if ((str != null) && (!str.equals(""))) rtn = Double.parseDouble(str); else
        return 0.0D;
    } catch (Exception ignore) {
      rtn = 0.0D;
    }
    return rtn;
  }

  public static synchronized float parseFloat(String str)
  {
    float rtn = 0.0F;
    try {
      if ((str != null) && (!str.equals(""))) rtn = Float.parseFloat(str); else
        return 0.0F;
    } catch (Exception ignore) {
      rtn = 0.0F;
    }
    return rtn;
  }
}