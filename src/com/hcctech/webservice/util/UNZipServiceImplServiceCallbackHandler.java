
/**
 * UNZipServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package com.hcctech.webservice.util;

    /**
     *  UNZipServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UNZipServiceImplServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UNZipServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UNZipServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for unbigzip method
            * override this method for handling normal response from unbigzip operation
            */
           public void receiveResultunbigzip(
                    com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnbigzipResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unbigzip operation
           */
            public void receiveErrorunbigzip(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unzipAndEncrypt method
            * override this method for handling normal response from unzipAndEncrypt operation
            */
           public void receiveResultunzipAndEncrypt(
                    com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipAndEncryptResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unzipAndEncrypt operation
           */
            public void receiveErrorunzipAndEncrypt(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unzip method
            * override this method for handling normal response from unzip operation
            */
           public void receiveResultunzip(
                    com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unzip operation
           */
            public void receiveErrorunzip(java.lang.Exception e) {
            }
                


    }
    