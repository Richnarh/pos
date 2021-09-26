package com.khoders.pos.utils;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FxmlView
{
    static String basePath = "src/com/khoders/pos/fxml/";
    public static URL getFxml(String filename)
    {
        try
        {
            Path path = Paths.get(basePath+filename+".fxml");
            return path.toUri().toURL();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
