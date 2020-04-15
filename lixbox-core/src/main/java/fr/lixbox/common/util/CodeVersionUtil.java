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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cette classe est utilitaire de lecture du fichier de build-info.properties
 * 
 * @author ludovic.terral
 */
public class CodeVersionUtil
{
    // ----------- Attribut(s) -----------    
    private static final Log LOG = LogFactory.getLog(CodeVersionUtil.class);



    // ----------- Methode(s) -----------
    private CodeVersionUtil()
    {
    }
    
    
    
    public static String getVersion(Class<?> clasz)
    {
        String result = "unknow";
        try(
            InputStream is = clasz.getResourceAsStream("/META-INF/MANIFEST.MF");
        )
        {
            Properties prop = new Properties();
            prop.load(is);
            result = prop.getProperty("Implementation-Version");
        }
        catch (IOException e)
        {
            LOG.error(e);
        }
        return result;
    }
}