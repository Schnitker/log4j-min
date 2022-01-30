package com.schnitker.github.log4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestOutput  {

    @Test
    public void test1() {

        ConsoleOutputCapturer out = new ConsoleOutputCapturer();
        out.start();
        Logger.getLogger( getClass() ).info( "hello world" );
        
        String logged = out.stop();
        
        Assert.assertTrue( "test INFO", logged.contains( "INFO" ) );
        Assert.assertTrue( "test INFO", logged.contains( "hello world" ) );
    }

    @Test
    public void test2() throws IOException {

        Logger.getLogger( getClass() ).info( "hello world" );
        
        List< String > lines = Files.readAllLines( Paths.get("the.log") );
        
        Assert.assertTrue( "test INFO", lines.size() > 0 );
        Assert.assertTrue( "test INFO", lines.get(0).contains( "hello world" ) );
    }
}
