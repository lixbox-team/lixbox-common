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
package fr.lixbox.common.stream.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;

import fr.lixbox.common.util.StringUtil;

public class FileUtil extends FileUtils
{
    // ----------- Attribut(s) -----------
    protected static final Log LOG = LogFactory.getLog(FileUtil.class);
    
    private static final String UNKNOWN_MIME_TYPE = "application/unknown";

    

    // ----------- Methode(s) -----------
    private FileUtil()
    {
        // constructeur servant à transformer la classe en utilitaire
    }
    
    

    /**
     * Cette methode identifie le type Mime d'un fichier ou d'un repertoire
     * 
     * @param fichier
     * 
     * @return le mime type
     */
    public static String getMIMEType(File fichier)
    {
        String result = UNKNOWN_MIME_TYPE;
        if (fichier.isDirectory())
        {
            result = "repertoire";
        }
        else if (!fichier.exists())
        {
            result = "fichier inexistant";
        }
        else
        {
            try
            {
                Tika detector = new Tika();                
                result = StringUtil.isEmpty(detector.detect(fichier))?result:detector.detect(fichier);
            }
            catch (Exception e)
            {
                LOG.error(e);
            }
        }        
        return result;
    }
    
    
    
    /**
     * Cette methode identifie le type Mime d'un tableau de byte
     * 
     * @param bytes[]
     * 
     * @return le mime type
     */
    public static String getMIMEType(byte[] data)
    {
        String result = UNKNOWN_MIME_TYPE;        
        try
        {
            Tika detector = new Tika();                
            result = StringUtil.isEmpty(detector.detect(data))?result:detector.detect(data);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return result;
    }
    
    
    
    /**
     * Cette methode identifie le type Mime d'un tableau de byte
     * 
     * @param bytes[]
     * 
     * @return le mime type
     */
    public static String getMIMEType(byte[] data, String filename)
    {
        String result = UNKNOWN_MIME_TYPE;        
        try
        {
            Tika detector = new Tika();                
            result = StringUtil.isEmpty(detector.detect(data, filename))?result:detector.detect(data, filename);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return result;
    }
}