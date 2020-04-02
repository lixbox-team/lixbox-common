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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;


/**
 * Cette classe lit le contenu d'un stream et de le rend en String.
 * 
 * @author ludovic.terral
 */
public class StreamStringUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(StreamStringUtil.class);


    
    // ----------- Methode -----------
    private StreamStringUtil()
    {
        //private constructor
    }
    
    
    
    /**
     * Cette methode transforme un stream entrant en une representation litterale
     * 
     * @param stream a transformer
     * 
     * @return une String image du stream
     * 
     * @throws BusinessException 
     */
    public static String read(InputStream stream)
    {
        return read(stream, StandardCharsets.UTF_8);
    }
    
    
    
    /**
     * Cette methode transforme un stream entrant en une representation litterale
     * 
     * @param stream a transformer
     * @param charset
     * 
     * @return une String image du stream
     * 
     * @throws BusinessException 
     */
    public static String read(InputStream stream, Charset charset)
    {
        try
        {
            return IOUtils.toString(stream, charset);
        }
        catch (IOException e)
        {
            throw new ProcessusException(e);
        }
    }
    
    
    
    /**
     * Cette methode transforme un stream entrant en une representation binaire
     * 
     * @param stream a transformer
     * 
     * @return un tableau de byte
     * 
     * @throws BusinessException 
     */
    public static Byte[] readToOByte(InputStream stream)
    {
        final List<Byte> byteList = new ArrayList<>();
        try
        {
            int i;      
            do
            {
                i = stream.read();
                if (i != -1)
                {
                    byteList.add((byte) i);
                }
            }
            while (i != -1);
            stream.close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
        return byteList.toArray(new Byte[]{});
    }   
    
    
        
    /**
     * Cette methode transforme un stream entrant en une representation binaire
     * 
     * @param stream a transformer
     * 
     * @return un tableau de byte
     * 
     * @throws BusinessException 
     */
    public static byte[] readToByte(InputStream stream)
    {        
        if (null == stream)
        {
            throw new ProcessusException(LixboxResources.getString(
                    "ERROR.PARAM.INCORRECT.02", "stream"));
        }        
        try
        {
            return IOUtils.toByteArray(stream);
        }
        catch (IOException e)
        {
            throw new ProcessusException(e);
        }
    }
    
    
    
    /**
     * Cette methode transforme une representation litterale en un stream sortant
     * 
     * @param string a transformer
     * @param os a transformer
     * 
     * @throws BusinessException
     */
    public static void write(String string, OutputStream os)
    {
        write(string, os, StandardCharsets.UTF_8); //$NON-NLS-1$
    }
    
    
    
    /**
     * Cette methode transforme une representation litterale en un stream sortant
     * 
     * @param string
     * @param os
     * @param charset
     * 
     * @throws BusinessException
     */
    public static void write(String string, OutputStream os, Charset charset)
    {
        try
        {
            os.write(string.getBytes(charset));
            os.flush();
            os.close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }
    
    
    
    /**
     * Cette methode transforme une representation binaire en un stream sortant
     * 
     * @param byteArray
     * @param os
     * 
     * @throws BusinessException
     */
    public static void writeByte(byte[] byteArray, OutputStream os)
    {
        try
        {
            os.write(byteArray);
            os.close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }
}
