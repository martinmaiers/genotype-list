/*

    gl-oauth  OAuth related projects and samples.
    Copyright (c) 2012 National Marrow Donor Program (NMDP)

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.immunogenomics.gl.oauth;

import javax.servlet.http.HttpServletRequest;


/**
 * Hooks that allow an application to attach authorization information to the thread or the request.
 * @author mgeorge
 *
 */
public interface AuthorizedHooks {
	
	/** Called prior to performing normal operations.
	 * Implementations are expected to attach necessary authorization information to the thread or the request.
	 * @param request
	 * @param authorization
	 */
	void beginAuthorized(HttpServletRequest request, AccessTokenDetails authorization);

	/**
	 * Remove any authorization information from the thread.
	 */
	void endAuthorized();
}