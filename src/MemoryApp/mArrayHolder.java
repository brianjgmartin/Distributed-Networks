package MemoryApp;


/**
* MemoryApp/mArrayHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Memory.idl
* 13 December 2014 17:46:42 o'clock GMT
*/

public final class mArrayHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public mArrayHolder ()
  {
  }

  public mArrayHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MemoryApp.mArrayHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MemoryApp.mArrayHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MemoryApp.mArrayHelper.type ();
  }

}
