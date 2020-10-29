package personal.yh.service.provider.service;

/**
 * user service
 *
 * @author yanhuan
 */
public interface UserService {

    String getInfoSuccess(Integer id);

    String getInfoFailure(Integer id);

    String paymentCircuitBreaker(Integer id);
}
