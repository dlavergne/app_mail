package com.itsoft.app_mail.util;

import java.util.Properties;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class PropertyLoader{

  
   /**
 * @param filename
 * @return
 * @throws IOException
 * @throws FileNotFoundException
 */
public static Properties load(String filename) throws IOException, FileNotFoundException{
      Properties properties = new Properties();
      FileInputStream input = new FileInputStream(filename);
      try{
         properties.load(input);
         return properties;
      }
              finally{
         input.close();

      }

   }
}