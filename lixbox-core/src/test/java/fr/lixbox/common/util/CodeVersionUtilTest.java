/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *   Copyrigth - LIXTEC - Tous droits reserves.
 *   
 *   Le contenu de ce fichier est la propriete de la societe Lixtec.
 *   
 *   Toute utilisation de ce fichier et des informations, sous n'importe quelle
 *   forme necessite un accord ecrit explicite des auteurs
 *   
 *   @AUTHOR Ludovic TERRAL
 *
 ******************************************************************************/
package fr.lixbox.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * Cette suite de test vérifie la classe CodeVersionUtil
 * 
 * @author ludovic.terral
 */
public class CodeVersionUtilTest
{
    @Test
    public final void testGetVersion()
    {
        Assert.assertTrue("Bad version read waited "+getVersion()+" received "+CodeVersionUtil.getVersion(this.getClass()), getVersion().equals(CodeVersionUtil.getVersion(this.getClass())));
    }
    
    
    
    private String getVersion()
    {
        String result = "";
        try
        {
            Properties prop = new Properties();
            InputStream is = this.getClass().getResourceAsStream("/build-info.properties");
            prop.load(is);
            is.close();
            result = prop.getProperty("version");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
