package tao.services.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.cs.tao.services.commons.BaseController;
import ro.cs.tao.services.commons.ServiceResponse;
import tao.services.sample.service.SampleService;

@Controller
@RequestMapping("/sample")
public class SampleController extends BaseController {

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/endpoint", method = RequestMethod.GET)
    public ResponseEntity<ServiceResponse<?>> sampleGet() {
        try {
            return prepareResult(sampleService.getLastNotification());
        } catch (Exception ex) {
            return handleException(ex);
        }
    }

}
