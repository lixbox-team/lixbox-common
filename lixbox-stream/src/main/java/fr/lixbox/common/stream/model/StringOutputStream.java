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
package fr.lixbox.common.stream.model;

import java.io.OutputStream;

/**
 * Cette classe considere une string comme un flux de donnee
 * 
 * @author ludovic.Terral
 */
public class StringOutputStream extends OutputStream
{
    // ----------- Attribut -----------
    private StringBuilder buffer;

    

    // ----------- Methode -----------
    public StringOutputStream()
    {
        buffer = new StringBuilder(2048);
    }

    

    public StringOutputStream(int size)
    {
        buffer = new StringBuilder(size);
    }

    
    
    public StringOutputStream(StringBuilder buf)
    {
        if (null == buf)
        {
            buffer = new StringBuilder(2048);
        }
        else
        {
            buffer = buf;
        }
    }
    
    
    
    public int length()
    {
        return buffer.length();
    }
    public void setLength(final int longueur)
    {
        buffer.setLength(longueur);
    }



    @Override
    public void write(final int bytes)    
    {       
        buffer.append((char) bytes);        
    }



    @Override
    public String toString()
    {
        return buffer.toString();
    }
}
