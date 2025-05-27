package cool.xxd.service.pay.application.service;


import group.hckj.pay.ui.request.BaseRequest;

public interface CommonService {
    <T extends BaseRequest> void validateRequest(T request);
}
