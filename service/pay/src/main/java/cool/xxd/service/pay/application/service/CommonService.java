package cool.xxd.service.pay.application.service;


public interface CommonService {
    <T extends BaseRequest> void validateRequest(T request);
}
