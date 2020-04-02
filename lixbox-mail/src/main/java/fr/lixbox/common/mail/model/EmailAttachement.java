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
package fr.lixbox.common.mail.model;

import java.net.URL;

/**
 * Cette classe est un attachement pour un email.
 * 
 * @author ludovic.terral
 */
public class EmailAttachement
{
    // ----------- Attribut -----------
    public static final String ATTACHMENT = "attachment";
    public static final String INLINE = "inline";
    private String name;
    private String description;
    private String path;
    private URL url;
    private String disposition;



    // ----------- Methode -----------
    public EmailAttachement()
    {
        this.name = "";
        this.description = "";
        this.path = "";
        this.disposition = ATTACHMENT;
    }



    public String getDescription()
    {
        return this.description;
    }
    public void setDescription(String desc)
    {
        this.description = desc;
    }
    


    public String getName()
    {
        return this.name;
    }
    public void setName(String aName)
    {
        this.name = aName;
    }
    


    public String getPath()
    {
        return this.path;
    }
    public void setPath(String aPath)
    {
        this.path = aPath;
    }
    


    public URL getURL()
    {
        return this.url;
    }
    public void setURL(URL aUrl)
    {
        this.url = aUrl;
    }
    

    
    public String getDisposition()
    {
        return this.disposition;
    }
    public void setDisposition(String aDisposition)
    {
        this.disposition = aDisposition;
    }
}
