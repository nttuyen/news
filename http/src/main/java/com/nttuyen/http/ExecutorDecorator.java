package com.nttuyen.http;

/**
 * @author nttuyen266@gmail.com
 */
public abstract class ExecutorDecorator implements Executor {
    protected Executor executor;
    public ExecutorDecorator() {
    }
    public ExecutorDecorator setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    protected ExecutorDecorator getDecorator(Class<? extends ExecutorDecorator> c) {
        if(c.isInstance(this)) {
            return this;
        } else if(executor instanceof ExecutorDecorator) {
            return ((ExecutorDecorator)executor).getDecorator(c);
        }
        return null;
    }
    protected Executor getExecutor() {
        if(executor instanceof ExecutorDecorator) {
            return ((ExecutorDecorator)executor).getExecutor();
        } else {
            return executor;
        }
    }
}
