package com.globalroam.gnum.api;

import com.globalroam.gnum.api.auth.Authentication;
import com.globalroam.gnum.common.httpclient.HttpRequest;
import com.globalroam.gnum.common.httpclient.domain.Response;
import org.springframework.stereotype.Component;


/**
 * Created by zhangjian on 2016/4/13.
 */
@Component
public class GnumRestHttpRequest extends HttpRequest {

    private String apiRootPath;

    public GnumRestHttpRequest() {
        super(HttpAPIProperties.httpType, Authentication.getInstance().getHeaders(),HttpAPIProperties.address,HttpAPIProperties.port);
        this.apiRootPath = HttpAPIProperties.getParam("api.uri.root");
    }


    @Override
    public Response get(String uri) {
        return super.get(this.apiRootPath + uri);
    }
}
