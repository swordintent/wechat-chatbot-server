package com.swordintent.wx.mp.error;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 统一错误处理页面配置
 * @author liuhe
 */
@Component
public class ErrorPageConfiguration implements ErrorPageRegistrar {
  @Override
  public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
    errorPageRegistry.addErrorPages(
        new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
    );
  }
}
