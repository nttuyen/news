package com.nttuyen.news.http;

/**
 * @author nttuyen266@gmail.com
 */
public abstract class HttpExecutorDecorator implements HttpExecutor {
    protected HttpExecutor executor;
    public HttpExecutorDecorator() {
    }
    public void setExecutor(HttpExecutor executor) {
        this.executor = executor;
    }

//    @Override
//    public void execute(HttpRequest command, HttpCallback callback) throws HttpException {
//        if(this.executor != null) {
//            this.executor.execute(command, callback);
//        }
//    }
}
