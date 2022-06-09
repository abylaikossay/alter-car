package net.alterapp.altercar.service;

import net.alterapp.altercar.filter.RequestContextFilter.RequestContext;


public interface ClientAttributesHelper {

  String getRequestId();

  RequestContext getRequestContext();

}
