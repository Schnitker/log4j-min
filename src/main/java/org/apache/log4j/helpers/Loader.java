/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.helpers;

import java.net.URL;


/**
   Load resources (or images) from various sources.
 
  @author Ceki G&uuml;lc&uuml;
 */

public class Loader  { 

  static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";

  /**
   *  Get a resource by delegating to getResource(String).
   *  @param resource resource name
   *  @param clazz class, ignored.
   *  @return URL to resource or null.
   *  @deprecated as of 1.2.
   */
  public static URL getResource(String resource, Class clazz) {
      return getResource(resource);
  }

  /**
     This method will search for <code>resource</code> in different
     places. The search order is as follows:

     <ol>

     <p><li>Search for <code>resource</code> using the thread context
     class loader under Java2. If that fails, search for
     <code>resource</code> using the class loader that loaded this
     class (<code>Loader</code>). Under JDK 1.1, only the the class
     loader that loaded this class (<code>Loader</code>) is used.

     <p><li>Try one last time with
     <code>ClassLoader.getSystemResource(resource)</code>, that is is
     using the system class loader in JDK 1.2 and virtual machine's
     built-in class loader in JDK 1.1.

     </ol>
  */
  static public URL getResource(String resource) {
    ClassLoader classLoader = null;
    URL url = null;
    
    try {

  	  classLoader = Thread.currentThread().getContextClassLoader();
  	  if(classLoader != null) {
  	    LogLog.debug("Trying to find ["+resource+"] using context classloader "
  			 +classLoader+".");
  	    url = classLoader.getResource(resource);      
  	    if(url != null) {
  	      return url;
  	    }
  	  }
  	
  	// We could not find resource. Ler us now try with the
  	// classloader that loaded this class.
  	classLoader = Loader.class.getClassLoader(); 
  	if(classLoader != null) {
  	  LogLog.debug("Trying to find ["+resource+"] using "+classLoader
  		       +" class loader.");
  	  url = classLoader.getResource(resource);
  	  if(url != null) {
  	    return url;
  	  }
  	}
    } catch(Throwable t) {
      //
      //  can't be InterruptedException or InterruptedIOException
      //    since not declared, must be error or RuntimeError.
      LogLog.warn(TSTR, t);
    }
    
    // Last ditch attempt: get the resource from the class path. It
    // may be the case that clazz was loaded by the Extentsion class
    // loader which the parent of the system class loader. Hence the
    // code below.
    LogLog.debug("Trying to find ["+resource+
  		   "] using ClassLoader.getSystemResource().");
    return ClassLoader.getSystemResource(resource);
  } 
  
/*
 * simplified 
 */
static public Class loadClass (String clazz) throws ClassNotFoundException {
    try {
      return Thread.currentThread().getContextClassLoader().loadClass(clazz);
    } catch(Throwable t) {
    }
    return Class.forName(clazz);
  }
}
