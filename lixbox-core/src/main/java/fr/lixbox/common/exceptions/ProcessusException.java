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
