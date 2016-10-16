/**
 *
 */
package br.com.cadmea.spring.rest;

/**
 * @author Gilberto Santos
 */

public final class ServicePath {

  ///////////////////////////////////////////////////////////////
  // ROOT PATH
  ///////////////////////////////////////////////////////////////

  public static final String ALL = "/**";

  public static final String ROOT_PATH = "/api";

  public static final String PUBLIC_ROOT_PATH = ROOT_PATH + "/public";

  public static final String PRIVATE_ROOT_PATH = ROOT_PATH + "/private";

  ///////////////////////////////////////////////////////////////
  // PUBLIC PATHS
  ///////////////////////////////////////////////////////////////

  public static final String LOGIN_PATH = PUBLIC_ROOT_PATH + "/login";

  public static final String LOGOUT_PATH = PUBLIC_ROOT_PATH + "/logout";

}
