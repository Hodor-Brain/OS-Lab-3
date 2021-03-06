package com.company.Lab3;// The main MemoryManagement program, created by Alexander Reeder, 2000 Nov 19


import java.io.*;


public class MemoryManagement 
{
  private static void simulateWithWS(String[] args){
    ControlPanel controlPanel;
    Kernel kernel;

    if ( args.length < 1 || args.length > 2 )
    {
      System.out.println( "Usage: 'java MemoryManagement <COMMAND FILE> <PROPERTIES FILE>'" );
      System.exit( -1 );
    }

    File f = new File( args[0] );

    if ( ! ( f.exists() ) )
    {
      System.out.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
      System.exit( -1 );
    }
    if ( ! ( f.canRead() ) )
    {
      System.out.println( "MemoryM: error, read of " + f.getName() + " failed." );
      System.exit( -1 );
    }

    if ( args.length == 2 )
    {
      f = new File( args[1] );

      if ( ! ( f.exists() ) )
      {
        System.out.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
        System.exit( -1 );
      }
      if ( ! ( f.canRead() ) )
      {
        System.out.println( "MemoryM: error, read of " + f.getName() + " failed." );
        System.exit( -1 );
      }
    }

    kernel = new Kernel();
    controlPanel = new ControlPanel( "Memory Management with Working Set" );
    if ( args.length == 1 )
    {
      controlPanel.init( kernel , args[0] , null );
    }
    else
    {
      controlPanel.init( kernel , args[0] , args[1] );
    }
  }

  private static void simulateWithFIFO(String[] args){
    oldControlPanel controlPanel;
    oldKernel kernel;

    if ( args.length < 1 || args.length > 2 )
    {
      System.out.println( "Usage: 'java MemoryManagement <COMMAND FILE> <PROPERTIES FILE>'" );
      System.exit( -1 );
    }

    File f = new File( args[0] );

    if ( ! ( f.exists() ) )
    {
      System.out.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
      System.exit( -1 );
    }
    if ( ! ( f.canRead() ) )
    {
      System.out.println( "MemoryM: error, read of " + f.getName() + " failed." );
      System.exit( -1 );
    }

    if ( args.length == 2 )
    {
      f = new File( "C:\\Users\\rucla\\IdeaProjects\\OS-Lab-3\\src\\com\\company\\Lab3\\oldmemory.conf" );

      if ( ! ( f.exists() ) )
      {
        System.out.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
        System.exit( -1 );
      }
      if ( ! ( f.canRead() ) )
      {
        System.out.println( "MemoryM: error, read of " + f.getName() + " failed." );
        System.exit( -1 );
      }
    }

    kernel = new oldKernel();
    controlPanel = new oldControlPanel( "Memory Management with FIFO" );
    if ( args.length == 1 )
    {
      controlPanel.init( kernel , args[0] , null );
    }
    else
    {
      controlPanel.init( kernel , args[0] , "C:\\Users\\rucla\\IdeaProjects\\OS-Lab-3\\src\\com\\company\\Lab3\\oldmemory.conf" );
    }
  }

  public static void main(String[] args) 
  {
    new Thread(()->simulateWithWS(args)).start();
    ;
    new Thread(()->simulateWithFIFO(args)).start();
  }
}
