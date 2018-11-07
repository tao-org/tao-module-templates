package tao.services.sample.service;

import org.springframework.stereotype.Service;
import ro.cs.tao.messaging.Message;
import ro.cs.tao.messaging.NotifiableComponent;
import ro.cs.tao.messaging.Topics;

@Service("sampleService")
public class SampleService extends NotifiableComponent {
    @Override
    protected String[] topics() {
        return new String[] {Topics.INFORMATION };
    }

    public String sampleMethod() { return "Hello"; }

    public Message getLastNotification() {
        return getLastMessage();
    }
}
