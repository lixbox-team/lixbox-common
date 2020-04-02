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

import java.beans.Transient;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.mail.internet.InternetAddress;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe est une combinaison entre un parser et un pojo compatible avec
 * la RFC2822: compliant email addresses.
 * 
 * @author ludovic.terral
 */
@Embeddable
@XmlType(propOrder={"nom", "adresseMail"}, namespace="lixbox")
public class EmailAdresse implements Serializable
{
    // ----------- Attribut -----------
    private static final long serialVersionUID = -20110512100654L;
   
    private String nom;
    private String adresseMail;
    private String nomUtilisateur;



    // ----------- Methode -----------
    public EmailAdresse()
    {
    }
    
    
    
    public EmailAdresse(String adresseLitterale)
    {
        if (adresseLitterale.endsWith(";"))
        {
            adresseLitterale = adresseLitterale.substring(0, adresseLitterale.length()-1);
        }            
        nom = this.extraireNom(adresseLitterale);
        adresseMail = this.extraireAdresseMail(adresseLitterale);
        nomUtilisateur = this.extraireNomUtilisateur();
    }

    

    public EmailAdresse(InternetAddress internetAddress)
    {
        nom = internetAddress.getPersonal();
        adresseMail = internetAddress.getAddress();
        nomUtilisateur = this.extraireNomUtilisateur();
    }



    @XmlElement(required=false)
    public String getNom()
    {
        if (StringUtil.isEmpty(nom) && !StringUtil.isEmpty(nomUtilisateur))
        {
            nom = nomUtilisateur;
        }
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }



    @XmlElement(required=true)
    public String getAdresseMail()
    {
        return adresseMail;
    }
    public void setAdresseMail(String adresseMail)
    {
        this.adresseMail = adresseMail;
        this.nomUtilisateur = this.extraireNomUtilisateur();
    }



    @XmlTransient
    @Transient
    public String getNomUtilisateur()
    {
        return nomUtilisateur;
    }

    

    @Transient
    public String getAdresseLitterale()
    {
        return nom + " <" + adresseMail +">";
    }



    /**
     * Cette methode extrait l'email dans la chaine fournie
     * 
     * return la chaine email 
     */
    private String extraireAdresseMail(String adresseLitterale)
    {
        String result = "";
        if (!StringUtil.isEmpty(adresseLitterale))
        {
            if ((adresseLitterale.indexOf('<') != -1) &&
                    (adresseLitterale.indexOf('>') != -1))
            {
                result =adresseLitterale
                            .substring(adresseLitterale.indexOf('<') + 1, adresseLitterale.indexOf('>'))
                                .trim();
            }
            else
            {
                result = adresseLitterale;
            }
        }
        return result;
    }
    


    /**
     * Cette methode extrait le nom dans la chaine fournie
     * 
     * return la chaine nom 
     */
    private String extraireNom(String adresseLitterale)
    {
        String result = "";
        if (!StringUtil.isEmpty(adresseLitterale) && ((adresseLitterale.indexOf('<') != -1) &&
                    (adresseLitterale.indexOf('>') != -1)))
        {
            result =adresseLitterale
                        .substring(0, adresseLitterale.indexOf('<'))
                        .replaceAll("\"", "")
                            .trim();
        }
        return result;
    }
    
    

    /**
     * Cette methode extrait le nomUtilisateur dans la chaine fournie
     * 
     * return la chaine nomUtilisateur 
     */
    private String extraireNomUtilisateur()
    {
        String result = "";
        if (!StringUtil.isEmpty(adresseMail))
        {
            if (adresseMail.indexOf('@') != -1)
            {
                result =adresseMail.substring(0, adresseMail.indexOf('@')).trim();
            }
            else
            {
                result = adresseMail;
            }
        }
        return result;
    }
    
    
    
    public String toString()
    {
        return MessageFormat.format("{0} <{1}>", nom, adresseMail);
    }
}
