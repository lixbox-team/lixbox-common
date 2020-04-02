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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Embeddable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.exceptions.ProcessusException;


/**
 * Cette classe est l'entite stocke un contexte
 * d'activite.
 * 
 * @author ludovic.terral
 */
@Embeddable
public class Contexte implements Serializable
{
    // ----------- Attribut -----------   
    private static final long serialVersionUID = -688305311616951524L;
    protected static final Log LOG = LogFactory.getLog(Contexte.class);  
    private Map<String, String> contenu;

    
    
    // ----------- Methode -----------
    public Contexte()
    {
        contenu = new HashMap<>();
    }
    
    
    
    public Contexte(Contexte contexte)
    {
        contenu = contexte.contenu;
    }   

    
    
    public Contexte dupliquer()
    {
        final Contexte clone = new Contexte();
        for (Entry<String, String> entry: contenu.entrySet())
        {
            clone.contenu.put(entry.getKey(), entry.getValue());
        }
        return clone;
    }
           
        
 
    public Map<String, String> getContenu()
    {
        return contenu;
    }



    /**
     * Cette methode saisit un element contextuel
     * dans la pile de contexte.
     * 
     * @param elementName 
     * @param elementValue
     * 
     * @throws ProcessusException
     */
    public void put(final String elementName, final String elementValue)
    {        
        contenu.put(elementName, elementValue);
    }
    
    
    
    /**
     * Cette methode recupere un element contextuel
     * dans la pile de contexte.
     * 
     * @param element 
     * 
     * @return valeur de l'element contextuel
     */
    public String get(final String element)
    {
        return contenu.get(element);
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