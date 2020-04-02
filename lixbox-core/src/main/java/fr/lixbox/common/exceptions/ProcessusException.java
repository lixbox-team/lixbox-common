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
package fr.lixbox.common.exceptions;

import java.text.MessageFormat;


public class ProcessusException extends RuntimeException
{
    // ----------- Attribut -----------      
    private static final long serialVersionUID = -188301181616951524L;
    
    
    
    // ----------- Methode -----------   
    public ProcessusException()
    {
        
    }

    

    public ProcessusException(final Throwable cause, final String msg,
            final Object... args)
    {
        super(MessageFormat.format(msg.replace("'", "''"), args), cause);
    }


    
    public ProcessusException(final String msg, final Object... args)
    {
        this(null, msg, args);
    }



    public ProcessusException(String arg0)
    {
        super(arg0);
    }


    
    public ProcessusException(String arg0, Throwable arg1)
    {
        super(arg0 + System.getProperty("line.separator") + "[Orig : " + arg1.getMessage() + " ]", arg1);
    }

    
    
    public ProcessusException(Throwable arg0)
    {
        super(arg0);
    }
}
