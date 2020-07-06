/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 * This file is part of lixbox-common.
 *
 *    lixbox-iam is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    lixbox-iam is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *    along with lixbox-common.  If not, see <https://www.gnu.org/licenses/>
 *   
 *   @AUTHOR Lixbox-team
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
