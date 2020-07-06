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
