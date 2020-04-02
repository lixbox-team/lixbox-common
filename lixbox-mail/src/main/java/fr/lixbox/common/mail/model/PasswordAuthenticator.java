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

import javax.mail.Authenticator;

/**
 * Cette classe est une extension de la classe Authenticator
 * 
 * @author ludovic.terral
 */
public class PasswordAuthenticator extends Authenticator
{
    // ----------- Attribut -----------
    private String userName;
    private String password;



    // ----------- Methodes -----------  
    public PasswordAuthenticator(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }



    public String getUserName()
    {
        return this.userName;
    }



    public String getPassword()
    {
        return this.password;
    }
}
