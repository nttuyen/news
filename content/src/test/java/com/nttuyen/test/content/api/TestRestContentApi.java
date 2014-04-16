package com.nttuyen.test.content.api;

import com.nttuyen.content.api.ContentApi;
import com.nttuyen.content.api.impl.RestContentApi;
import com.nttuyen.content.entity.Content;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author nttuyen266@gmail.com
 */
public class TestRestContentApi {
    private ContentApi api;

    @Before
    public void before() {
        api = new RestContentApi();
    }

    @Test
    public void testGetNextCrawledContent() {
        Content content = api.nextCrawledContent();
        Assert.assertNotNull(content);
    }
}
