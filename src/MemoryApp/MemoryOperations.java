package MemoryApp;


/**
* MemoryApp/MemoryOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Memory.idl
* 18 November 2014 20:01:41 o'clock GMT
*/

public interface MemoryOperations 
{
  boolean login (String name, String password);
  void register (String username, String password);
  void addmemory (String memory, String username);
  void addResource (String resource, String memory);
} // interface MemoryOperations
