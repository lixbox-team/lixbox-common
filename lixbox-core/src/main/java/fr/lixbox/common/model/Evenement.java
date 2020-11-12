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
package fr.lixbox.common.model;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.util.StringUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.ConstraintViolation;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


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
    public static Evenement valueOf(String json)
    {
        Evenement result = null;
        TypeReference<Evenement> typeRef = new TypeReference<Evenement>() {};
        if (StringUtil.isNotEmpty(json)) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                result = mapper.readValue(json, typeRef);
            } catch (Exception var5) {
                LOG.error(var5, var5);
            }
        }

        return result;
    }
    
    
    
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