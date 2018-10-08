package cn.uce.cloud.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;


@Component
public class LoggerFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(LoggerFilter.class);
    @Autowired
    private Tracer tracer;
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 900;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
    	tracer.addTag("操作人", "王泽邦");
    	log.info("traceid--"+tracer.getCurrentSpan().getTraceId());
    	return null ; 
    }
}
