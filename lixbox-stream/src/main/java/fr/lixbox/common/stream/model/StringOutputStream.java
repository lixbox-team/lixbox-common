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
package fr.lixbox.common.stream.model;

import java.io.OutputStream;

/**
 * Cette classe considere une string comme un flux de donnee
 * 
 * @author ludovic.Terral
 */
public class StringOutputStream extends OutputStream
{
    // ----------- Attribut -----------
    private StringBuilder buffer;

    

    // ----------- Methode -----------
    public StringOutputStream()
    {
        buffer = new StringBuilder(2048);
    }

    

    public StringOutputStream(int size)
    {
        buffer = new StringBuilder(size);
    }

    
    
    public StringOutputStream(StringBuilder buf)
    {
        if (null == buf)
        {
            buffer = new StringBuilder(2048);
        }
        else
        {
            buffer = buf;
        }
    }
    
    
    
    public int length()
    {
        return buffer.length();
    }
    public void setLength(final int longueur)
    {
        buffer.setLength(longueur);
    }



    @Override
    public void write(final int bytes)    
    {       
        buffer.append((char) bytes);        
    }



    @Override
    public String toString()
    {
        return buffer.toString();
    }
}
