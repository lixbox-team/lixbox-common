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
package fr.lixbox.common.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.ConstraintViolation;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.model.enumeration.NiveauEvenement;


/**
 * Cette classe est l'entite qui stocke un evenement
 * du systeme.
 * 
 * @author ludovic.terral
 */
@XmlType(propOrder={"niveau", "libelle", "dateEvent", "contexte"})
public class Evenement implements Serializable
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(Evenement.class);
    private static final long serialVersionUID = -688305381616951524L;

    private NiveauEvenement niveau;
    private String libelle;
    private Contexte contexte;
    private Calendar dateEvent;



    // ----------- Methode -----------
    public Evenement(final NiveauEvenement niveau,
            final String libelle, final Contexte contexte)
    {
        this.libelle=libelle;
        this.niveau=niveau;
        this.contexte=contexte;
        dateEvent=Calendar.getInstance();
    }



    public Evenement(final NiveauEvenement niveau,
            final String libelle, final Calendar dateEvent,
            final Contexte contexte)
    {
        this.libelle=libelle;
        this.niveau=niveau;
        this.contexte=contexte;
        this.dateEvent=dateEvent;
    }

    

    public Evenement(ConstraintViolation<?> violation, Contexte contexte)
    {
        this.libelle= violation.getPropertyPath()+": "+ violation.getMessage();
        this.niveau=NiveauEvenement.ERROR;
        this.contexte=contexte;
        dateEvent=Calendar.getInstance();
    }
    

    
    public Evenement()
    {
        LOG.debug("Evenement cree");
    }



    @XmlElement(required=true)
    public String getLibelle()
    {
        return libelle;
    }
    public void setLibelle(final String libelle)
    {
        this.libelle = libelle;
    }



    @Column(name = "EVT_NIVEAU", nullable=false, columnDefinition="varchar2(30 char)")
    @Enumerated(EnumType.STRING)
    @XmlElement(required=true)
    public NiveauEvenement getNiveau()
    {
        return niveau;
    }
    public void setNiveau(final NiveauEvenement niveau)
    {
        this.niveau = niveau;
    }



    public Contexte getContexte()
    {
        return contexte;
    }
    public void setContexte(final Contexte contexte)
    {
        this.contexte = contexte;
    }



    @XmlElement(required=true)
    public Calendar getDateEvent()
    {
        return dateEvent;
    }
    public void setDateEvent(final Calendar datEvent)
    {
        this.dateEvent=datEvent;
    }
    


    @Override
    public String toString()
    {
        String result = "Content error";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } 
        catch (JsonProcessingException e) 
        {
            LOG.error(e,e);
        }
        return result;
    }
}