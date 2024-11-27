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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.common.util.StringUtil;
import jakarta.mail.internet.InternetAddress;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Cette classe est l'entite Mail. Elle surcouche l'api JAVAMAIL
 * pour faciliter l'envoie des emails.
 * 
 * @author ludovic.terral
 */
@XmlType(name="EmailList")
public class EmailList implements Serializable
{
    // ----------- Attribut -----------   
    private static final long serialVersionUID = -120110512082919L;

    private List<EmailAdresse> listeAdresses;
    private String adressesLiterrales;
    
    
    
    // ----------- Methode -----------
    public EmailList()
    {       
    }
    
    
    
    public EmailList(String listeAdresses)
    {
        this.parserListeAdresses(listeAdresses, ";");        
    }
    
    
    
    public EmailList(EmailAdresse adresse)
    {
        this.listeAdresses = new ArrayList<>();
        listeAdresses.add(adresse);   
    }
    
    
    
    public EmailList(String listeAdresses, String separateur)
    {
        this.parserListeAdresses(listeAdresses, separateur);   
    }
    
    
    
    public EmailList(List<EmailAdresse> listeAdresses)
    {
        this.listeAdresses = listeAdresses;
        this.parserListeAdresses(listeAdresses, ";");   
    }
    


    public List<EmailAdresse> getListeEmailAdresses()
    {
        if (CollectionUtil.isEmpty(listeAdresses))
        {
            this.listeAdresses = new ArrayList<>();
        }
        return listeAdresses;
    }
    public void setListeEmailAdresses(List<EmailAdresse> listeAdresses)
    {
        this.listeAdresses = listeAdresses;
        parserListeAdresses(listeAdresses, ";");
    }
    

    
    public EmailList setListeInternetAdresses(InternetAddress[] addresses)
    {
        this.listeAdresses = new ArrayList<>();
        for (InternetAddress adress : addresses)
        {
            this.listeAdresses.add(new EmailAdresse(adress));
        }
        parserListeAdresses(this.listeAdresses, ";");
        return this;
    }
    
        
    
    /**
     * Cette methode convertit une ligne d'adresse mail en une liste
     * de String.
     * 
     * @param liste
     * @param separateur
     */
    private void parserListeAdresses(List<EmailAdresse> liste, String separateur)
    {
        if (!CollectionUtil.isEmpty(liste))
        {
            StringBuilder sbf = new StringBuilder(40);
            for (EmailAdresse adresse : liste)
            {
                sbf.append(adresse.getAdresseMail());
                sbf.append(separateur);
            }
            adressesLiterrales = sbf.substring(0, sbf.length()-1);
        }
    }


    
    /**
     * Cette methode convertit une ligne d'adresse mail en une liste
     * de String.
     * 
     * @param liste
     * @param separateur
     */
    private void parserListeAdresses(String liste, String separateur)
    {
        if (!StringUtil.isEmpty(liste))
        {
            liste = liste.trim();
            if (liste.endsWith(separateur))
            {
                liste = liste.substring(0, liste.lastIndexOf(separateur));
            }
            if (!StringUtil.isEmpty(separateur))
            {
                final StringTokenizer tokenMail = new StringTokenizer(liste, separateur);
                adressesLiterrales = liste;                
                listeAdresses = new ArrayList<>();
                while (tokenMail.hasMoreTokens()) // $codepro.audit.disable useForLoop
                {
                    listeAdresses.add(new EmailAdresse(tokenMail.nextToken().trim())); // $codepro.audit.disable avoidInstantiationInLoops
                }
            }
        }
    }
    
    

    @Override
    public String toString()
    {
        final StringBuilder sbf = new StringBuilder("EmailList:\t");
        if ( !StringUtil.isEmpty(adressesLiterrales))
        {
            sbf.append(adressesLiterrales);
        }
        return sbf.toString();
    }
}