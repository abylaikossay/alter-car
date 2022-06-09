package net.alterapp.altercar.service.impl;

import net.alterapp.altercar.filter.RequestContextFilter;
import net.alterapp.altercar.filter.RequestContextFilter.RequestContext;
import net.alterapp.altercar.service.ClientAttributesHelper;
import org.springframework.stereotype.Service;

@Service
public class ClientAttributesHelperImpl implements ClientAttributesHelper {

  @Override
  public String getRequestId() {
    return getContext().getRequestId();
  }


  @Override
  public RequestContext getRequestContext() {
    return getContext();
  }

  private RequestContext getContext() {
    RequestContext context = RequestContextFilter.CONTEXT.get();
    return context;
  }

}
