package MemoryApp;

/**
* MemoryApp/MemoryHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Memory.idl
* 13 December 2014 17:46:42 o'clock GMT
*/

public final class MemoryHolder implements org.omg.CORBA.portable.Streamable
{
  public MemoryApp.Memory value = null;

  public MemoryHolder ()
  {
  }

  public MemoryHolder (MemoryApp.Memory initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MemoryApp.MemoryHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MemoryApp.MemoryHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MemoryApp.MemoryHelper.type ();
  }

}
